package myweb.secondboard.repository;

import myweb.secondboard.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryInterface {

}
