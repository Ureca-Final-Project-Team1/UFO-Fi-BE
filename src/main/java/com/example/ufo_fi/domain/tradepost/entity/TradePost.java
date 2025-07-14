package com.example.ufo_fi.domain.tradepost.entity;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.report.entity.Report;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "trade_posts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TradePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_data_type")
    private MobileDataType mobileDataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier")
    private Carrier carrier;

    @Column(name = "sell_mobile_data_capacity_gb")
    private Integer sellMobileDataCapacityGb;

    @Column(name = "title", length = 15)
    private String title;

    @Column(name = "price_per_unit")
    private Integer pricePerUnit;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "report_count")
    private Integer reportCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TradePostStatus tradePostStatus;

    @Column(name = "is_update")
    private Boolean isUpdate;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "tradePost")
    @Builder.Default
    private List<Report> reports = new ArrayList<>();

    public void addReport(Report report) {
        this.reports.add(report);
        report.setTradePost(this);
    }

    public static TradePost of(TradePostCreateReq request, Boolean isUpdate, Boolean isDelete,
        TradePostStatus tradePostStatus,
        Integer reportCount, User user) {

        return TradePost.builder()
            .user(user)
            .title(request.getTitle())
            .pricePerUnit(request.getPricePerUnit())
            .sellMobileDataCapacityGb(request.getSellDataAmount())
            .carrier(user.getUserPlan().getCarrier())
            .mobileDataType(user.getUserPlan().getMobileDataType())
            .tradePostStatus(tradePostStatus)
            .reportCount(reportCount)
            .isUpdate(isUpdate)
            .isDelete(isDelete)
            .build();
    }

    @PrePersist
    @PreUpdate
    public void saveTotalPrice() {

        if (this.pricePerUnit != null && this.sellMobileDataCapacityGb > 0) {
            this.totalPrice = this.pricePerUnit * this.sellMobileDataCapacityGb;
        } else {
            this.totalPrice = 0;
        }
    }

    public void softDeleteAndStatusDelete() {

        this.isDelete = true;
        this.tradePostStatus = TradePostStatus.DELETED;
    }

    public void update(TradePostUpdateReq request) {

        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }

        if (request.getPricePerUnit() != null) {
            this.pricePerUnit = request.getPricePerUnit();
        }

        if (request.getSellMobileDataCapacityGb() != null) {
            this.sellMobileDataCapacityGb = request.getSellMobileDataCapacityGb();
        }
    }

    public void verifyOwner(TradePost tradePost, User user) {

        if (!tradePost.getUser().getId().equals(user.getId())) {

            throw new GlobalException(TradePostErrorCode.NO_AUTHORITY);
        }
    }

    public void updateStatusSoldOut(){
        this.tradePostStatus = TradePostStatus.SOLD_OUT;
    }
}