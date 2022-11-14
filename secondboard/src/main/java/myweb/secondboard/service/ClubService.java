package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Club;
import myweb.secondboard.domain.ClubMember;
import myweb.secondboard.domain.File;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.ClubSaveForm;
import myweb.secondboard.dto.ClubUpdateForm;
import myweb.secondboard.repository.ClubMemberRepository;
import myweb.secondboard.repository.ClubRepository;
import myweb.secondboard.repository.FileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

  private final ClubRepository clubRepository;
  private final ClubMemberRepository clubMemberRepository;
  private final FileRepository fileRepository;
  private final FileService fileService;

  public Page<Club> getClubList(Pageable pageable) {
    return clubRepository.findAll(pageable);
  }

  public Club findOne(Long clubId) {
    return clubRepository.findOne(clubId);
  }

  @Transactional
  public Club addClub(ClubSaveForm form, Member member, MultipartFile photoImg) throws IOException {

    File file = File.createImg(fileService.ImgSave(photoImg));
    fileRepository.save(file);

    // case : 사진 업로드 안할때 -> view에서 src로 기본사진 불러옴.

    Club club = Club.createClub(form, file, member);
    clubRepository.save(club);

    ClubMember clubMember = ClubMember.createClubMember(club, member);
    clubMemberRepository.save(clubMember);

    return club;
  }

  @Transactional
  public ClubMember addClubMember(Club club, Member member) {
    ClubMember clubMember = ClubMember.createClubMember(club, member);
    clubMemberRepository.save(clubMember);
    return clubMember;
  }


  public List<ClubMember> getClubMemberList(Long clubId) {

    return clubMemberRepository.findAll()
      .stream()
      .filter(clubMember -> clubMember.getClub().getId() == clubId).toList();
  }

  public ClubMember clubMemberCheck(Long clubId, Long memberId) {
    return clubMemberRepository.exist(clubId, memberId).orElse(null);
  }

  @Transactional
  public Long update(ClubUpdateForm form, MultipartFile file) throws IOException {

    Club club = clubRepository.findOne(form.getId());
    File originFile = club.getFile();
    byte[] files = fileService.ImgSave(file);

    if(!file.isEmpty()){
      originFile.updateImgPath(originFile, files);
    }

    club.updateClub(form, club);

    return club.getId();
  }

  @Transactional
  public void deleteClubMember(Long clubId, Long memberId) {
    ClubMember clubMember = clubMemberCheck(clubId, memberId);
    clubMemberRepository.delete(clubMember);
    Club club = clubRepository.findOne(clubId);
    club.setMemberCount(club.getMemberCount() - 1);
  }

  @Transactional
  public void deleteClub(Long clubId) {
    clubMemberRepository.deleteAllInBatch(getClubMemberList(clubId));
    clubRepository.deleteById(clubId);
  }

  public List<Club> searchClubs(String keyword) {
    // 검색 키워드가 지역 이름이랑 같거나, 클럽 이름에 포함되었을 때
    return clubRepository.findAll().stream()
      .filter(searchClubs -> searchClubs.getLocal().getName().contains(keyword)
        || searchClubs.getName().contains(keyword)).toList();
  }

  public Page<Club> searchClubs(String keyword, Pageable pageable) {
    String localName = keyword;
    String name = keyword;
    return clubRepository.findByLocal_NameContainingOrNameContaining(localName, name, pageable);
  }

  public ClubMember get(Long id) {
    return clubMemberRepository.findOne(id);
  }

}