package ua.polina.smart_house_monitoring_system.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.polina.smart_house_monitoring_system.repository.UserRepository;

/**
 * The type Custom user detail service.
 */
@Primary
@Component
public class CustomUserDetailService implements UserDetailsService {
    /**
     * The User repository.
     */
    private UserRepository userRepository;

    /**
     * Instantiates a new Custom user detail service.
     *
     * @param userRepository the user repository
     */
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * The method to find user in database by his/her username.
     *
     * @param username username
     * @return userDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
