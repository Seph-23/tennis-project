package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.ResultAddForm;
import myweb.secondboard.web.GameResult;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ResultTemp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "result_temp_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "player_id")
  private Player player;

  @Enumerated(EnumType.STRING)
  private GameResult gameResult;


  public static ResultTemp createResultTemp(ResultAddForm resultAddForm, Player player) {
    ResultTemp resultTemp = new ResultTemp();
    resultTemp.setGameResult(resultAddForm.getGameResult());
    resultTemp.setPlayer(player);

    return  resultTemp;
  }
}
