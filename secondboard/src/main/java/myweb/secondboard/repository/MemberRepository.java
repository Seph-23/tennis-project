package myweb.secondboard.repository;

import myweb.secondboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryInterface {

}
