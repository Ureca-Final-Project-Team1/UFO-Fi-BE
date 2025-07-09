package com.example.ufo_fi.domain.tradepost.repository;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradePostRepository extends JpaRepository<TradePost, Long> {

    @Query("select t from TradePost t where t.isDelete = false and t.createdAt < :cursor ORDER BY t.createdAt DESC ")
    List<TradePost> findByCursorPaging(@Param("cursor") LocalDateTime cursor, Pageable pageable);

}
