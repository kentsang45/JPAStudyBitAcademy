package org.kent.board.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.kent.board.entity.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    // DUMMY DATE 100개
    @Test
    public void testInsertMembers(){

        IntStream.rangeClosed(1,100).forEach(m->{
            Member member = Member.builder()
                    .email("USER_" +m+"@aaa.com")
                    .pw("1111")
                    .name("USERNAME_" +m)
                    .build();

            memberRepository.save(member);
        });

    }

}
