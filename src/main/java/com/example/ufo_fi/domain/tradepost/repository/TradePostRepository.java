package com.example.ufo_fi.domain.tradepost.repository;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePostRepository extends JpaRepository<TradePost, Long>, TradePostQueryDsl {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select tp from TradePost tp where tp.id = :postId")
    Optional<TradePost> findByIdWithLock(@Param("postId") Long postId);

    List<TradePost> findAllByUser(User readUser);

    @Query("""
    SELECT DISTINCT tp
    FROM TradePost tp
    JOIN FETCH tp.reports r
    GROUP BY tp
    HAVING COUNT(r) >= 3
    ORDER BY tp.createdAt ASC
    """)
    List<TradePost> findReportedPostsWithAtLeastThreeReports();
}
