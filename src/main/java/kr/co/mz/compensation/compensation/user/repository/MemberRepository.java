package kr.co.mz.compensation.compensation.user.repository;

import kr.co.mz.compensation.compensation.user.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

}
