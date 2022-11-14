package myweb.secondboard.domain.comments;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter @Setter
@MappedSuperclass
public abstract class CommentAbstract {
  @NotNull
  @Column(length = 100)
  private String content;

  @NotNull
  @Column(length = 20)
  private String author;

  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  @LastModifiedDate
  @Column(length = 40)
  private String modifiedDate;
}
