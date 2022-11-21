package myweb.secondboard.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.TournamentSaveForm;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tournament {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 31)
  private String title;

  private String content;

  @Column(length = 145)
  private String place;

  @Column(length = 40)
  private LocalDate applicationStartDate;

  @Column(length = 40)
  private LocalDate applicationEndDate;

  @Column(length = 40)
  private LocalDate compStartDate;

  @Column(length = 40)
  private LocalDate compEndDate;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "local_id")
  private Local local;

  private String url;

  @Lob
  private byte[] imgEn;

  public static Tournament createTournament(TournamentSaveForm form, MultipartFile file) {
    Tournament tournament = new Tournament();
    tournament.setTitle(form.getTitle());
    tournament.setContent(form.getContent());
    tournament.setPlace(form.getPlace());
    tournament.setApplicationStartDate(form.getApplicationStartDate());
    tournament.setApplicationEndDate(form.getApplicationEndDate());
    tournament.setCompStartDate(form.getCompStartDate());
    tournament.setCompEndDate(form.getCompEndDate());
    tournament.setLocal(form.getLocal());
    tournament.setUrl(form.getUrl());
    return tournament;
  }

  public void setImgEn(MultipartFile file, Tournament tournament) throws IOException {
    byte[] ImgEn = new byte[0];

    if (file != null) {
      Base64.Encoder encoder = Base64.getEncoder();
      ImgEn = encoder.encode(file.getBytes());
    }
    tournament.setImgEn(ImgEn);
  }

}
