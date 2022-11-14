package myweb.secondboard.domain.boards;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BoardAbstract {

  private String title;

  @Column(length = 1000000000)
  private String content;

  private String author;
  private Integer views;
  private String createdDate;
  private String modifiedDate;

}
