package org.kent.guestbook.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.kent.guestbook.entity.Guestbook;
import org.kent.guestbook.entity.QGuestbook;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
@Log4j2
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void makeDummyData() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook gb = Guestbook.builder().title("TITLE_" + i)
                    .content("CONTENT_" + i)
                    .writer("user" + (i % 10))
                    .build();

            log.info(guestbookRepository.save(gb));
        });
    }

    @Test
    public void testUpdate() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if (result.isPresent()) {
            Guestbook gb = result.get();

            gb.changeContent("Changed Content...");
            gb.changeTitle("Changed Title...");

            log.info(guestbookRepository.save(gb));
        }
    }

    // 단일 조건 검색
    @Test
    public void testSimpleSearch() {
        Pageable page = PageRequest.of(0, 10, Sort.by("gno").descending());


        QGuestbook qGuestbook = QGuestbook.guestbook;

        // keyword 잡기
        String keyword = "1";

        // 다양한 검색 조건을 위해
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 제목에 1이 들어있는 Guestbook 찾기
        // title like ? 와 같다...
        BooleanExpression expression = qGuestbook.title.contains(keyword);

        // 이제 조건문이 만들어짐...
        booleanBuilder.and(expression);

        // 검색 + 페이징 완료
        Page<Guestbook> resultList = guestbookRepository.findAll(booleanBuilder, page);

        resultList.forEach(g -> log.info(g));
    }

    @Test
    public void testDualSearch() {
        Pageable page = PageRequest.of(0, 10, Sort.by("gno").descending());

        String keyword = "1";
        String[] arr = {"t", "c", "w"};

        // QDomain
        QGuestbook qGuestbook = QGuestbook.guestbook;

        // total builder
        BooleanBuilder totalBuilder = new BooleanBuilder();

        // condition builder
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        // index builder
        BooleanBuilder indexBuilder = new BooleanBuilder();


        Arrays.stream(arr).forEach(str -> {
            log.info(str);

            switch (str) {
                case "t":
                    conditionBuilder.or(qGuestbook.title.contains(keyword));
                    break;
                case "c":
                    conditionBuilder.or(qGuestbook.content.contains(keyword));
                    break;
                case "w":
                    conditionBuilder.or(qGuestbook.writer.contains(keyword));
                    break;
                default:
                    break;
            }
        });


        indexBuilder.and(qGuestbook.gno.gt(0L));

        // builder 통합 작업
        totalBuilder.and(conditionBuilder);
        totalBuilder.and(indexBuilder);

        // 마지막 builder로 pageList 만들기
        guestbookRepository.findAll(totalBuilder, page).forEach(g -> log.info(g));


    }

}
