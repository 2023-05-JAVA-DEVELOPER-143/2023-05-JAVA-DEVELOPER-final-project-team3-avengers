package com.danaga.exception.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AlreadyExistsException extends Exception {
	private String msg;
	
	public static class ExistsRecentViewException extends AlreadyExistsException{
		public ExistsRecentViewException() {
			
		}
	}
}
