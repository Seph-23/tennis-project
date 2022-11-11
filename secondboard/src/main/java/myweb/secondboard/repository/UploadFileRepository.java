package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.BoardUploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<BoardUploadFile, Long>{

}
