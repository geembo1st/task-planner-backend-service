package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.EmailNotFoundException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailWithRoles(email)
                .map(user -> {
                    List<GrantedAuthority> authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList());
                    return new CustomUserDetails(user, authorities);
                })
                .orElseThrow(() -> new EmailNotFoundException("Email не найден: " + email));
    }
}
