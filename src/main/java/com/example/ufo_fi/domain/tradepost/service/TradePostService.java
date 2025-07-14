package com.example.ufo_fi.domain.tradepost.service;

import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostFilterReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostSearchReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostFilterRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostSearchRes;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.entity.UserAccount;
import com.example.ufo_fi.domain.user.entity.UserPlan;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserAccountRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService {
    private final UserRepository userRepository;
    private final TradePostRepository tradePostRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * 게시물 생성
     * 리팩토링 전
     * 1. 유저를 얻어온다.
     * 2. 유저의 계좌가 없다면 예외 발생
     * 3. 유저의 팔 수 있는 데이터를 가져옴
     * 4. 등록하려는 팔 데이터량을 가져옴
     * 5. 비교해서, 팔 수 있는것보다 파려는게 많으면 예외
     * 6. TradePost 생성
     * 7. 유저의 팔 수 있는 데이터량 감소
     * 8. total 가격 계산 저장
     * 9. 영속화(저장)
     * 10. return
     *
     * 리팩토링 후
     * 1. User, UserAccount, UserPlan를 fetch join으로 가져옴(이상현상 막기 위해, 비관락)
     * 2. plan은 따로 가져옴. 여러 사람이 조회가 가능하기 때문에 병목 고려, plan 조회는 비관락에서 뺌
     * 3. NullPointerException 검사
     * 4. 나머지 위와 같음
     *
     * 학습 포인트
     * => User라는 루트 엔티티에 여러 서브엔티티 존재 : User로 Join해서 나머지 userAccount같은 서브 엔티티 접근
     * => fetchJoin과 영속 상태
     */
    @Transactional
    public TradePostCommonRes createTradePost(TradePostCreateReq request, Long userId) {
//
//        User user = getUser(userId);
//
//        if (!userAccountRepository.existsByUser(user)) {
//
//            throw new GlobalException(TradePostErrorCode.ACCOUNT_NOT_REGISTERED);
//        }
//
//        int userAvailableData = user.getUserPlan().getSellMobileDataCapacityGb();
//        int requestSellData = request.getSellMobileDataCapacityGb();
//
//        if (requestSellData > userAvailableData) {
//
//            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
//        }
//
//        TradePost tradePost = TradePost.of(request, false, false, TradePostStatus.SELLING, 0, user);
//
//        user.getUserPlan().subtractSellableDataAmount(requestSellData);
//        tradePost.calculateTotalPrice();// total 가격 저장
//
//        TradePost savedTradePost = tradePostRepository.save(tradePost);
//
//        return new TradePostCommonRes(savedTradePost.getId());

        //리팩토링 코드 총 3번의 쿼리 => fetch join으로 2번의 쿼리로 리팩토링 나중에 이런식으로 리팩토링하면 좋을 듯 합니다!
        User user = userRepository.findUserWithUserPlanAndUserAccountWithPessimisticLock(userId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));

        UserPlan userPlan = user.getUserPlan();
        if(userPlan == null) {
            throw new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND);
        }

        Plan plan = userPlan.getPlan();
        if(plan == null){
            throw new GlobalException(TradePostErrorCode.PLAN_NOT_FOUND);
        }

        UserAccount userAccount = user.getUserAccount();
        if(userAccount == null) {
            throw new GlobalException(TradePostErrorCode.ACCOUNT_NOT_REGISTERED);
        }

        int userAvailableData = userPlan.getSellableDataAmount();
        int requestSellData = request.getSellMobileDataCapacityGb();
        if (requestSellData > userAvailableData) {
            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        TradePost tradePost = TradePost.of(request, false, false, TradePostStatus.SELLING, 0, user, plan);
        userPlan.subtractSellableDataAmount(requestSellData);
        tradePost.calculateTotalPrice();// total 가격 저장
        TradePost savedTradePost = tradePostRepository.save(tradePost);
        return new TradePostCommonRes(savedTradePost.getId());
    }


    /**
     * 게시물 조회(cursor 기반 최신순 정렬)
     */
    public TradePostSearchRes readTradePostList(TradePostSearchReq request, Long userId) {

        Pageable pageable = PageRequest.of(0, request.getSize());

        Slice<TradePost> posts = tradePostRepository.findByCursorPaging(
            request.getCursor(), request.getLastId(), pageable
        );

        LocalDateTime nextCursor = null;
        Long nextLastId = null;
        List<TradePost> postContent = posts.getContent();

        if (!postContent.isEmpty()) {
            TradePost lastPost = postContent.get(postContent.size() - 1);
            nextCursor = lastPost.getCreatedAt();
            nextLastId = lastPost.getId();
        }

        return TradePostSearchRes.of(posts, nextCursor, nextLastId);
    }

    /**
     * 게시물 필터링(cursor 기반 유저가 원하는대로 조회)
     */
    @Transactional
    public TradePostFilterRes readFilterList(TradePostFilterReq request, Long userId) {

        Slice<TradePost> postSlice = tradePostRepository.searchWithPagination(request);

        return TradePostFilterRes.from(postSlice);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public TradePostCommonRes updateTradePost(Long postId, TradePostUpdateReq request,
        Long userId) {

        User user = getUser(userId);

        TradePost tradePost = tradePostRepository.findByIdWithLock(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));

        tradePost.verifyOwner(tradePost, user);

        if (request.getSellMobileDataCapacityGb() != null) {
            user.getUserPlan().increaseSellableDataAmount(tradePost.getSellMobileDataCapacityGb());
            user.getUserPlan().subtractSellableDataAmount(request.getSellMobileDataCapacityGb());
        }

        //비교 로직 추가 -> 구매하기

        tradePost.calculateTotalPrice();// total 가격 저장
        tradePost.update(request);

        return new TradePostCommonRes(tradePost.getId());
    }

    /**
     * 게시물 삭제
     */
    @Transactional
    public TradePostCommonRes deleteTradePost(Long postId, Long userId) {

        User user = getUser(userId);

        TradePost tradePost = tradePostRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));

        tradePost.verifyOwner(tradePost, user);

        if (!tradePost.getTradePostStatus().equals(TradePostStatus.SELLING)) {

            throw new GlobalException(TradePostErrorCode.CANNOT_DELETE_NOT_SELLING_POST);
        }

        int dataToRestore = tradePost.getSellMobileDataCapacityGb();
        user.getUserPlan().increaseSellableDataAmount(dataToRestore);

        tradePost.softDeleteAndStatusDelete();

        return new TradePostCommonRes(tradePost.getId());
    }

    private User getUser(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
    }
}
