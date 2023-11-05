package com.danaga.exception.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoundNoObjectException extends Exception {
	private String msg;
	private String img;
	private String data;
	public FoundNoObjectException(String msg) {
		this.msg = msg;
	}
	public static class FoundNoMemberException extends FoundNoObjectException{
		public FoundNoMemberException() {
			
		}
	}
	public static class FoundNoOptionSetException extends FoundNoObjectException{
		public FoundNoOptionSetException() {
			
		}
	}
	public static class FoundNoRecentViewException extends FoundNoObjectException{
		public FoundNoRecentViewException() {
			
		}
	}
	public static class FoundNoProductException extends FoundNoObjectException{
		public FoundNoProductException() {
			
		}
	}
	public static class FoundNoCategoryException extends FoundNoObjectException{
		public FoundNoCategoryException() {
			
		}
	}
	public static class FoundNoInterestException extends FoundNoObjectException {
		public FoundNoInterestException() {
			
		}
	}
	public static class FoundNoOptionsException extends FoundNoObjectException {
		public FoundNoOptionsException() {
			
		}
	}
}
