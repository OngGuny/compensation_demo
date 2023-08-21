package kr.co.mz.compensation.compensation.security;

import kr.co.mz.compensation.compensation.user.dto.GenericUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final GenericUserDto genericUserDto;

    public CustomUserDetails(GenericUserDto genericUserDto, Collection<? extends GrantedAuthority> authorities) {
        super(genericUserDto.getEmail(), genericUserDto.getPassword(), authorities);
        this.genericUserDto = genericUserDto;
    }

    public GenericUserDto getUserDto() {
        System.out.println("userDto : " + genericUserDto);
        return genericUserDto;
    }
}
