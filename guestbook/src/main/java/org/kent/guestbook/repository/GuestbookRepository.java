package org.kent.guestbook.repository;

import org.kent.guestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository
//              JpaRepository<Type, PkType>
        extends JpaRepository<Guestbook, Long>
//                동적 쿼리를 위한 상속...
                , QuerydslPredicateExecutor<Guestbook> {



}
