package com.backend.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationProvider {
    UserDetailsServiceImpl userDetailsService;
    PasswordEncoder passwordEncoder;
}
