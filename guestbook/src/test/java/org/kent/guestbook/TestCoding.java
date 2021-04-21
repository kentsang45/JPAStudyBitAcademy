package org.kent.guestbook;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log4j2
public class TestCoding {

    @Test
    public void test2() {
        log.info("TEST2");
        int result = reverse(1534236469);
        log.info("result : " + result);
        int a = 0;
    }

    public int reverse(int x) {
        Long digit = 1L;
        List<Long> resultList = new ArrayList();


        while (true) {
            // 129 / 1 = 129
            // 129 / 10 = 12
            // 129 / 100 = 1
            // 129 / 1000 = 0
            Long result = x / digit;

            // 그 자리수라는 뜻
            if (0 == result) {
                digit /= 10;
                break;
            }
            digit *= 10;

            // 12 % 10 = 2
            // 129 % 10 = 9
            resultList.add(result % 10L);
        }
        // 9, 2,
        Long result = 0L;

        for (int i = 0; i < resultList.size(); ++i) {
            result += resultList.get(i) * digit;
            digit /= 10;
        }

        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            result = 0L;
        }

        int result1 = result.intValue();
        return result1;
    }

    @Test
    public void testkakao1() {
        // https://programmers.co.kr/learn/courses/30/lessons/72410?language=java
        log.info("TEST2");
        String result = solution(".TEST_00..11$%^&*().TESTTESTTESTTEST");
        log.info("result : " + result);
        log.info("resultSize : " + result.length());
        int a = 0;
    }

    public String solution(String new_id) {
        // 1단계 = 소문자
        new_id = new_id.toLowerCase();
        String answer = "";

        // int totalLength = new_id.length() >= 16 ? 16 : new_id.length() ;
        int totalLength = new_id.length();

        for(int i =0; i < totalLength; ++i) {
            char temp = new_id.charAt(i);
            int length = answer.length()-1;
            // char beforeChar =    answer.charAt(answer.length()-2)

            // 4단계 = 점이 처음이나 끝
            if((0 == i || totalLength-1 == i) && temp == '.'){
                continue;
            }

            // 3단계 = 연속되는 점
            if(0 <= length && answer.charAt(length) == '.' && temp == '.'){
                continue;
            }

            // 2단계 = 기호
            if((temp >= 97 && temp <= 122) || temp == '-' || temp == '.' || temp == '_' || (temp >= '0' && temp <= '9')){
                answer += temp;
            }
        }

        // 5단계 = 비어있다면 a
        if(answer.isEmpty()){
            answer = "a";
        }

        // 6단계 = 길이 15
        answer = answer.substring(0, 15);

        //  

        return answer;
    }
}
