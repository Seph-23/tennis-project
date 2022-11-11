package myweb.secondboard.domain.boards;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class BoardUploadFile extends UploadAbstract{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_file_id")
  private Long id;


}
