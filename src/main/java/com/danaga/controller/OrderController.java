package com.danaga.controller;

import java.util.*;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.jaxb.SpringDataJaxb.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.danaga.dto.*;
import com.danaga.dto.product.ProductDto;
import com.danaga.entity.*;
import com.danaga.service.*;
import com.danaga.service.product.OptionSetService;

import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final OptionSetService optionSetService;
	/******************************* 회원 ****************************/
	/*
	 * 주문상세보기(회원)
	 */
	// OrdersDto memberOrderDetail(Long orderNo)
//   @GetMapping("/member_order_detail/{orderNo}")
//   public String memberOrderDetail(Model model,@PathVariable  Long orderNo)throws Exception {
//	   try {
//
//		   OrdersDto ordersDto = orderService.memberOrderDetail(orderNo);
//		   model.addAttribute("ordersDto", ordersDto);
//		   return "orders/orders_detail";
//	   }catch(Exception e) {
//           e.printStackTrace();
//           model.addAttribute("errorMsg", e.getMessage());
//           return null;
//	   }
//   }  -------------------->Rest로 가야할듯;;

	/*
	 * 주문+주문아이템 목록(회원)
	 */
//로그인 한 후에 메뉴에서 주문목록보기 클릭하면 나오게하기
	@GetMapping("/member_order_List")
	public String memberOrderList(Model model, HttpSession session) {//model은 데이터를 담아서 넘겨주는역활
		try {
//		   String sUserId = "User1";
			String sUserId = (String) session.getAttribute("sUserId");
			List<OrdersDto> orderDtoList = orderService.memberOrderList(sUserId);
			model.addAttribute("orderDtoList", orderDtoList);
			return "orders/orders_list";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("주문목록이 없습니다?", e.getMessage());
			return null;
		}
	}

	/*
	 * 상품에서 주문(form)(공통)
	 */
	@GetMapping("/product_order_save_form")
	public String memberProductOrderAddForm(@ModelAttribute("cartDto") CartDto cartDto, Model model) {
		
		ResponseDto<?> responseDto= optionSetService.findById(cartDto.getId());
		List<ProductDto> productDtoList = (List<ProductDto>) responseDto.getData();
		ProductDto productDto = productDtoList.get(0);
		
		model.addAttribute(cartDto.getId());
		model.addAttribute(cartDto.getQty());
		model.addAttribute(productDto.getTotalPrice());
		
//가격넣기ㄴ
		return "orders/order_save_form";

	}

	/*
	 * 상품에서 주문(action)(회원)
	 */
	@PostMapping("/member_product_order_save_action")//modelAttribute html에서 보낸 데이터를 받는곳
	public String memberProductOrderAddAction(@ModelAttribute("ordersProductDto") OrdersProductDto ordersProductDto, Model model,
			HttpSession session) {

		String sUserId = (String) session.getAttribute("sUserId");
		try {
			orderService.memberProductOrderSave(sUserId, ordersProductDto);
			return "redirect:orders/order_list";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "product/product_detail/" + ordersProductDto.getOptionSetId();
		}
	}

	/*
	 * 카트에서 주문(form)(공통) //form에서 sUserId에 유무에 따라서 뿌려지는 data가 달라지게해야함(회원,비회원)
	 */
	@GetMapping("/member_cart_order_save_form")
	public String memberCartOrderAddForm(Model model, HttpSession session) throws Exception {

		String sUserId = (String) session.getAttribute("sUserId");

		if(sUserId.isEmpty()) {
			List<CartDto> fUserCarts =(List<CartDto>)session.getAttribute("cartDtoList");
			
			model.addAttribute("fUserCarts",fUserCarts);
		}else {
			
			List<SUserCartResponseDto> cartDto = cartService.findsUserCartList(sUserId);
			
			model.addAttribute("cartDto", cartDto);
		}

		return "orders/order_save_form";
	}

	/*
	 * 카트에서 주문(action)(회원)
	 */
	@PostMapping("/member_cart_order_save_action")
	public String memberCartOrderAddAction(@ModelAttribute("deliveryDto") DeliveryDto deliveryDto, Model model, HttpSession session)
			throws Exception {

		String sUserId = (String) session.getAttribute("sUserId");
		try {
			orderService.memberCartOrderSave(sUserId, deliveryDto);
			return "redirect:orders/order_list";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "cart/list";
		}
	}
	
	/*
	 * 카트에서 선택주문(action)(회원)
	 */
	@PostMapping("/member_cart_select_order_save_action")
	public String memberCartSelectOrderAddAction(@ModelAttribute("deliveryDto") DeliveryDto deliveryDto, Model model, HttpSession session) {

		String sUserId = (String) session.getAttribute("sUserId");
		try {
			List<CartDto> fUserCarts =(List<CartDto>)session.getAttribute("cartDtoList");
			orderService.memberCartSelectOrderSave(sUserId, deliveryDto, fUserCarts);
			for (CartDto cartDto : fUserCarts) {
				cartService.deleteCart(cartDto.getId(), sUserId);
			}
			return "redirect:orders/order_list";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "cart/cart_form";
		}
	}

	/******************************* 비회원 ****************************/
	/*
	 * 주문상세보기(비회원)
	 */
	public String guestDetail(@ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto ,Model model) {
		try {
			orderService.guestOrderDetail(orderGuestDto.getOrderNo(),orderGuestDto.getName(),orderGuestDto.getPhoneNo());
			return "orders/order_guest_list";
		}catch (Exception e) {
			e.getStackTrace();
			model.addAttribute("errorMsg",e.getMessage());
			return null;
		}
	}
	
	/*
	 * 주문+주문아이템 목록(비회원) 
	 */
	@GetMapping("/guest_orderList/{orderNo},{phoneNumber}")
	public String guestOrderList(Model model, @PathVariable Long orderNo, @PathVariable String phoneNumber) {
		try {
			List<OrdersDto> ordersDtoList = orderService.guestOrderList(orderNo, phoneNumber);
			model.addAttribute("ordersDtoList", ordersDtoList);
			return "orders/orders_guest";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return null;
		}
	}

	/*
	 * 상품에서 주문(action)(비회원)
	 */
	@PostMapping("/guest_product_order_save_action")//modelAttribute html에서 보낸 데이터를 받는곳
	public String guestProductOrderAddAction(@ModelAttribute("ordersProductDto") OrdersProductDto ordersProductDto, @ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto
			, Model model) {

		try {
			orderService.guestProductOrderSave(ordersProductDto,orderGuestDto);
			return "redirect:orders/guest/order_list";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "product/product_detail/" + ordersProductDto.getOptionSetId();
		}
	}

	/*
	 * 카트에서 주문(action)(비회원) (선택주문까지됨)
	 */
	@PostMapping("/guest_cart_order_save_action")
	public String guestCartOrderAddAction(@ModelAttribute("deliveryDto") DeliveryDto deliveryDto,@ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto, Model model, HttpSession session)
			throws Exception {

		List<CartDto> fUserCarts =(List<CartDto>)session.getAttribute("cartDtoList");
		try {
			orderService.guestCartOrderSave(fUserCarts, deliveryDto, orderGuestDto);
//			session.invalidate();
			return "redirect:orders/guest/order_list";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "cart/cart_form";
		}
	}
