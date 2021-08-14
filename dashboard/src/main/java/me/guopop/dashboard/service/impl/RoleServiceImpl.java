package me.guopop.dashboard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import me.guopop.dashboard.entity.Permission;
import me.guopop.dashboard.entity.Role;
import me.guopop.dashboard.exception.RoleException;
import me.guopop.dashboard.model.enums.ErrorEnum;
import me.guopop.dashboard.model.query.RoleFindQuery;
import me.guopop.dashboard.model.query.RoleQuery;
import me.guopop.dashboard.model.vo.PageVO;
import me.guopop.dashboard.model.vo.PermissionVO;
import me.guopop.dashboard.model.vo.RoleVO;
import me.guopop.dashboard.repository.PermissionRepository;
import me.guopop.dashboard.repository.RoleRepository;
import me.guopop.dashboard.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author guopop
 * @date 2021/4/4 21:29
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RoleQuery query) {
        roleRepository.findByName(query.getName()).ifPresent(r -> {
            throw new RoleException(ErrorEnum.ROLE_NAME_EXIST);
        });

        List<Permission> permissions = permissionRepository.findAllById(query.getPermissionIds());

        Role role = Role.builder().name(query.getName()).permissions(CollUtil.newHashSet(permissions)).build();

        roleRepository.save(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Long id, RoleQuery query) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleException(ErrorEnum.ROLE_NO_EXIST));

        roleRepository.findByName(query.getName()).ifPresent(r -> {
            if (!Objects.equals(r, role)) {
                throw new RoleException(ErrorEnum.ROLE_NAME_EXIST);
            }
        });

        List<Permission> permissions = permissionRepository.findAllById(query.getPermissionIds());

        role.setName(query.getName());
        role.setPermissions(CollUtil.newHashSet(permissions));

        roleRepository.save(role);
    }

    @Override
    public PageVO<RoleVO> query(RoleFindQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getPageSize(), Sort.Direction.DESC, "id");

        Specification<Role> specification = generateSpecification(query);

        Page<Role> rolePage = roleRepository.findAll(specification, pageRequest);

        List<RoleVO> roleVOList = rolePage.getContent().stream().map(r -> {
            List<PermissionVO> permissionVOList = r.getPermissions().stream().map(this::convertPermission2VO).collect(Collectors.toList());
            return RoleVO.builder().name(r.getName()).permissionVOList(permissionVOList).build();
        }).collect(Collectors.toList());

        return PageVO.generate(rolePage.getTotalElements(), roleVOList);
    }

    private Specification<Role> generateSpecification(RoleFindQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Path<Object> name = root.get("name");
            return criteriaBuilder.like(name.as(String.class), StrUtil.wrap(query.getName(), "%"));
        };
    }

    private PermissionVO convertPermission2VO(Permission p) {
        return PermissionVO.builder()
                .id(p.getId())
                .name(p.getName())
                .identifier(p.getIdentifier())
                .type(p.getType().name())
                .permissionOrder(p.getPermissionOrder())
                .build();
    }
}
