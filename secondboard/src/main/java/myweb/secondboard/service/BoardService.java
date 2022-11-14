package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.dto.BoardUpdateForm;
import myweb.secondboard.repository.BoardLikeRepository;
import myweb.secondboard.repository.BoardReportRepository;
import myweb.secondboard.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final BoardLikeRepository boardLikeRepository;
  private final BoardReportRepository boardReportRepository;

  public Board findOne(Long boardId) {
    return boardRepository.findOne(boardId);
  }

  //게시글 등록
  @Transactional
  public Long addBoard(BoardSaveForm form, Member member) {
    Board board = Board.createBoard(form, member);
    boardRepository.save(board);
    return board.getId();
  }

  @Transactional
  public Page<Board> getBoardList(Pageable pageable) {
    return boardRepository.findAll(pageable);
  }

  @Transactional
  public void increaseView(Long boardId) {
    boardRepository.updateView(boardId);
  }

  @Transactional
  public void deleteById(Long boardId) {
    boardLikeRepository.deleteAllByBoardId(boardId);
    boardReportRepository.deleteAllByBoardId(boardId);
    boardRepository.deleteById(boardId);
  }

  @Transactional
  public void update(BoardUpdateForm form, Member member) {
    Board board = boardRepository.findOne(form.getId()); //조회한 board 엔티티는 영속 상태
    board.updateBoard(board, form, member);
    // boardRepository.save(board); 트랜잭션 커밋 시점에 변경 감지(Dirty checking)
  }

  public Page<Board> searchBoards(String keyword, Pageable pageable) {
    String title = keyword;
    String author = keyword;
    return boardRepository.findByTitleContainingOrAuthorContaining(title, author, pageable);
  }
}
