package myweb.secondboard.repository;

import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Matching;

public interface MatchRepositoryInterface {

  Matching findOne(Long matchId);
}
