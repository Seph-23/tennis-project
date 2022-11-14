package myweb.secondboard.repository;

import myweb.secondboard.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryInterface {
  Page<Board> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);
}
