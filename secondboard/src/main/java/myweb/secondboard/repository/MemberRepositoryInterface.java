package myweb.secondboard.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import myweb.secondboard.domain.Member;

public interface MemberRepositoryInterface {

  List<Member> findAll();

  Optional<Member> findByLoginId(String loginId);

  Optional<Member> findByNickname(String nickname);

  Optional<Member> findByEmail(String email);

  Optional<Member> findByPhoneNum(String phoneNum);

  List<Member> findRankTopThree();

  List<Member> findRankList();
}
