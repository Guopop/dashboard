package me.guopop.dashboard.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.guopop.dashboard.entity.Permission;
import me.guopop.dashboard.exception.PermissionException;
import me.guopop.dashboard.model.enums.ErrorEnum;
import me.guopop.dashboard.model.query.PermissionQuery;
import me.guopop.dashboard.model.vo.PermissionVO;
import me.guopop.dashboard.repository.PermissionRepository;
import me.guopop.dashboard.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author guopop
 * @date 2021/4/4 21:29
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PermissionQuery query) {
        Permission parent = permissionRepository.findById(query.getParentId())
                .orElseThrow(() -> new PermissionException(ErrorEnum.ILLEGAL_ARGUMENT));

        permissionRepository.findByName(query.getName()).ifPresent(p -> {
            throw new PermissionException(ErrorEnum.PERMISSION_NAME_EXIST);
        });

        permissionRepository.findByIdentifierAndType(query.getIdentifier(), Permission.Type.BUTTON).ifPresent(p -> {
            throw new PermissionException(ErrorEnum.PERMISSION_IDENTIFIER_EXIST);
        });

        checkPermissionType(query, parent);

        sortPermissionOrder(query, parent);

        Permission permission = new Permission();
        BeanUtils.copyProperties(query, permission);
        permission.setParent(parent);
        permissionRepository.save(permission);
    }

    private void checkPermissionType(PermissionQuery query, Permission parent) {
        boolean isDirectory = Objects.equals(query.getType(), Permission.Type.DIRECTORY);
        boolean isMenu = Objects.equals(query.getType(), Permission.Type.MENU);
        boolean isButton = Objects.equals(query.getType(), Permission.Type.BUTTON);
        boolean isParentDirectory = Objects.equals(parent.getType(), Permission.Type.DIRECTORY);
        boolean isParentMenu = Objects.equals(parent.getType(), Permission.Type.MENU);
        if (isDirectory && !isParentDirectory) {
            throw new PermissionException(ErrorEnum.PERMISSION_DIR_PARENT_IS_DIR);
        }
        if (isMenu && !isParentDirectory) {
            throw new PermissionException(ErrorEnum.PERMISSION_MENU_PARENT_IS_DIR);
        }
        if (isButton && !isParentMenu) {
            throw new PermissionException(ErrorEnum.PERMISSION_BUTTON_PARENT_IS_MENU);
        }
    }

    private void sortPermissionOrder(PermissionQuery query, Permission parent) {
        AtomicInteger sortOrder = new AtomicInteger(query.getPermissionOrder());

        parent.getChildren().stream()
                .sorted(Comparator.comparingInt(Permission::getPermissionOrder))
                .forEach(p -> {
                    if (Objects.equals(sortOrder.get(), p.getPermissionOrder())) {
                        p.setPermissionOrder(sortOrder.getAndIncrement() + 1);
                        permissionRepository.save(p);
                    }
                });
    }

    @Override
    public List<PermissionVO> query() {
        return permissionRepository.findAll()
                .stream()
                .filter(p -> Objects.nonNull(p.getParent()))
                .map(this::convertPermission2VO)
                .collect(Collectors.toList());
    }

    private PermissionVO convertPermission2VO(Permission p) {
        PermissionVO vo = PermissionVO.builder().build();
        BeanUtils.copyProperties(p, vo);

        vo.setType(p.getType().name());
        vo.setParentId(p.getParent().getId());

        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Long id, PermissionQuery query) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionException(ErrorEnum.ILLEGAL_ARGUMENT));

        permissionRepository.findByName(query.getName()).ifPresent(p -> {
            if (!Objects.equals(p.getName(), permission.getName())) {
                throw new PermissionException(ErrorEnum.PERMISSION_NAME_EXIST);
            }
        });

        permissionRepository.findByIdentifierAndType(query.getIdentifier(), Permission.Type.BUTTON).ifPresent(p -> {
            if (!Objects.equals(p.getIdentifier(), permission.getIdentifier())) {
                throw new PermissionException(ErrorEnum.PERMISSION_IDENTIFIER_EXIST);
            }
        });

        Permission parent = permissionRepository.findById(query.getParentId())
                .orElseThrow(() -> new PermissionException(ErrorEnum.ILLEGAL_ARGUMENT));

        checkPermissionType(query, parent);

        sortPermissionOrder(query, parent);

        BeanUtils.copyProperties(query, permission);
        permissionRepository.save(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }
}
