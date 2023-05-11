package com.sparkle.converter;

import com.sparkle.dto.UserDTO;
import com.sparkle.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDtoConverter implements Converter<String, UserDTO> {

    private final UserService userService;

    public UserDtoConverter(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDTO convert(String source) {
        if (source==null || source.equals("")){
            return null;
        }else{
            return userService.findById(Long.parseLong(source));
        }

    }
}
