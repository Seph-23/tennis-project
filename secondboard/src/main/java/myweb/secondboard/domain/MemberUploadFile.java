package myweb.secondboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.boards.UploadAbstract;

@Entity
@Getter
@Setter
public class MemberUploadFile extends UploadAbstract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_file_id")
  private Long id;

}
