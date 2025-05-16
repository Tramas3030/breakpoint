package br.com.Tramas3030.breakpoint.security;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Named("encodePassword")
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }
}

