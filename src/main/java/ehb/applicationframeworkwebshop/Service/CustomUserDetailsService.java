package ehb.applicationframeworkwebshop.Service;

import ehb.applicationframeworkwebshop.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

    User findByEmail(String email);
    User save(User user);
}
