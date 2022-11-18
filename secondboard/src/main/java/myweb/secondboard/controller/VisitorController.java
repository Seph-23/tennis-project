package myweb.secondboard.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Club;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.ClubService;
import myweb.secondboard.service.VisitorService;
import myweb.secondboard.web.SessionConst;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/club/visitor")
public class VisitorController {

    private final ClubService clubService;
    private final VisitorService visitorService;


    @PostMapping("/visitorAdd/{clubId}")
    public JSONObject visitorAdd(@PathVariable("clubId") Long clubId,
                                 @RequestParam Map<String, Object> param, HttpServletRequest request) {

        JSONObject result1 = new JSONObject();

        String content = param.get("content").toString();
        if (content.length() < 1 || content.length() > 100) {
            result1.put("result", "validate");
            return result1;
        }

        Member member = (Member) request.getSession(false)
                .getAttribute(SessionConst.LOGIN_MEMBER);
        Club club = clubService.findOne(clubId);
        visitorService.save(param, club, member);

        result1.put("result", "success");
        return result1;

    }

    @PostMapping("/visitorUpdate/{visitorId}")
    public JSONObject visitorUpdate(@PathVariable("visitorId") Long visitorId,
                                    @RequestParam Map<String, Object> param) {
        JSONObject result = new JSONObject();
        String content = param.get("content").toString();
        if (content.length() < 1 || content.length() > 100) {
            result.put("result", "validate");
            return result;
        }

        visitorService.updateVisitor(visitorId, param);
        result.put("result", "success");
        return result;

    }

    @PostMapping("/visitorUpdateCancel/{visitorID}")
    public JSONObject visitorUpdateCancel(@PathVariable("visitorID") Long visitorId){
        JSONObject result = new JSONObject();

        visitorService.updateVisitorCancle(visitorId);
        result.put("result", "success");
        return result;
    }

    @PostMapping("/visitorDelete/{visitorId}")
    public JSONObject visitorDelete(@PathVariable("visitorId") Long visitorId){
        JSONObject result = new JSONObject();

        visitorService.deleteById(visitorId);

        result.put("result", "success");
        return result;
    }


}
