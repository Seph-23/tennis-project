package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.PlayerAddForm;
import myweb.secondboard.web.Team;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "player_id")
  private Long id;

  @NotNull
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "matching_id")
  private Matching matching;

  @NotNull
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Team team;


  public static Player createPlayerFromForm(PlayerAddForm form, Member member, Matching matching) {
    Player player = new Player();
    player.setMatching(matching);
    player.setMember(member);
    player.setTeam(Team.valueOf(form.getTeam()));
    return player;
  }

  public static Player createPlayer(Matching matching, Member member) {
    Player player = new Player();
    player.setTeam(Team.A);
    player.setMember(member);
    player.setMatching(matching);

    return player;
  }
}
