package myweb.secondboard.domain.boards;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class UploadAbstract {

  @Column
  private String fileName;

  @Column
  private String saveFileName;

  @Column
  private String filePath;

  @Column
  private String contentType;

  private long size;

  private LocalDateTime registerDate;
}
