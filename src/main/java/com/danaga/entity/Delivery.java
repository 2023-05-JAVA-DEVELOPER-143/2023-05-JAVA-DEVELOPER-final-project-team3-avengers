package com.danaga.entity;

import java.time.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
	@Id
	@SequenceGenerator(name = "DELIVERY_DELIVERY_NO_SEQ", sequenceName = "DELIVERY_DELIVERY_NO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_DELIVERY_NO_SEQ")
	@Column(length = 20)
	private Long deNo;
	@Column(length = 10)
	private String deName;
	@Column(length = 20)
	private Integer dePhonenumber;
	@Column(length = 100)
	private String deAddress;	    
	
	@OneToOne
	@JoinColumn(name = "oNo")
	private Orders orders;
	private Long de_no;
	private String de_name;	
	private String de_phonenumber;    
	private String de_address;	    

}
