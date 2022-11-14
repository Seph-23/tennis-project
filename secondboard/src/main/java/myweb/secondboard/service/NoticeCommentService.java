package myweb.secondboard.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.domain.comments.NoticeComment;
import myweb.secondboard.repository.CommentRepository;
import myweb.secondboard.repository.NoticeCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeCommentService {

    private final NoticeCommentRepository noticeCommentRepository;

    public List<NoticeComment> findComments(Long noticeId) {
        return noticeCommentRepository.findComments(noticeId);
    }

   

    @Transactional
    public void save(Map<String, Object> param, Notice notice, Member member) {
        NoticeComment noticeComment = NoticeComment.createComment(param, notice, member);
        noticeCommentRepository.save(noticeComment);
    }

    @Transactional
    public void deleteById(Long commentId) {
        noticeCommentRepository.deleteById(commentId);
    }

    @Transactional
    public void updateComment(Long commentId, Map<String, Object> param) {
        NoticeComment noticeComment = noticeCommentRepository.findById(commentId).get();
        noticeComment.updateComment(commentId, param, noticeComment);
    }

    @Transactional
    public void updateCommentCancel(Long commentId) {
        NoticeComment noticeComment = noticeCommentRepository.findById(commentId).get();
    }
}
