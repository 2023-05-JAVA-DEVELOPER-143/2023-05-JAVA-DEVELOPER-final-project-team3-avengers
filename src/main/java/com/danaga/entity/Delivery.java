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
	@SequenceGenerator(name = "DELIVERY_DEV_NO_SEQ", sequenceName = "DELIVERY_DEV_NO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_DEV_NO_SEQ")
	private Long de_no;
	private String de_name;		    	
	private Integer de_phonenumber;    
	private String de_address;	    
}
