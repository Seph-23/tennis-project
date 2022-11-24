package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResultStatus implements ResultStatusMapper {

    RESULTYES("결과등록완료"),
    RESULTNO("결과미등록");

    @Getter
    private final String title;
}