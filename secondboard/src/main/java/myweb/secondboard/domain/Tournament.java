package myweb.secondboard.domain;

import static javax.persistence.FetchType.*;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tournament {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(length = 31)
  private String title;

  @NotNull
  @Column(length = 145)
  private String place;

  @NotNull
  @Column(length = 40)
  private LocalDate applicationStartDate;

  @NotNull
  @Column(length = 40)
  private LocalDate applicationEndDate;

  @NotNull
  @Column(length = 40)
  private LocalDate CompStartDate;

  @NotNull
  @Column(length = 40)
  private LocalDate CompEndDate;

  @Column(length = 256)
  private String image;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "local_id")
  private Local local;

}
