package com.example.ufo_fi.domain.tradepost.repository;

import static com.example.ufo_fi.domain.tradepost.entity.QTradePost.tradePost;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostFilterReq;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TradePostQueryDslImpl implements TradePostQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<TradePost> findByCursorPaging(LocalDateTime cursor, Long lastId,
        Pageable pageable) {

        List<TradePost> content = queryFactory
            .selectFrom(tradePost)
            .where(
                tradePost.isDelete.isFalse(),
                tradePost.reportCount.lt(3),
                cursorPaging(cursor, lastId)
            )
            .orderBy(tradePost.createdAt.desc(), tradePost.id.desc())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<TradePost> searchWithPagination(TradePostFilterReq condition) {

        int pageSize = condition.getSize();

        List<TradePost> tradePosts = queryFactory
            .selectFrom(tradePost)
            .where(
                carrierEq(condition.getCarrier()),
                rangePrice(condition.getMinTotalPrice(), condition.getMaxTotalPrice()),
                rangeGb(condition.getMinCapacity(), condition.getMaxCapacity()),
                cursorCondition(condition.getCursorCreatedAt(), condition.getCursorId())
            )
            .orderBy(tradePost.createdAt.desc(), tradePost.id.desc())
            .limit(pageSize + 1)
            .fetch();

        boolean hasNext = tradePosts.size() > pageSize;

        if (hasNext) {
            tradePosts.remove(pageSize);
        }

        return new SliceImpl<>(tradePosts, Pageable.ofSize(pageSize), hasNext);

    }

    @Override
    public List<TradePost> findCheapestCandidates(TradePostBulkPurchaseReq condition,
        Carrier carrier, MobileDataType mobileDataType) {

        return queryFactory
            .selectFrom(tradePost)
            .where(
                tradePost.isDelete.isFalse(),
                tradePost.tradePostStatus.eq(TradePostStatus.SELLING),
                tradePost.carrier.eq(carrier),
                tradePost.mobileDataType.eq(mobileDataType),
                tradePost.pricePerUnit.loe(condition.getMaxPrice())
            )
            .orderBy(
                tradePost.pricePerUnit.asc(),
                tradePost.createdAt.desc()
            )
            .limit(100)
            .fetch();
    }

    private BooleanExpression cursorPaging(LocalDateTime cursor, Long lastId) {

        if (cursor == null || lastId == null) {
            return null;
        }

        return tradePost.createdAt.lt(cursor)
            .or(tradePost.createdAt.eq(cursor).and(tradePost.id.lt(lastId)));
    }

    private BooleanExpression cursorCondition(LocalDateTime createdAt, Long id) {

        if (createdAt == null || id == null) {
            return null;
        }

        return tradePost.createdAt.lt(createdAt)
            .or(tradePost.createdAt.eq(createdAt).and(tradePost.id.lt(id)));
    }

    private BooleanExpression carrierEq(Carrier carrier) {

        if (carrier == null) {
            return null;
        }

        return tradePost.carrier.eq(carrier);
    }

    private BooleanExpression rangePrice(Integer minTotalPrice, Integer maxTotalPrice) {

        if (minTotalPrice == null && maxTotalPrice == null) {

            return null;
        }

        if (minTotalPrice != null && maxTotalPrice != null) {

            return tradePost.totalPrice.goe(minTotalPrice)
                .and(tradePost.totalPrice.loe(maxTotalPrice));
        }

        if (minTotalPrice != null) {

            return tradePost.totalPrice.goe(minTotalPrice);
        }

        return tradePost.totalPrice.loe(maxTotalPrice);
    }

    private BooleanExpression rangeGb(Integer minCapacity, Integer maxCapacity) {

        if (minCapacity == null && maxCapacity == null) {

            return null;
        }

        if (minCapacity != null && maxCapacity != null) {

            return tradePost.sellMobileDataCapacityGb.goe(minCapacity)
                .and(tradePost.sellMobileDataCapacityGb.loe(maxCapacity));
        }

        if (minCapacity != null) {

            return tradePost.sellMobileDataCapacityGb.goe(minCapacity);
        }

        return tradePost.sellMobileDataCapacityGb.loe(maxCapacity);
    }
}
