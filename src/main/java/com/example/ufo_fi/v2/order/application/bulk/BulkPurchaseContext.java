package com.example.ufo_fi.v2.order.application.bulk;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BulkPurchaseContext {

    private final TradePostManager tradePostManager;
    private final BulkPurchaseSuccessHandler bulkPurchaseSuccessHandler;
    private final BulkPurchaseFailureHandler bulkPurchaseFailureHandler;

    public PurchaseResult bulkPurchase(List<Long> postIds, Long buyerId) {
        PurchaseResult purchaseResult = new PurchaseResult();

        postIds.forEach(postId -> {
            TradePost tradePost = tradePostManager.findById(postId); // lock 풀었음
            if (tradePost.canSellingNow()) {
                bulkPurchaseSuccessHandler.handleSuccess(tradePost, buyerId, purchaseResult);
            } else {
                bulkPurchaseFailureHandler.handleFailure(tradePost, buyerId, purchaseResult);
            }
        });

        return purchaseResult;
    }
}