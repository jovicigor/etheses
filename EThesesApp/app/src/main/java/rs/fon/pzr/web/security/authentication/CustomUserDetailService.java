package rs.fon.pzr.web.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.pzr.core.domain.model.user.UserEntity;
import rs.fon.pzr.core.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        UserEntity user = userService.getUser(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with userName: " + email));

        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        if (user.isAdmin()) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail().asString(), user.getPassword(), true, true, true, true, authList);
    }
}
