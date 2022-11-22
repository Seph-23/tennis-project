package myweb.secondboard.repository;

import myweb.secondboard.domain.MemberUploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberUploadFileRepository extends JpaRepository<MemberUploadFile, Long> {

}
