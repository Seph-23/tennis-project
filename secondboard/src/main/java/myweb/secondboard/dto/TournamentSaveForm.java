package myweb.secondboard.dto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.Local;
import myweb.secondboard.domain.Tournament;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
public class TournamentSaveForm {

  private String title;
  private String content;
  private String place;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate applicationStartDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate applicationEndDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate compStartDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate compEndDate;
  private Local local;
  private String url;
  private String image;

  public TournamentSaveForm() {
  }

  public TournamentSaveForm(Tournament tournament) {
    this.title = tournament.getTitle();
    this.content = tournament.getContent();
    this.place = tournament.getPlace();
    this.applicationStartDate = tournament.getApplicationStartDate();
    this.applicationEndDate = tournament.getApplicationEndDate();
    this.compStartDate = tournament.getCompStartDate();
    this.compEndDate = tournament.getCompEndDate();
    this.local = tournament.getLocal();
    this.url = tournament.getUrl();
    String src = new String(tournament.getImgEn(), StandardCharsets.UTF_8);
    this.image = src;
  }
}
