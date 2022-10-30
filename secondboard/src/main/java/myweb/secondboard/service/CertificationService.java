package myweb.secondboard.service;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificationService {

  @Transactional
  public void certifiedPhoneNumber(String phoneNumber, String cerNum) {

    String api_key = "NCSWIXRHSS3XZX8Z";
    String api_secret = "AD58F4OMBVC0I0TNUMUI0KCTLEMP0VDD";
    Message coolsms = new Message(api_key, api_secret);

    // 4 params(to, from, type, text) are mandatory. must be filled
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("to", phoneNumber);    // 수신전화번호
    params.put("from", "01087524626");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
    params.put("type", "SMS");
    params.put("text", "GOGOTENNIS 휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
    params.put("app_version", "test app 1.2"); // application name and version

    try {
      JSONObject obj = (JSONObject) coolsms.send(params);
      System.out.println(obj.toString());
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    } catch (CoolsmsException e) {
      throw new RuntimeException(e);
    }

  }
}
