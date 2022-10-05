package myweb.secondboard.repository;

import myweb.secondboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryInterface {

}
