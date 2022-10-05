package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.repository.BoardRepository;
import myweb.secondboard.repository.BoardRepositoryInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final BoardRepositoryInterface boardRepositoryInterface;

  //게시글 등록
  @Transactional
  public Long addBoard(BoardSaveForm form, Member member) {
    Board board = Board.createBoard(form, member);
    boardRepository.save(board);
    return board.getId();
  }

  @Transactional
  public Page<Board> getBoardList(Pageable pageable) {
    return boardRepositoryInterface.findAll(pageable);
  }

  public Board findOne(Long boardId) {
    return boardRepository.findOne(boardId);
  }

  @Transactional
  public void increaseView(Long boardId) {
    boardRepository.updateView(boardId);
  }
}
