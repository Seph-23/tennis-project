package myweb.secondboard.controller;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.service.CertificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsApiController {

  private final CertificationService certificationService;

  @GetMapping("/check/sendSMS")
  public String sendSMS(String phoneNumber) {

    Random rand  = new Random();
    String numStr = "";
    for(int i=0; i<4; i++) {
      String ran = Integer.toString(rand.nextInt(10));
      numStr+=ran;
    }

    System.out.println("수신자 번호 : " + phoneNumber);
    System.out.println("인증번호 : " + numStr);
    certificationService.certifiedPhoneNumber(phoneNumber,numStr);
    return numStr;
  }

}
