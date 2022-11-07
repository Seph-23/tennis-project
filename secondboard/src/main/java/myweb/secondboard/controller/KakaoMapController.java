package myweb.secondboard.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoMapController {

  @ResponseBody
  @PostMapping("/kakaoMap/addPlace")
  public JSONObject markerLocation(@RequestParam Map<String, Object> param, Model model) {
    JSONObject result = new JSONObject();
    System.out.println("param.get(\"lat\") = " + param.get("lat"));
    System.out.println("param.get(\"lon\") = " + param.get("lng"));

    model.addAttribute("lat", param.get("lat").toString());

    result.put("result", "success");
    return result;
  }
}
