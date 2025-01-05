package com.ll.wiseSaying;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FirstTest {

    @Test
    void t1() {
        int rst = 1;
        assertThat(rst).isEqualTo(1);
    }

    @Test
    void t2() {
//        App app = new App();
//        app.run();

        // aaa가 출력되는가?
        // assertThat(result).isEqualTo("aaa");
    }

    @Test
    void t3() {
        String out = TestBot.run("");
        assertThat(out)
                .contains("명령 )")
                .contains("명언앱을 종료합니다.");

        // 출력값을 체크
    }

    @Test
    @DisplayName("명령을 여러번 입력할 수 있다.")
    void t4() {
        String out = TestBot.run("""
                등록
                등록
                종료
                """);

        // 명령 ) 횟수를 세서 검증해야 함.

        // 전체 문자열에서 "명령 )"의 등장 횟수 계산
        long count = out.split("명령 \\)").length - 1;

        // 횟수를 검증
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("앱 시작시 '== 명언 앱 ==' 출력")
    void t5() {
        String out = TestBot.run("");

        // 문자열 순서 적용 (앞 문자열 뒤에 뒤 문자열 나와야 함, but 그 중간에 무슨 문자열이 와도 상관없음)
        assertThat(out)
                .containsSubsequence("== 명언 앱 ==", "명언앱을 종료합니다.");

    }

    @Test
    @DisplayName("등록 - 명언 1개 입력")
    void t6() {
        String out = TestBot.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .containsSubsequence("명언 : ", "작가 : ");

    }
}
