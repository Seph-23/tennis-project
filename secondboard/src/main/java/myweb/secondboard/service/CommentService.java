package myweb.secondboard.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findComments(Long boardId) {
        return commentRepository.findComments(boardId);
    }

    @Transactional
    public void save(Map<String, Object> param, Board board, Member member) {
        Comment comment = new Comment();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        comment.setAuthor(member.getNickname());
        comment.setContent(param.get("content").toString());
        comment.setBoard(board);
        comment.setMember(member);
        comment.setCreatedDate(LocalDateTime.now().format(dtf));
        comment.setModifiedDate(LocalDateTime.now().format(dtf));
        commentRepository.save(comment);
    }

    public void deleteById(Map<String, Object> param) {
        commentRepository.deleteById(Long.parseLong(param.get("id").toString()));
    }
}
