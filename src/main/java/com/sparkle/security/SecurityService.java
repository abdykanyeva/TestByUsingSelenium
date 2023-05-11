package com.sparkle.security;

import com.sparkle.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {
    UserDTO getLoggedInUser();
}
