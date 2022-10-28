package myweb.secondboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.MatchSaveForm;
import myweb.secondboard.dto.PlayerAddForm;
import myweb.secondboard.web.Team;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "player_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "match_id")
  private Matching matching;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

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
    player.setTeam(Team.valueOf(form.getTeam()));
    player.setMember(member);
    player.setMatching(matching);
    return player;
  }
}
