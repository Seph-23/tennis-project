package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.ClubSaveForm;
import myweb.secondboard.dto.ClubUpdateForm;
import myweb.secondboard.web.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Club implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_id")
  private Long id;

  @NotNull
  @Column(length = 16)
  private String name;

  @Column(length = 256)
  private String introduction;

  @NotNull
  @Column(length = 10)
  private int memberCount;

  // base64 파일 저장
  @Lob
  private byte[] img;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "local_id")
  private Local local;


  @NotNull
  @OneToOne(fetch = LAZY)
  @JoinColumn(name="member_id")
  private Member member;

  public static Club createClub(ClubSaveForm form, Member leader, MultipartFile file) throws IOException {
    Club club = new Club();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    club.setName(form.getName());
    club.setIntroduction(form.getIntroduction());
    club.setMemberCount(1);
    club.setCreatedDate(LocalDateTime.now().format(dtf));
    club.setStatus(Status.RECRUITING);
    club.setLocal(form.getLocal());
    club.setMember(leader);

    if(file.isEmpty()){
      club.setImg(null);
    } else {

      byte[] ImgEn = new byte[0];
      if (file != null) {
        Base64.Encoder encoder = Base64.getEncoder();
        ImgEn = encoder.encode(file.getBytes());
      }
      club.setImg(ImgEn);
    }

    return club;
  }

  public void updateClub(ClubUpdateForm form, Club club, MultipartFile file) throws IOException {
    club.setId(form.getId());
    club.setName(form.getName());
    club.setIntroduction(form.getIntroduction());
    club.setStatus(form.getStatus());
    club.setLocal(form.getLocal());

    if(file.isEmpty()){
      byte[] origin = club.getImg();
      club.setImg(origin);

    } else {
      byte[] updateImg = new byte[0];
      if (file != null) {
        Base64.Encoder encoder = Base64.getEncoder();
        updateImg = encoder.encode(file.getBytes());
      }
      club.setImg(updateImg);
    }
  }
}