//	
//	/*
//	 * 카트에서 선택주문(action)(비회원)
//	 */
//	@PostMapping("/guest_cart_select_order_save_action")
//	public String guestCartSelectOrderAddAction(@ModelAttribute List<CartDto> cartDtoList,
//			@ModelAttribute DeliveryDto deliveryDto, Model model, HttpSession session) {
//
//		String sUserId = (String) session.getAttribute("sUserId");
//		try {
//			orderService.memberCartSelectOrderSave(sUserId, deliveryDto, cartDtoList);
//			return "redirect:orders/order_list";
//		} catch (Exception e) {
//			model.addAttribute("msg", e.getMessage());
//			e.printStackTrace();
//			return "cart/list";
//		}
//	}
//	
	
	
	/******************************* 회원 ****************************/

	// 주문상세보기(회원)(이미 완성됨)
//   memberOrderDetail  //에헤이 조졌네 이거 -----> Rest로 가야함

	// 주문+주문아이템 목록(완료) 테스트x
//   memberOrderList

	// 상품에서 직접주문(완료) 테스트x
//   memberProductOrderAdd

	// cart에서 주문(완료) 테스트x
//   memberCartOrderSave

	// cart에서 선택주문(완료) 테스트x
//   memberCartSelectOrderSave

	/******************************* 비회원 ****************************/

	// 주문상세보기(비회원)(완료) 테스트x
// guestOrderList

	// 주문+주문아이템 목록(비회원)(완료) 테스트x
//   memberOrderList

	// 상품에서 주문(비회원)(완료) 테스트x
//   guestProductOrderSave

	// 카트에서 주문(비회원)(완료) 테스트x
//   guestCartOrderSave

}