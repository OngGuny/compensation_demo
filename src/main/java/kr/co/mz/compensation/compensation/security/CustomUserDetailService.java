package kr.co.mz.compensation.compensation.security;

import kr.co.mz.compensation.compensation.user.dto.GenericUserDto;
import kr.co.mz.compensation.compensation.user.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomUserDetailService(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userEntity = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new CustomUserDetails(modelMapper.map(userEntity, GenericUserDto.class),
                List.of(new SimpleGrantedAuthority(userEntity.getRole().name())));
    }
}
