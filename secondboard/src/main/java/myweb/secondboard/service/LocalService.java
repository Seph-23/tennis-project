package myweb.secondboard.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Local;
import myweb.secondboard.repository.LocalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocalService {

  private final LocalRepository localRepository;

  public List<Local> getLocalList() {
    return  localRepository.findAll();
  }
}
