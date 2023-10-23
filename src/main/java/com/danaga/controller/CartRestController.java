package com.danaga.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danaga.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartRestController {
	private final CartService cartService;

	/*
	 * @Operation(summary = "카트리스트")
	 * 
	 * @GetMapping("/list") public ResponseEntity<List<Cart>> getCartList(String
	 * username) throws Exception { return
	 * ResponseEntity.status(HttpStatus.OK).body(cartService.findCartList("User1"));
	 * }
	 * 
	 * 
	 * /*
	 * 
	 * @Operation(summary = "카트리스트")
	 * 
	 * @GetMapping("/list") public ResponseEntity<List<Cart>> getCartList(String
	 * username) throws Exception { return
	 * ResponseEntity.status(HttpStatus.OK).body(cartService.findCartList("User1"));
	 * }
	 */

	@Operation(summary = "카트삭제")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map> delete(@PathVariable(name = "id") Long id) throws Exception {
		cartService.deleteCart(id);
		return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>());
	}

	/*
	 * @Operation(summary = "카트삭제")
	 * 
	 * @DeleteMapping("/{id}") public ResponseEntity<Map> delete(@PathVariable(name
	 * = "id") Long id) throws Exception { cartService.deleteCart(id); return
	 * ResponseEntity.status(HttpStatus.OK).body(new HashMap<>()); }
	 */

}
