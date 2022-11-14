package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.BoardLike;
import myweb.secondboard.repository.BoardLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardLikeService {
  private final BoardLikeRepository boardLikeRepository;

  public Long getLikeCount(Long boardId) {
//    return boardLikeRepository.findAll().stream().filter(like -> like.getBoard().getId() == boardId).toList().size();
    return boardLikeRepository.countByBoardId(boardId);
  }

  public String checkLike(Long boardId, Long memberId) {
    Optional<BoardLike> likeCheck = boardLikeRepository.find(boardId, memberId);

    if (likeCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }

  @Transactional
  public int clickLike(Board board, Member member) {
    BoardLike likeCheck = boardLikeRepository.find(board.getId(), member.getId()).orElse(null);
    if (likeCheck == null) {
      BoardLike like = BoardLike.createLike(board, member);
      boardLikeRepository.save(like);
      return 1;
    } else {
      boardLikeRepository.delete(likeCheck);
      return 0;
    }
  }
}
