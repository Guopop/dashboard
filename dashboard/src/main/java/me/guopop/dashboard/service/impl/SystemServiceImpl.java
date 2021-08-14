package me.guopop.dashboard.service.impl;

import me.guopop.dashboard.entity.Permission;
import me.guopop.dashboard.entity.Role;
import me.guopop.dashboard.entity.User;
import me.guopop.dashboard.model.query.AuthenticationQuery;
import me.guopop.dashboard.model.vo.AuthenticationVO;
import me.guopop.dashboard.model.vo.AuthorizationVO;
import me.guopop.dashboard.model.vo.PermissionVO;
import me.guopop.dashboard.repository.UserRepository;
import me.guopop.dashboard.service.SystemService;
import me.guopop.dashboard.util.ContextUtil;
import me.guopop.dashboard.util.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author guopop
 * @date 2021/4/16 07:18
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthenticationVO authenticate(AuthenticationQuery query) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(query.getUsername(), query.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = JwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return AuthenticationVO.builder().token(token).build();
    }

    @Override
    public AuthorizationVO authorize() {
        User user = userRepository.findByName(ContextUtil.getCurrentUser().getUsername()).orElseThrow(() ->
                new IllegalArgumentException("当前用户不存在")
        );

        Set<PermissionVO> permissionVoSet = user.getRoles().stream().map(Role::getPermissions)
                .flatMap(permissions -> permissions.stream()
                        .filter(permission -> !permission.getType().equals(Permission.Type.BUTTON))
                        .map(this::generatePermissionVO)
                ).collect(Collectors.toSet());

        Set<String> identifiers = user.getRoles().stream().map(Role::getPermissions)
                .flatMap(permissions -> permissions.stream()
                        .filter(permission -> permission.getType().equals(Permission.Type.BUTTON))
                        .map(Permission::getIdentifier)
                ).collect(Collectors.toSet());

        return AuthorizationVO.builder()
                .username(user.getName())
                .identifiers(identifiers)
                .permissions(permissionVoSet)
                .build();
    }

    private PermissionVO generatePermissionVO(Permission permission) {
        PermissionVO vo = PermissionVO.builder().build();
        BeanUtils.copyProperties(permission, vo);
        vo.setParentId(permission.getParent().getId());
        vo.setType(permission.getType().name());
        return vo;
    }
}
