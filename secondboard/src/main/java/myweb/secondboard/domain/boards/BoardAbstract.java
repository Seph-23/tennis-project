package myweb.secondboard.domain.boards;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


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
