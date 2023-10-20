package com.danaga.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "LikeConfig") 
@Table(name = "like_config", uniqueConstraints = {
    @UniqueConstraint(name = "like_config_uq", columnNames = {"board_id", "member_id"})
})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LikeConfig {
    @Id
    @SequenceGenerator(name = "like_config_id_seq", sequenceName = "like_config_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_config_id_seq") // sequenceName 수정
    private Long id;

    // @ColumnDefault(value = "1") // 주석 처리: @ColumnDefault 사용에 대한 JPA 구현체 종속성 확인

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_id")
    @ToString.Exclude
    private Board board;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;
}