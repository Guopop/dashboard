package me.guopop.dashboard.handler;

import me.guopop.dashboard.entity.User;
import me.guopop.dashboard.exception.AuthenticationException;
import me.guopop.dashboard.model.enums.ErrorEnum;
import me.guopop.dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author guopop
 * @date 2021/1/15 15:06
 */
@Component
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() -> new AuthenticationException(ErrorEnum.AUTH_USER_NO_EXIST));

        String encodePassword = passwordEncoder.encode(user.getPassword());

        Set<SimpleGrantedAuthority> authoritySet = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(username, encodePassword, authoritySet);
    }
}
