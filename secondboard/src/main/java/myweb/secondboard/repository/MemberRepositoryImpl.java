package myweb.secondboard.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;

  public List<Member> findAll() {         //Member entity 전체를 리스트로 반환
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }

  public Optional<Member> findByLoginId(String loginId) {
    return Optional.ofNullable(
        findAll().stream().filter(m -> m.getLoginId().equals(loginId)).findFirst().orElse(null));
//    List<Member> members = findAll();
//    for (Member member : members) {
//      if (member.getLoginId().equals(loginId)) {
//        return Optional.ofNullable(member);
//      }
//    }
//    return null;
  }

  public Optional<Member> findByNickname(String nickname) {
    return findAll().stream().filter(m -> m.getNickname().equals(nickname)).findFirst();
  }

  public Optional<Member> findByEmail(String email) {
    return findAll().stream().filter(m -> m.getEmail().equals(email)).findFirst();
  }
}
