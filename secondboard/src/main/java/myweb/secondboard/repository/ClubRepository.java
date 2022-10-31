package myweb.secondboard.repository;

import myweb.secondboard.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryInterface{
}
