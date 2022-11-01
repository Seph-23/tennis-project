package myweb.secondboard.repository;

import myweb.secondboard.domain.ClubMember;

public interface ClubMemberRepositoryInterface {

  ClubMember findOne(Long id);
}
