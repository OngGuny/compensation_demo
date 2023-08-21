package kr.co.mz.compensation.compensation.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GenericUserDto {

    private Long seq;
    private String email;
    private String name;
    private String password;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String role;
}

