package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Club;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.Visitor;
import myweb.secondboard.repository.VisitorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public List<Visitor> findVisitors(Long clubId){
        return visitorRepository.findVisitors(clubId);
    }


    public void save(Map<String, Object> param, Club club, Member member) {
        Visitor visitor = Visitor.createVisitor(param, club, member);
        visitorRepository.save(visitor);

    }

    @Transactional
    public void updateVisitor(Long visitorId, Map<String, Object> param) {
        Visitor visitor = visitorRepository.findById(visitorId).get();
        visitor.updateVisitor(visitorId, param, visitor);
    }

    @Transactional
    public void updateVisitorCancle(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId).get();
    }

    @Transactional
    public void deleteById(Long visitorId) {
        visitorRepository.deleteById(visitorId);
    }
}
