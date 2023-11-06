package com.danaga.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danaga.dto.ResponseDto;
import com.danaga.exception.product.FoundNoObjectException;
import com.danaga.exception.product.NeedLoginException;
import com.danaga.exception.product.ProductExceptionMsg;
import com.danaga.exception.product.FoundNoObjectException.FoundNoMemberException;
import com.danaga.exception.product.FoundNoObjectException.FoundNoOptionSetException;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value = {FoundNoObjectException.FoundNoOptionSetException.class
			,FoundNoObjectException.FoundNoMemberException.class,NeedLoginException
			.class})
	protected String defaultException(Exception e, HttpSession session) {
		    if (e instanceof FoundNoOptionSetException) {
		    	return "redirect:/404.html"; //없는 상품 조회
		    } else if (e instanceof FoundNoMemberException) {
		    	session.removeAttribute("sUserId");
				return "redirect:/member_login_form";//로그인이 잘못된 상태
		    } else if (e instanceof NeedLoginException) {
				return "redirect:/member_login_form";//비로그인 상태
			}
		    return "redirect:/404.html"; //없는 상품 조회
		}
	
	@ResponseBody
	@ExceptionHandler(value = {NeedLoginException.class,FoundNoObjectException.FoundNoOptionSetException.class
			,FoundNoObjectException.FoundNoMemberException.class,MethodArgumentNotValidException.class})
	protected ResponseEntity<?> defaultException(Exception e) {
		 ProductExceptionMsg errorMsg=null;
		    if (e instanceof NeedLoginException) {
		        errorMsg = ((NeedLoginException) e).getMsg();
		    } else if (e instanceof FoundNoObjectException.FoundNoOptionSetException) {
		        errorMsg = ((FoundNoObjectException.FoundNoOptionSetException) e).getMsg();
		    } else if (e instanceof FoundNoObjectException.FoundNoMemberException) {
		        errorMsg = ((FoundNoObjectException.FoundNoMemberException) e).getMsg();
		    } else if (e instanceof MethodArgumentNotValidException) {
		        errorMsg = ProductExceptionMsg.WRONG_PARAMETER;
		    } 
		    return ResponseEntity.badRequest().body(ResponseDto.builder().msg(errorMsg).build());
		}

}
