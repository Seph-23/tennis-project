package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.ClubSaveForm;
import myweb.secondboard.dto.ClubUpdateForm;
import myweb.secondboard.web.Status;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

  @Column(length = 256)
  private String img;

  @Column
  private String imgPath;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "local_id")
  private Local local;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name="file_id")
  private File file;

  @NotNull
  @OneToOne(fetch = LAZY)
  @JoinColumn(name="member_id")
  private Member member;

  public static Club createClub(ClubSaveForm form, File file, Member leader) {
    Club club = new Club();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    club.setName(form.getName());
    club.setIntroduction(form.getIntroduction());
    club.setMemberCount(1);
    club.setCreatedDate(LocalDateTime.now().format(dtf));
    club.setStatus(Status.RECRUITING);
    club.setLocal(form.getLocal());
    club.setMember(leader);
    club.setFile(file);
    return club;
  }

  public void updateClub(ClubUpdateForm form, Club club) {
    club.setId(form.getId());
    club.setName(form.getName());
    club.setIntroduction(form.getIntroduction());
    club.setStatus(form.getStatus());
    club.setLocal(form.getLocal());
  }
}
