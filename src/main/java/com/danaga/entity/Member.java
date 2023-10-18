package com.danaga.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Member {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long memberIdCode;
   @Column(unique = true)
   private String memberId;
   private String memberPassword;
   @Column(unique = true)
   private String memberEmail;
   private String memberName;
   private String memberNickname;
   private String memberAddress;
   private Date memberBirthday;
   @Column(unique = true)
   private String memberPhoneNo;
   @CreationTimestamp
   private LocalDateTime memberJoinDate;
   private String memberRole;// Member, Guest, Admin
   @Builder.Default
   private String memberGrade = "Rookie"; /* Rookie Bronze, Silver, Gold, Platinum, Diamond 결제 가격의 1%가 등급 포인트로 쌓임
                         등급 점수   Rookie : 0 ~ 1000
                                  Bronze : 1001 ~ 5000
                                  Silver : 5001 ~ 10000
                                  Gold : 10001 ~ 20000
                                  Platinum : 20001 ~ 35000
                                  Diamond : 35001 ~  */
   @Builder.Default
   private Integer memberGradePoint = 0;
   
   //관계설정
   
   // Orders
   @OneToMany(mappedBy = "member")
   @Builder.Default
   private List<Orders> orderList = new ArrayList<>();
   
   // Cart
   @OneToMany(mappedBy = "member")
   @Builder.Default
   private List<Cart> cartList = new ArrayList<>();
   
   // Interest
   @OneToMany(mappedBy = "member")
   @Builder.Default
   private List<Interest> interestList = new ArrayList<>();
   
   // RecnetView
   @OneToMany(mappedBy = "member")
   @Builder.Default
   private List<RecentView> recentViewList = new ArrayList<>();
   
//   // Board
//   @OneToMany(mappedBy = "member")
//   @Builder.Default
//   private List<Board> boardList = new ArrayList<>();
//   
//   // Comment
//   @OneToMany(mappedBy = "member")
//   @Builder.Default
//   private List<Comment> commentList = new ArrayList<>();
   
   // Review
//   @OneToMany(mappedBy = "member")
//   @Builder.Default
//   private List<Review> reviewList = new ArrayList<>();
   
// Review
//   @OneToMany(mappedBy = "member")
//   @Builder.Default
//   private List<Coupon> couponList = new ArrayList<>();
}