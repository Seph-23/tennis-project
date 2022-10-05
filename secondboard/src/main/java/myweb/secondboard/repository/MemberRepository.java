package myweb.secondboard.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  private final EntityManager em;

  public void save(Member member) {       //멤버 엔티티를 디비에 저장.
    em.persist(member);
  }

  public List<Member> findAll() {         //Member entity 전체를 리스트로 반환
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }

  public Optional<Member> findByLoginId(String loginId) {
    return findAll().stream().filter(m -> m.getLoginId().equals(loginId)).findFirst();
  }

  public Optional<Member> findByNickname(String nickname) {
    return findAll().stream().filter(m -> m.getNickname().equals(nickname)).findFirst();
  }

  public Optional<Member> findByEmail(String email) {
    return findAll().stream().filter(m -> m.getEmail().equals(email)).findFirst();
  }
}
