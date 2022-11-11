package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
