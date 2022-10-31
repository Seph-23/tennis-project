package myweb.secondboard.repository;

import myweb.secondboard.domain.Club;

public interface ClubRepositoryInterface {

  Club findOne(Long clubId);
}