package ar.edu.unnoba.pm2026.courses.util;

import com.password4j.Password;

public class PasswordEncoder {
    public String encode(String rawPassword){
        return Password.hash(rawPassword).withBcrypt().getResult();
    }
    public boolean verify(String rawPassword, String encodedPassword){
        return Password.check(rawPassword, encodedPassword).withBcrypt();
    }
}
