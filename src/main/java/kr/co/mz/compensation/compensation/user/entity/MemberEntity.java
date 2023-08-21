package kr.co.mz.compensation.compensation.user.entity;

import jakarta.persistence.*;
import kr.co.mz.compensation.compensation.user.role.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    @Column(name = "email", nullable = false)
    protected String email;
    @Column(name = "name", nullable = false)
    protected String name;
    @Column(name = "password", nullable = false)
    protected String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    protected Role role;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "modified_at")
    protected LocalDateTime modifiedAt;

}
