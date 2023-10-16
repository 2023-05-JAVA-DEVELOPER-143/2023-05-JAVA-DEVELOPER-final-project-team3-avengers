package com.danaga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oracle.sql.TIMESTAMP;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
	@Id
	private TIMESTAMP sDate;
	
	private Long dailySalesQty;
	private Long dailySalesRevenue;
	private Long dailyBoardInquiry;
	private Long dailyNewMember;
	

}
