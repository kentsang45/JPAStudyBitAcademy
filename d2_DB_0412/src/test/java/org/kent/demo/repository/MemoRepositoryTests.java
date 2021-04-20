package org.kent.demo.repository;



import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.kent.demo.entity.Memo;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class MemoRepositoryTests {
    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void test1(){
       log.info(memoRepository.getClass().getName());

    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder()
                    .memoText("SAMPLE_MEMO_" + i)
                    .build();

            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){
        Long mno = 101L;

        Optional<Memo> result = memoRepository.findById(mno);
        log.info("=====================");

        if(result.isPresent()){
            log.info(result.get());
        } else{
            log.info("결과가 없습니다. : " + mno);
        }
    }

    @Test
    @Transactional
    public void testSelect2(){
        Long mno = 101L;

        try {
            Memo result = memoRepository.getOne(mno);
            log.info("=====================");
            log.info(result);

        }catch(Exception e){
            log.info("결과가 없습니다. : " + mno);
        }
    }

    @Test
    public void testUpdate(){
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);

        if(result.isPresent()){
            Memo memo = result.get();
            memo.changeText("UPDATE_100");
            memoRepository.save(memo);
        } else{
            log.info("결과가 없습니다. : " + mno);
        }

    }

    @Test
    public void testPage1(){
        Sort sort1 = Sort.by("mno").descending();

        Pageable page = PageRequest.of(0, 200, sort1);

        Page<Memo> list = memoRepository.findAll(page);

        log.info(list);

        // 내용물
        list.getContent().forEach(m->log.info(m));
    }

    @Test
    public void testQuery(){
        List<Memo> list = memoRepository.findByMemoTextContaining("1");
        list.forEach(m->log.info(m));
    }
    @Test
    public void testQuery2(){
        // Sort.by("<컬럼이름>")
        Pageable page = PageRequest.of(0, 10, Sort.by("mno"));

        Page<Memo> result = memoRepository.findByMemoTextContaining("1", page);

        result.getContent().forEach(m->log.info(m));
    }

    @Test
    public void testQuery3(){
        // Sort.by("<컬럼이름>")

//        memoRepository.getList("1").forEach(m->log.info(m));
//        memoRepository.getList("%1%").forEach(m->log.info(m));
        // memoRepository.getList3("1").forEach(m->log.info(m));
        memoRepository.getPagedList("1", PageRequest.of(0, 10, Sort.by("mno"))).forEach(m->log.info(m));
    }
}
