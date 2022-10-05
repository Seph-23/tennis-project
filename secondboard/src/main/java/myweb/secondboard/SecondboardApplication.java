package myweb.secondboard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.repository.BoardRepositoryInterface;
import myweb.secondboard.repository.MemberRepositoryInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecondboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondboardApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner initData(MemberRepositoryInterface memberRepositoryInterface,
//		BoardRepositoryInterface boardRepositoryInterface) {
//
//		return args -> IntStream.rangeClosed(1, 154).forEach(i -> {
//			Member member = new Member();
//			member.setLoginId("testtest" + i);
//			member.setPassword("testtest" + i + "!");
//			member.setNickname("test" + i);
//			member.setEmail("test" + i + "@gmail.com");
//			memberRepositoryInterface.save(member);
//
//			Board board = new Board();
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss");
//			board.setTitle("test" + i);
//			board.setAuthor(member.getNickname());
//			board.setContent("test" + i + "게시글 내용 입니다....");
//			board.setViews(0);
//			board.setCreatedDate(LocalDateTime.now().format(dtf));
//			board.setModifiedDate(LocalDateTime.now().format(dtf));
//			board.setMember(member);
//			boardRepositoryInterface.save(board);
//		});
//	}
}
