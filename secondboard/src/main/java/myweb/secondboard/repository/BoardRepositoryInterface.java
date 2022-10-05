package myweb.secondboard.repository;

import myweb.secondboard.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepositoryInterface extends JpaRepository<Board, Long> {

}
