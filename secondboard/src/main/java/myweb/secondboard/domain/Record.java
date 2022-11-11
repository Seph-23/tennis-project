package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Record implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "record_id")
  private Long id;

  private Integer win;

  private Integer lose;

  private Integer penalty;

  private Double rate;

  private Integer points;

  public static Record createRecord() {
    Record record = new Record();
    record.setWin(0);
    record.setLose(0);
    record.setPenalty(0);
    record.setPoints(100);
    return record;
  }
}


