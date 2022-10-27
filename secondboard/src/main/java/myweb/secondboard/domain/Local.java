package myweb.secondboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 지역 도메인
 * @since 2022-10-25
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Local {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "local_id")
  private Long id;

  @NotNull
  @Column(length = 11)
  private String name;
}
