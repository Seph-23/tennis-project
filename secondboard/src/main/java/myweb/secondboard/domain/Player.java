package myweb.secondboard.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.MatchSaveForm;
import myweb.secondboard.dto.PlayerAddForm;
import myweb.secondboard.web.Team;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "player_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "match_id")
  private Matching matching;

  @Enumerated(EnumType.STRING)
  private Team team;

  public static Player createPlayer(Matching matching, Member member) {
    Player player = new Player();
    player.setTeam(Team.A);
    player.setMember(member);
    player.setMatching(matching);
    return player;
  }

  public static Player createPlayerFromForm(PlayerAddForm form, Member member, Matching matching) {
    Player player = new Player();
    player.setMatching(matching);
    player.setMember(member);
    player.setTeam(Team.valueOf(form.getTeam()));
    return player;
  }
}
