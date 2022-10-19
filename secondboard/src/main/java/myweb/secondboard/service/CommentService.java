package myweb.secondboard.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
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

    @Transactional
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void updateComment(Long commentId, Map<String, Object> param) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        Comment comment = commentRepository.findById(commentId).get();
        comment.setContent(param.get("content").toString());
        comment.setModifiedDate(LocalDateTime.now().format(dtf));
        commentRepository.save(comment);
    }

    @Transactional
    public void updateCommentCancel(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        commentRepository.save(comment);
    }
}
