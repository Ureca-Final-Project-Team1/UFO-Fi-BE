package com.example.ufo_fi.domain.tradepost.entity;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.report.entity.Report;
import com.example.ufo_fi.domain.user.entity.User;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
    private Long id;

    @Column(name = "mobile_data_type", nullable = false, length = 255)  //MySQL에서 256부터 2Byte를 사용하기에 255를 써준다.
    private String mobileDataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier", nullable = false)
    private Carrier carrier;

    @Column(name = "sell_mobile_data_capacity_gb", nullable = false)
    private Integer sellMobileDataCapacityGb;

    @Column(name = "title", nullable = false, length = 15)
    private String title;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "report_count", nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer reportCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TradePostStatus tradePostStatus;

    @Column(name = "is_update", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isUpdate;

    @Column(name = "is_delete", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isDelete;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
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
}