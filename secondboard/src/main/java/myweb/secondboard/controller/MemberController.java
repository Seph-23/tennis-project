package myweb.secondboard.controller;

        import java.io.IOException;
        import java.nio.charset.StandardCharsets;
        import java.security.NoSuchAlgorithmException;
        import java.util.Map;
        import java.util.Optional;
        import javax.validation.Valid;
        import lombok.RequiredArgsConstructor;
        import lombok.extern.slf4j.Slf4j;
        import myweb.secondboard.domain.Member;
        import myweb.secondboard.dto.FindPasswordForm;
        import myweb.secondboard.dto.MemberSaveForm;
        import myweb.secondboard.dto.MemberUpdateForm;
        import myweb.secondboard.dto.UpdatePasswordForm;
        import myweb.secondboard.service.MemberService;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.validation.annotation.Validated;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.ModelAttribute;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/new")
  public String signUpPage(Model model) {
    model.addAttribute("member", new MemberSaveForm());
    return "/members/signUpPage";
  }

  @PostMapping("/new")
  public String signUp(@Validated @ModelAttribute("member") MemberSaveForm form,
                       BindingResult bindingResult) throws NoSuchAlgorithmException {
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/members/signUpPage";
    }
    //SignUp Success Logic
    memberService.signUp(form);
    return "redirect:/";
  }

  // 내정보
  @GetMapping("/profile/{memberId}")
  public String profileHome(@PathVariable("memberId") Long memberId, Model model) {

    Member member = memberService.findById(memberId);
    System.out.println("이제 프로필 출력할께요 ==>"+ member.getFile());

    if(member.getFile() != null){
      String src = new String(member.getFile().getSaveImg(), StandardCharsets.UTF_8);
      System.out.println("이제 변경된 프로필 이미지는? ==>"+member.getFile().toString());
      model.addAttribute("src", src);
    }

    model.addAttribute("member", member);

    // 내정보 수정 모달용
    MemberUpdateForm form = new MemberUpdateForm();
    form.setId(member.getId());
    form.setNickname(member.getNickname());
    form.setIntroduction(member.getIntroduction());
    model.addAttribute("form",form);

    return "/members/profileHome";
  }

  @PostMapping("/profileUpdate")
  public String profileUpdate(@Validated @ModelAttribute("form")MemberUpdateForm form,
                              BindingResult bindingResult, Model model, MultipartFile file) throws IOException {

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/home";
    }

    System.out.println("controller에서 이미지 받았습니다. ==>"+file.isEmpty());

    Long memberId = memberService.updateMember(form, file);


    return "redirect:/members/profile/"+memberId;
  }

  //==비밀번호 찾기==//
  @GetMapping("/find/password")
  public String findPassword(Model model) {
    model.addAttribute("form", new FindPasswordForm());
    return "/members/findPassword";
  }

  //==비밀번호 인증 : 휴대폰 인증 사용==//
  @PostMapping("/find")
  public String findPassword(@Valid @ModelAttribute("form") FindPasswordForm form, BindingResult bindingResult) {

    System.out.println("form.getLoginId() = " + form.getLoginId());
    Optional<Member> findMemberByLoginId = memberService.findByLoginId(form.getLoginId());
    Long updateMemberLoginId;
    if (findMemberByLoginId.isPresent()) {
      updateMemberLoginId = findMemberByLoginId.get().getId();
    } else {
      bindingResult.reject("NotFoundLoginMember", "입력하신 아이디를 찾을 수 없습니다.");
      return  "/members/findPassword";
    }

    return "redirect:/members/find/"+ updateMemberLoginId;
  }

  @GetMapping("/find/{updateMemberLoginId}")
  public String AuthenticateMember(Model model, @ModelAttribute("form") FindPasswordForm form, BindingResult bindingResult,
                                   @PathVariable("updateMemberLoginId") Long updateMemberLoginId) {
    Member updateMember = memberService.findById(updateMemberLoginId);
    String phoneNumber = updateMember.getPhoneNumber();

    //01012341234 -> *1*1*
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < phoneNumber.length(); i++) {
      if (i % 3 == 0) {
        builder.append(phoneNumber.charAt(i));
      }
      else {
        builder.append('*');
      }
    }
    model.addAttribute("loginId",updateMember.getLoginId());
    model.addAttribute("phoneNum",builder.toString());
    model.addAttribute("updateMemberLoginId", updateMemberLoginId);
    return "/members/memberAuthentication";
  }

  @GetMapping("/update/password/{updateMemberLoginId}")
  public String updatePassword(Model model,@PathVariable("updateMemberLoginId") Long updateMemberLoginId ) {
    UpdatePasswordForm form = new UpdatePasswordForm();
    form.setId(updateMemberLoginId); // 155
    System.out.println("form.getId() = " + form.getId());
    model.addAttribute("form", form);

    return "/members/updateMemberPassword";
  }

  @PostMapping("/update")
  public String updatePassword(@Valid @ModelAttribute("form") UpdatePasswordForm form, BindingResult bindingResult)
          throws NoSuchAlgorithmException {
    //==비밀번호 업데이트 로직==//

    Member member = memberService.findById(form.getId()); //변경할 회원
    String updatePassword = form.getUpdatePassword();
    String updatePasswordCheck = form.getUpdatePasswordCheck();

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/members/updateMemberPassword";
    }

    System.out.println("updatePasswordCheck = " + updatePassword);
    System.out.println("updatePasswordCheck = " + updatePasswordCheck);

    if (!updatePassword.equals(updatePasswordCheck)) {
      bindingResult.reject("NotMatchPassword", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      return "/members/updateMemberPassword";
    }

    memberService.updatePassword(form, member);

    return "redirect:/";
  }




}
