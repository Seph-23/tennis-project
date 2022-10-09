package myweb.secondboard.repository;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl {

    @PersistenceContext
    private final EntityManager em;

}
