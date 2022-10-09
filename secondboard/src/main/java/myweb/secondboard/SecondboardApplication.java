package myweb.secondboard;

import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.repository.BoardRepository;
import myweb.secondboard.repository.CommentRepository;
import myweb.secondboard.repository.MemberRepository;
import myweb.secondboard.repository.MemberRepositoryInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@SpringBootApplication
public class SecondboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondboardApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(MemberRepository memberRepository,
									  BoardRepository boardRepository,
									  CommentRepository commentRepository) {

		return args -> IntStream.rangeClosed(1, 154).forEach(i -> {
			Member member = new Member();
			member.setLoginId("testtest" + i);
			member.setPassword("testtest" + i + "!");
			member.setNickname("test" + i);
			member.setEmail("test" + i + "@gmail.com");
			memberRepository.save(member);

			Board board = new Board();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
			board.setTitle("test" + i);
			board.setAuthor(member.getNickname());
			board.setContent("test" + i + "게시글 내용 입니다....");
			board.setViews(0);
			board.setCreatedDate(LocalDateTime.now().format(dtf));
			board.setModifiedDate(LocalDateTime.now().format(dtf));
			board.setMember(member);

			Comment comment = new Comment();
			comment.setContent("testtest" + i);
			comment.setAuthor(member.getNickname());
			comment.setCreatedDate(LocalDateTime.now().format(dtf));
			comment.setModifiedDate(LocalDateTime.now().format(dtf));
			comment.setMember(member);
			comment.setBoard(board);

			board.setComments(commentRepository.findComments(board.getId()));

			boardRepository.save(board);
			commentRepository.save(comment);
		});
	}
}
