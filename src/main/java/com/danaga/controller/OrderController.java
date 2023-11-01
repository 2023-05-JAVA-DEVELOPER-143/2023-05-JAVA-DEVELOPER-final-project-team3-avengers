package com.danaga.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.danaga.dto.*;
import com.danaga.dto.product.ProductDto;
import com.danaga.entity.*;
import com.danaga.repository.*;
import com.danaga.service.*;
import com.danaga.service.product.OptionSetService;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final OptionSetService optionSetService;
	private final MemberService memberService;
	private final MemberRepository memberRepository;

	/******************************* 회원 ****************************/
	/*
	 * 주문+주문아이템 목록(회원)
	 */
	// 로그인 한 후에 메뉴에서 주문목록보기 클릭하면 나오게하기
	@LoginCheck
	@GetMapping("/member_order_List")
	public String memberOrderList(Model model, HttpServletRequest request) {// model은 데이터를 담아서 넘겨주는역활

		// public String memberOrderList(Model model, HttpSession session) {// model은
		// 데이터를 담아서 넘겨주는역활
		try {
//		   String sUserId = "User1";
			String loginUser = (String) request.getSession().getAttribute("sUserId");
			List<OrdersDto> orderDtoList = orderService.memberOrderList(loginUser);
		    // orderDtoList를 id 기준으로 오름차순 정렬
		    orderDtoList.sort(Comparator.comparing(OrdersDto::getId));
			model.addAttribute("orderDtoList", orderDtoList);
			Long id = memberService.findIdByUsername(loginUser);
			Member member = memberRepository.findById(id).get();
			model.addAttribute("loginUser", member);
			return "orders/orders1";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("주문목록이 없습니다?", e.getMessage());
			return null;
		}
	}

	/*
	 * 상품에서 주문(form)(공통)
	 */
	@GetMapping("/product_order_form")
	public String memberProductOrderAddForm(@ModelAttribute("cartDto") CartDto cartDto, Model model) {

		ResponseDto<?> responseDto = optionSetService.findById(cartDto.getId());
		List<ProductDto> productDtoList = (List<ProductDto>) responseDto.getData();
		ProductDto productDto = productDtoList.get(0);

		List<SUserCartOrderDto> sUserCartOrderDtoList = new ArrayList<>();
		SUserCartOrderDto sUserCartOrderDto = SUserCartOrderDto.builder().id(cartDto.getId()).qty(cartDto.getQty())
				.productName(productDto.getName()).totalPrice(productDto.getTotalPrice()).build();
		sUserCartOrderDtoList.add(sUserCartOrderDto);
		model.addAttribute("sUserCartOrderDtoList", sUserCartOrderDtoList);

		return "orders/order_save_form";

	}

	/*
	 * 상품에서 주문(action)(공통)
	 */

	@PostMapping("/product_order_action") 
	public String memberProductOrderAddAction(@ModelAttribute("ordersProductDto") OrdersProductDto ordersProductDto,
			@ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto, Model model, HttpSession session) {

		String sUserId = (String) session.getAttribute("sUserId");
		if (sUserId == null) {// 비회원
			try {
				orderService.guestProductOrderSave(ordersProductDto, orderGuestDto);
				return "redirect:orders/guest/order_list";
			} catch (Exception e) {
				model.addAttribute("msg", e.getMessage());
				e.printStackTrace();
				return "product/product_detail/" + ordersProductDto.getOptionSetId();
			}
		} else { // 회원
			try {
				orderService.memberProductOrderSave(sUserId, ordersProductDto);
				return "redirect:orders/order_list";
			} catch (Exception e) {
				model.addAttribute("msg", e.getMessage());
				e.printStackTrace();
				return "product/product_detail/" + ordersProductDto.getOptionSetId();
			}
		}
	}

	/*
	 * 카트에서 보내온 데이터로 주문(form)(공통)
	 * 달라지게해야함(회원,비회원)
	 */
	@PostMapping("/cart_order_form")
	public String memberCartOrderAddForm(@RequestBody List<SUserCartOrderDto> sUserCartOrderDto, Model model,
			HttpSession session) throws Exception {
		System.out.println(sUserCartOrderDto.size());
		System.out.println(sUserCartOrderDto);
		model.addAttribute("sUserCartOrderDto", sUserCartOrderDto);
		session.setAttribute("sUserCartOrderDto", sUserCartOrderDto);

//		List<SUserCartOrderDto> sUserCartOrderDto = new ArrayList<>();
//		SUserCartOrderDto userCartOrderDto= SUserCartOrderDto.builder()
//						.id(2L)
//						.qty(3)
//						.productName("dd")
//						.totalPrice(300000)
//						.build();
//		sUserCartOrderDto.add(userCartOrderDto);
		return "orders/order_save_form";
	}
	
	@GetMapping("/order_save_form")
	public String orderSaveForm(Model model, HttpSession session) {
		model.addAttribute("sUserCartOrderDto",session.getAttribute("sUserCartOrderDto"));
		return "orders/order_save_form";
	}
	
	/*
	 * 카트에서 보내온 데이터로 주문(action)(공통)
	 */
	@PostMapping("/cart_order_action")
	public String memberCartSelectOrderAddAction(@ModelAttribute("deliveryDto") DeliveryDto deliveryDto,
			@ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto, Model model, HttpSession session) {

		String sUserId = (String) session.getAttribute("sUserId");

		if (sUserId == null) { // 비회원주문
			try {
				List<SUserCartOrderDto> sUserCartOrderDto = (List<SUserCartOrderDto>) session
						.getAttribute("sUserCartOrderDto");
				List<CartDto> fUserCarts = new ArrayList<>();
				for (int i = 0; i < sUserCartOrderDto.size(); i++) {
					CartDto cartDto = CartDto.builder().id(sUserCartOrderDto.get(i).getId())
							.qty(sUserCartOrderDto.get(i).getQty()).build();
					fUserCarts.add(cartDto);
				}
				orderService.guestCartSelectOrderSave(deliveryDto, fUserCarts, orderGuestDto);
//				session.invalidate();
				return "redirect:orders/guest/order_list";
			} catch (Exception e) {
				model.addAttribute("msg", e.getMessage());
				e.printStackTrace();
				return "cart/cart_form";
			}
		} else { // 회원주문
			try {
				List<SUserCartOrderDto> sUserCartOrderDto = (List<SUserCartOrderDto>) session
						.getAttribute("sUserCartOrderDto");
				List<CartDto> fUserCarts = new ArrayList<>();
				for (int i = 0; i < sUserCartOrderDto.size(); i++) {
					CartDto cartDto = CartDto.builder().id(sUserCartOrderDto.get(i).getId())
							.qty(sUserCartOrderDto.get(i).getQty()).build();
					fUserCarts.add(cartDto);
				}
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
	}

	/******************************* 비회원 ****************************/

	/*
	 * 주문+주문아이템 목록(비회원) 주문List보기(비회원) 비회원 찾는 폼에서 데이터를 보내줘서 이 url로 받으면 list뿌려주고 디테일까지
	 * 나오게만들기
	 */
	@GetMapping("/guest_order_detail")
	public String guestOrderList(@ModelAttribute OrdersGuestDetailDto ordersGuestDetailDto, Model model) {
		try {
			List<OrdersDto> ordersDtoList = orderService.guestOrderList(ordersGuestDetailDto.getOrderNO(),
					ordersGuestDetailDto.getPhoneNumber(), ordersGuestDetailDto.getName());
			model.addAttribute("ordersDtoList", ordersDtoList);
			return "orders/order_guest_detail";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return null;
		}
	}


	/******************************* 회원 ****************************/

	// 주문상세보기(회원)(이미 완성됨)
//   memberOrderDetail  

	// 주문+주문아이템 목록 테스트x
//   memberOrderList

	// 상품에서 직접주문 테스트x
//   memberProductOrderAdd

	// cart에서 주문 테스트x
//   memberCartOrderSave

	// cart에서 선택주문 테스트x
//   memberCartSelectOrderSave

	/******************************* 비회원 ****************************/

	// 주문상세보기(비회원) 테스트x
// guestOrderList

	// 주문+주문아이템 목록(비회원) 테스트x
//   memberOrderList

	// 상품에서 주문(비회원) 테스트x
//   guestProductOrderSave

	// 카트에서 주문(비회원) 테스트x
//   guestCartOrderSave

}