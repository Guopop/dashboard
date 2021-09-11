package me.guopop.dashboard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import me.guopop.dashboard.entity.Role;
import me.guopop.dashboard.entity.User;
import me.guopop.dashboard.exception.UserException;
import me.guopop.dashboard.model.enums.ErrorEnum;
import me.guopop.dashboard.model.query.UserFindQuery;
import me.guopop.dashboard.model.query.UserQuery;
import me.guopop.dashboard.model.vo.PageVO;
import me.guopop.dashboard.model.vo.RoleVO;
import me.guopop.dashboard.model.vo.UserVO;
import me.guopop.dashboard.repository.RoleRepository;
import me.guopop.dashboard.repository.UserRepository;
import me.guopop.dashboard.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserQuery query) {
        userRepository.findByName(query.getName()).ifPresent(u -> {
            throw new UserException(ErrorEnum.USER_NAME_EXIST);
        });

        List<Role> roles = roleRepository.findAllById(query.getRoleIds());

        User user = User.builder()
                .name(query.getName())
                .password(query.getPassword())
                .state(query.getState())
                .build();
        if (CollUtil.isNotEmpty(roles)) {
            user.setRoles(CollUtil.newHashSet(roles));
        }

        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Long id, UserQuery query) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(ErrorEnum.USER_NO_EXIST));

        userRepository.findByName(query.getName()).ifPresent(u -> {
            if (!Objects.equals(u, user)) {
                throw new UserException(ErrorEnum.USER_NAME_EXIST);
            }
        });

        user.setName(query.getName());
        user.setState(query.getState());
        if (CollUtil.isNotEmpty(query.getRoleIds())) {
            List<Role> roles = roleRepository.findAllById(query.getRoleIds());
            user.setRoles(CollUtil.newHashSet(roles));
        }

        userRepository.save(user);
    }

    @Override
    public PageVO<UserVO> query(UserFindQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getPageSize(), Sort.Direction.DESC, "id");

        Specification<User> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Path<Object> name = root.get("name");
            return criteriaBuilder.like(name.as(String.class), StrUtil.wrap(query.getName(), "%"));
        };

        Page<User> userPage = userRepository.findAll(specification, pageRequest);

        List<UserVO> userVOList = userPage.getContent().stream().map(u -> {
            String role = u.getRoles().stream().map(Role::getName).collect(Collectors.joining("|"));
            return UserVO.builder().id(u.getId()).name(u.getName()).role(role).state(u.getState()).build();
        }).collect(Collectors.toList());

        return PageVO.generate(userPage.getTotalElements(), userVOList);
    }
}
