package com.example.ufo_fi.domain.trade_post;

import com.example.ufo_fi.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "trade_posts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier")
    private CarrierType carrier;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_data_type")
    private MobileDataType mobileDataType;

    @Column(name = "sell_mobile_data_capacity_gb")
    private int sellMobileDataCapacityGb;

    @Column(name = "title", length = 15)
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "report_count")
    private int reportCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostStatus postStatus;

    @Column(name = "is_update")
    private boolean isUpdate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    public static TradePost of(User user, CarrierType carrier, MobileDataType mobileDataType,
        int capacity, String title, int price) {
        return TradePost.builder()
            .user(user)
            .carrier(carrier)
            .mobileDataType(mobileDataType)
            .sellMobileDataCapacityGb(capacity)
            .title(title)
            .price(price)
            .reportCount(0)
            .postStatus(PostStatus.SELLING)
            .isUpdate(false)
            .build();
    }

}
