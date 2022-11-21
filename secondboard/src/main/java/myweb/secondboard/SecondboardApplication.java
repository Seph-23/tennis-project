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
import myweb.secondboard.dto.MatchingSaveForm;
import myweb.secondboard.repository.*;
import myweb.secondboard.service.MatchingService;
import myweb.secondboard.service.MemberService;
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

	 /**
   * 멤버 정보
   */
//  @Order(1)
//  @Bean
//  public CommandLineRunner initMember(MemberRepository memberRepository,
//    RecordRepository recordRepository) {
//    return args -> IntStream.rangeClosed(1, 500).forEach(i -> {
//      try {
//        //생성 객체 준비
//        Member member = new Member();
//        PasswordEncrypt passwordEncrypt = new PasswordEncrypt();
//        Record record = Record.createRecord();
//
//        member.setLoginId("testtest" + i);
//        member.setPassword(passwordEncrypt.encrypt("testtest" + i));
//        member.setNickname("test" + i);
//        member.setEmail("test" + i + "@test.com");
//        member.setBirthday("19950307");
//        member.setPhoneNumber("01021219" + String.format("%03d", i));
//        member.setProvider(Provider.GOGOTENNIS);
//        member.setRole(Role.MEMBER);
//        member.setTier(Tier.IRON);
//        member.setRecord(record);
//
//        if (i % 2 == 1) {
//          member.setGender(Gender.MALE);
//        } else {
//          member.setGender(Gender.FEMALE);
//        }
//
//        recordRepository.save(record);
//        memberRepository.save(member);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    });
//  }
//
//  //내일, 복식, 실내 매치 생성
//  @Order(2)
//  @Bean
//  public CommandLineRunner initMatching(MemberService memberService,
//                                        MatchingService matchingService) {
//    return args -> IntStream.rangeClosed(1, 10).forEach(i -> {
//      try {
//        Member member = memberService.findById(Long.valueOf(i));
//        MatchingSaveForm form = new MatchingSaveForm();
//        form.setTitle("테스트 매치: 복식 실내 - " + i);
//        form.setContent("복식 실내: 테스트 매치 " + i + "번 입니다.");
//        form.setMatchingDate(LocalDate.now().plusDays(1));
//        form.setMatchingStartTime("14:40");
//        form.setMatchingEndTime("16:40");
//        form.setMatchingType(MatchingType.DOUBLE);
//        form.setCourtType(CourtType.INDOOR);
//        form.setBeforeHour("13:40");
//        form.setBeforeTwoHour("12:40");
//
//        if (i % 2 == 1) {
//          form.setPlace("잠원한강공원 테니스장");
//          form.setLat("37.52027097095218");
//          form.setLng("127.01107673077598");
//        } else {
//          form.setPlace("목동테니스장");
//          form.setLat("37.52803255610589");
//          form.setLng("126.87778133983099");
//        }
//
//        matchingService.addMatching(form, member);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    });
//  }
//
//  public static int rand(int min, int max)
//	{
//		if (min > max || (max - min + 1 > Integer.MAX_VALUE)) {
//			throw new IllegalArgumentException("Invalid range");
//		}
//
//		return new Random().nextInt(max - min + 1) + min;
//	}
//
//	public static int generate() {
//		int r;
//		do {
//			r = 5 * (rand(1, 5) - 1) + rand(1, 5);
//		} while (r > 7);
//
//		return r;
//	}
//
//	@Order(3)
//	@Bean
//	public CommandLineRunner test(LocalRepository localRepository, TournamentRepository tournamentRepository) {
//        return args -> {
//            //==Local (지역) 테스트 데이터==//
//            List<String> locals = new ArrayList<>();
//            for (String s : Arrays.asList("서울", "경기", "강원", "경상", "전라", "충청", "제주")) {
//                locals.add(s);
//            }
//
//            for (int i = 1; i <= 7; i++) {
//                Local local = new Local();
//                local.setName(locals.get(i - 1));
//                localRepository.save(local);
//            }
//        };
//    }
//
////  == 관리자 데이터 11.16(수) 테스트 완료 ==//
//  @Order(4)
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