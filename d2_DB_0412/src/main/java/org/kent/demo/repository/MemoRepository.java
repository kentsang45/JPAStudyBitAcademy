package org.kent.demo.repository;

import org.kent.demo.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JapRepository<Type, PkType>
public interface MemoRepository extends JpaRepository<Memo, Long> {

    // interface지만 MyBatis의 Mapper처럼 프록시 객체를 만들어준다.

    List<Memo> findByMemoTextContaining(String memoText);

    Page<Memo> findByMemoTextContaining(String memoText, Pageable page);

    // Query Annotation 사용해보기
    // : 사용
    @Query("select m from Memo m where m.memoText like :keyword ")
    List<Memo> getList(String keyword);

    // @Param 사용
    @Query("select m from Memo m where m.memoText like :keyword ")
    List<Memo> getList2(@Param("keyword")String memoText);


    // @Param 사용
    @Query("select m from Memo m where m.memoText like concat('%', :keyword , '%')")
    Page<Memo> getPagedList(@Param("keyword")String memoText, Pageable page);
}
