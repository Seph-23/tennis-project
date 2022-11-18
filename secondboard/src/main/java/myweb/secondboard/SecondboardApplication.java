package myweb.secondboard;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import myweb.secondboard.domain.*;
import myweb.secondboard.domain.Record;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.domain.boards.Question;
import myweb.secondboard.repository.*;
import myweb.secondboard.web.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

/**
 * 11월1일자 복구
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SecondboardApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecondboardApplication.class, args);
  }

	@Bean(name = "uploadPath")
	public String uploadPath() {
		return "/Users/hwang-uichan/Project/tennis-project/secondboard/src/main/resources/static/files";
  }
  @Bean
  JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }

//	@Order(1)
//  @Bean
//	public CommandLineRunner initData(MemberRepository memberRepository,
//		BoardRepository boardRepository, CommentRepository commentRepository,
//			LessonRepository lessonRepository, NoticeRepository noticeRepository,
//			QuestionRepository questionRepository){
//
//		return args -> IntStream.rangeClosed(1, 154).forEach(i -> {
//			try {
//				Member member = new Member();
//				PasswordEncrypt passwordEncrypt = new PasswordEncrypt();
//				member.setLoginId("testtest" + i);
//				member.setPassword(passwordEncrypt.encrypt("testtest" + i + "!"));
//				member.setNickname("test" + i);
//				member.setEmail("test" + i + "@gmail.com");
//				member.setBirthday("19951126");
//				member.setPhoneNumber("01021219" + String.format("%03d", i));
//				member.setGender(Gender.MALE);
//				member.setProvider(Provider.GOGOTENNIS);
//				member.setRole(Role.MEMBER);
//				member.setTier(Tier.BRONZE);
//				member.setRecord(new Record());
//				member.setFile(new File());
//				memberRepository.save(member);
//
//				Board board = new Board();
//				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
//				board.setTitle("자유게시판 " + i);
//				board.setAuthor(member.getNickname());
//				board.setContent("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet atque labore obcaecati repellendus sed? Amet dolores dolorum iusto laboriosam natus optio veniam! A aliquid architecto dicta enim maiores mollitia, neque.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet atque labore obcaecati repellendus sed? Amet dolores dolorum iusto laboriosam natus optio veniam! A aliquid architecto dicta enim maiores mollitia, neque.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet atque labore obcaecati repellendus sed? Amet dolores dolorum iusto laboriosam natus optio veniam! A aliquid architecto dicta enim maiores mollitia, neque.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet atque labore obcaecati repellendus sed? Amet dolores dolorum iusto laboriosam natus optio veniam! A aliquid architecto dicta enim maiores mollitia, neque.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet atque labore obcaecati repellendus sed? Amet dolores dolorum iusto laboriosam natus optio veniam! A aliquid architecto dicta enim maiores mollitia, neque.");
//				board.setViews(0);
//        board.setLikeCount(0L);
//				board.setCreatedDate(LocalDateTime.now().format(dtf));
//				board.setModifiedDate(LocalDateTime.now().format(dtf));
//				board.setMember(member);
//
//				Lesson lesson = new Lesson();
//				lesson.setTitle("레슨 " + i);
//				lesson.setAuthor(member.getNickname());
//				lesson.setContent("test lesson");
//				lesson.setViews(0);
//				lesson.setCreatedDate(LocalDateTime.now().format(dtf));
//				lesson.setModifiedDate(LocalDateTime.now().format(dtf));
//				lesson.setMember(member);
//
//				Notice notice = new Notice();
//				notice.setTitle("공지사항 " + i);
//				notice.setAuthor(member.getNickname());
//				notice.setContent("test notice");
//				notice.setViews(0);
//        notice.setLikeCount(0L);
//				notice.setCreatedDate(LocalDateTime.now().format(dtf));
//				notice.setModifiedDate(LocalDateTime.now().format(dtf));
//				notice.setMember(member);
//
//				Question question = new Question();
//				question.setTitle("Q&A " + i);
//				question.setAuthor(member.getNickname());
//				question.setContent("test question");
//				question.setViews(0);
//				question.setCreatedDate(LocalDateTime.now().format(dtf));
//				question.setModifiedDate(LocalDateTime.now().format(dtf));
//				question.setMember(member);
//				question.setCondition(AnswerCondition.ACCEPT);
//
//				Comment comment = new Comment();
//				comment.setContent("testtest" + i);
//				comment.setAuthor(member.getNickname());
//				comment.setCreatedDate(LocalDateTime.now().format(dtf));
//				comment.setModifiedDate(LocalDateTime.now().format(dtf));
//				comment.setMember(member);
//				comment.setBoard(board);
//
//				board.setComments(commentRepository.findComments(board.getId()));
//
//				boardRepository.save(board);
//				lessonRepository.save(lesson);
//				noticeRepository.save(notice);
//				questionRepository.save(question);
//				commentRepository.save(comment);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}
//
	public static int rand(int min, int max)
	{
		if (min > max || (max - min + 1 > Integer.MAX_VALUE)) {
			throw new IllegalArgumentException("Invalid range");
		}

		return new Random().nextInt(max - min + 1) + min;
	}

	public static int generate() {
		int r;
		do {
			r = 5 * (rand(1, 5) - 1) + rand(1, 5);
		} while (r > 7);

		return r;
	}

	@Order(2)
	@Bean
	public CommandLineRunner test(LocalRepository localRepository, TournamentRepository tournamentRepository) {
        return args -> {
            //==Local (지역) 테스트 데이터==//
            List<String> locals = new ArrayList<>();
            for (String s : Arrays.asList("서울", "경기", "강원", "경상", "전라", "충청", "제주")) {
                locals.add(s);
            }

            for (int i = 1; i <= 7; i++) {
                Local local = new Local();
                local.setName(locals.get(i - 1));
                localRepository.save(local);
            }
            //==Tournament(대회) 테스트 데이터==//
            for (int i = 1; i <= 16; i++) {
                Tournament tournament = new Tournament();

                tournament.setCompStartDate(LocalDate.now());
                tournament.setCompEndDate(LocalDate.now());
                tournament.setApplicationStartDate(LocalDate.now());
                tournament.setApplicationEndDate(LocalDate.now());
                String url = "https://mdbootstrap.com/img/new/standard/nature";
                Random random = new Random();
                int a = random.nextInt(6) + 184;
                tournament.setImage(url + "/" + a + ".jpg");
                tournament.setPlace("올림픽공원");
                tournament.setTitle("대회명" + i);

                int val = generate();
                tournament.setLocal(localRepository.findById((long) val).get());
                tournamentRepository.save(tournament);
            }
        };
    }
//
//	}
//	@Order(3)
//	@Bean
//	public CommandLineRunner initMatching(MatchingRepository matchRepository,
//		MemberRepository memberRepository, PlayerRepository playerRepository) {
//		return args -> IntStream.rangeClosed(1, 154).forEach(i -> {
//			Matching matching = new Matching();
//			Player player = new Player();
//			Member member = memberRepository.findById(Long.valueOf(i)).get();
//			if (i % 2 == 0) {
//				matching.setTitle("매치 test" + i);
//				matching.setAuthor(member.getNickname());
//				matching.setMatchingDate(LocalDate.of(2022, 12, 12));
//				matching.setMatchingStartTime("16:30");
//				matching.setMatchingEndTime("18:30");
//				matching.setMatchingType(MatchingType.DOUBLE);
//				matching.setCourtType(CourtType.INDOOR);
//				matching.setPlace("한남테니스장");
//				matching.setMember(member);
//				matching.setMatchingCondition(MatchingCondition.AVAILABLE);
//				matching.setLat("37.546108538841295");
//				matching.setLng("127.00368899119701");
//
//			} else if (i % 2 == 1) {
//				matching.setTitle("매치 test" + i);
//				matching.setAuthor(member.getNickname());
//				matching.setMatchingDate(LocalDate.of(2022, 12, 12));
//				matching.setMatchingStartTime("13:30");
//				matching.setMatchingEndTime("15:30");
//				matching.setMatchingType(MatchingType.SINGLE);
//				matching.setCourtType(CourtType.OUTDOOR);
//				matching.setPlace("한남테니스장");
//				matching.setMember(member);
//				matching.setMatchingCondition(MatchingCondition.AVAILABLE);
//				matching.setLat("37.546108538841295");
//				matching.setLng("127.00368899119701");
//			}
//			matchRepository.save(matching);
//
//			player.setMatching(matching);
//			player.setMember(member);
//			player.setTeam(Team.A);
//			playerRepository.save(player);
//
//		});
//	}

//  == 관리자 데이터 11.16(수) 테스트 완료 ==//
//  @Order(1)
//  @Bean
//  public CommandLineRunner initAdminMember(MemberRepository memberRepository) {
//    return  args -> {
//      Member member = new Member();
//      PasswordEncrypt passwordEncrypt = new PasswordEncrypt();
//      member.setLoginId("adminadmin");
//      member.setPassword(passwordEncrypt.encrypt("adminadmin!"));
//      member.setNickname("admin");
//      member.setEmail("admin" + "@gmail.com");
//      member.setBirthday("19951126");
//      member.setPhoneNumber("01012345678");
//      member.setGender(Gender.MALE);
//      member.setProvider(Provider.GOGOTENNIS);
//      member.setRole(Role.ADMIN);
//      member.setTier(Tier.BRONZE);
//      memberRepository.save(member);
//    };
//  }

}