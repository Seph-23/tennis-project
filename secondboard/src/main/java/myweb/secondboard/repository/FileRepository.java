package myweb.secondboard.repository;

import myweb.secondboard.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {


}
