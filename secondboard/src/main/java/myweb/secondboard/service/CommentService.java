package myweb.secondboard.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.repository.BoardCommentReportRepository;
import myweb.secondboard.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardCommentReportRepository boardCommentReportRepository;

    public Comment findOne(Long commentId) {
    return commentRepository.findById(commentId).get();
    }

    public List<Comment> findComments(Long boardId) {
        return commentRepository.findComments(boardId);
    }

    @Transactional
    public void save(Map<String, Object> param, Board board, Member member) {
        Comment comment = Comment.createComment(param, board, member);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteById(Long commentId) {
        boardCommentReportRepository.deleteAllByCommentId(commentId);
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void updateComment(Long commentId, Map<String, Object> param) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.updateComment(commentId, param, comment);
    }

    @Transactional
    public void updateCommentCancel(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
    }
}
