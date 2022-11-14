package myweb.secondboard.repository;

import myweb.secondboard.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryInterface{

  Page<Club> findByLocal_NameContainingOrNameContaining(String localName, String name, Pageable pageable);
}
