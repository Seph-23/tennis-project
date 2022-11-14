package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryInterface {
  Page<Notice> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);
}
