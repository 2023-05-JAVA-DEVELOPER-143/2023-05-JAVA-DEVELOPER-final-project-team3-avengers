package com.danaga.controller;

import java.util.*;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
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
import lombok.extern.slf4j.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final OptionSetService optionSetService;
	private final MemberService memberService;
	private final MemberRepository memberRepository;

	/*
	 * 주문완료알림페이지에서 index.html로 돌아가기  session으로 회원일 떄 비회원일 때
	 */
	@GetMapping("/order_complete_to_index")
	public String orderCompleteToIndex() {
		return "/index";
	}
	
	
	/******************************* 회원 ****************************/
	/*
	 * 주문+주문아이템 목록(회원)
	 */
	@LoginCheck
	@GetMapping("/member_order_List")
	public String memberOrderList(Model model, HttpServletRequest request) {// model은 데이터를 담아서 넘겨주는역활

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
	public String memberProductOrderAddForm(@ModelAttribute("cartDto") CartDto cartDto, Model model,HttpSession session) {

		ResponseDto<?> responseDto = optionSetService.findById(cartDto.getOptionSetId());
		List<ProductDto> productDtoList = (List<ProductDto>) responseDto.getData();
		ProductDto productDto = productDtoList.get(0);

		List<SUserCartOrderDto> sUserCartOrderDtoList = new ArrayList<>();
		SUserCartOrderDto sUserCartOrderDto = SUserCartOrderDto.builder().id(cartDto.getOptionSetId())
				.qty(cartDto.getQty()).productName(productDto.getName()).totalPrice(productDto.getTotalPrice()).build();
		sUserCartOrderDtoList.add(sUserCartOrderDto);
		
		Integer realTotalPrice = 0;
		for (int i = 0; i < sUserCartOrderDtoList.size(); i++) {
			realTotalPrice += sUserCartOrderDtoList.get(i).getTotalPrice();
			System.out.println(realTotalPrice);
		}
		
		model.addAttribute("realTotalPrice", realTotalPrice);
		model.addAttribute("sUserCartOrderDto", sUserCartOrderDtoList);
		session.setAttribute("sUserCartOrderDto", sUserCartOrderDtoList);
		session.setAttribute("realTotalPrice", realTotalPrice);
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
				return "orders/order_complete";
			} catch (Exception e) {
				model.addAttribute("msg", e.getMessage());
				e.printStackTrace();
				return "product/product_detail/" + ordersProductDto.getOptionSetId();
			}
		} else { // 회원
			try {
				orderService.memberProductOrderSave(sUserId, ordersProductDto);
				return "orders/order_complete";
			} catch (Exception e) {
				model.addAttribute("msg", e.getMessage());
				e.printStackTrace();
				return "product/product_detail/" + ordersProductDto.getOptionSetId();
			}
		}
	}

	/*
	 * 카트에서 보내온 데이터로 주문(form)(공통)
	 */
	@PostMapping("/cart_order_form")
	public String memberCartOrderAddForm(@RequestBody List<SUserCartOrderDto> sUserCartOrderDtoList, Model model,
			HttpSession session) throws Exception {
		System.out.println("###########" + sUserCartOrderDtoList.size());
		System.out.println(sUserCartOrderDtoList);
		String sUserId = (String) session.getAttribute("sUserId");
		if (sUserId != null) {
			MemberResponseDto memberResponseDto = memberService.getMemberBy(sUserId);
			Integer discountRate = gradePoint(memberResponseDto.getGrade());
			Integer realTotalPrice = 0;
			for (int i = 0; i < sUserCartOrderDtoList.size(); i++) {
				sUserCartOrderDtoList.get(i).setTotalPrice(
						(sUserCartOrderDtoList.get(i).getTotalPrice() * sUserCartOrderDtoList.get(i).getQty())
								- sUserCartOrderDtoList.get(i).getTotalPrice() * sUserCartOrderDtoList.get(i).getQty()
										* discountRate / 10);
				realTotalPrice += sUserCartOrderDtoList.get(i).getTotalPrice();
				System.out.println(realTotalPrice);
			}
			System.out.println("333333333" + realTotalPrice);
			model.addAttribute("sUserCartOrderDto", sUserCartOrderDtoList);
			model.addAttribute("realTotalPrice", realTotalPrice);
			session.setAttribute("sUserCartOrderDto", sUserCartOrderDtoList);
			session.setAttribute("realTotalPrice", realTotalPrice);
			return "orders/order_save_form";
		} else {
			Integer realTotalPrice = 0;
			for (int i = 0; i < sUserCartOrderDtoList.size(); i++) {
				realTotalPrice += sUserCartOrderDtoList.get(i).getTotalPrice();
				System.out.println(realTotalPrice);
			}
			model.addAttribute("sUserCartOrderDto", sUserCartOrderDtoList);
			model.addAttribute("realTotalPrice", realTotalPrice);
			session.setAttribute("sUserCartOrderDto", sUserCartOrderDtoList);
			session.setAttribute("realTotalPrice", realTotalPrice);
			return "orders/order_save_form";
		}

	}

	@GetMapping("/order_save_form")
	public String orderSaveForm(Model model, HttpSession session) {

		model.addAttribute("sUserCartOrderDto", session.getAttribute("sUserCartOrderDto"));
		model.addAttribute("realTotalPrice", session.getAttribute("realTotalPrice"));
		return "orders/order_save_form";

	}

	/*
	 * 카트에서 보내온 데이터로 주문(action)(공통)
	 */
	@PostMapping("/cart_order_action")
	public String memberCartSelectOrderAddAction(@ModelAttribute("orderTotalDto") OrderTotalDto orderTotalDto,
			Model model, HttpSession session) {
		String sUserId = (String) session.getAttribute("sUserId");
		if (sUserId == null) { // 비회원주문
			try {
				List<SUserCartOrderDto> sUserCartOrderDto = (List<SUserCartOrderDto>) session
						.getAttribute("sUserCartOrderDto");
				List<CartDto> fUserCarts = new ArrayList<>();
				for (int i = 0; i < sUserCartOrderDto.size(); i++) {
					CartDto cartDto = CartDto.builder().optionSetId(sUserCartOrderDto.get(i).getId())
							.qty(sUserCartOrderDto.get(i).getQty()).build();
					fUserCarts.add(cartDto);
				}
				DeliveryDto deliveryDto = new DeliveryDto();
				deliveryDto.setName(orderTotalDto.getReceiverName());
				deliveryDto.setPhoneNumber(orderTotalDto.getReceiverPhoneNo());
				deliveryDto.setAddress(orderTotalDto.getReceiverAddress());
				deliveryDto.setDetailAddress(orderTotalDto.getReceiverDetailAddress());
				deliveryDto.setPostCode(orderTotalDto.getReceiverPostCode());
				OrderGuestDto orderGuestDto = new OrderGuestDto();
				orderGuestDto.setName(orderTotalDto.getOrdererName());
				orderGuestDto.setPhoneNo(orderTotalDto.getOrdererPhoneNo());

				OrdersDto ordersDto= orderService.guestCartSelectOrderSave(deliveryDto, fUserCarts, orderGuestDto);
				
				model.addAttribute("orderId",ordersDto.getId());
//				session.invalidate();
				return "orders/order_complete";
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
					CartDto cartDto = CartDto.builder().optionSetId(sUserCartOrderDto.get(i).getId())
							.qty(sUserCartOrderDto.get(i).getQty()).build();
					fUserCarts.add(cartDto);
				}
				DeliveryDto deliveryDto = new DeliveryDto();
				deliveryDto.setName(orderTotalDto.getReceiverName());
				deliveryDto.setPhoneNumber(orderTotalDto.getReceiverPhoneNo());
				deliveryDto.setAddress(orderTotalDto.getReceiverAddress());
				deliveryDto.setDetailAddress(orderTotalDto.getReceiverDetailAddress());
				deliveryDto.setPostCode(orderTotalDto.getReceiverPostCode());
				orderService.memberCartSelectOrderSave(sUserId, deliveryDto, fUserCarts);
//				for (CartDto cartDto : fUserCarts) {
//					cartService.deleteCart(cartDto.getId(), sUserId);
//				}
				return "orders/order_complete";
			} catch (Exception e) {
				model.addAttribute("msg", e.getMessage());
				e.printStackTrace();
				return "cart/cart_form";
			}
		}
	}

	/******************************* 비회원 ****************************/

	/*
	 * 로그아웃 상태에서 메인페이지에서 상단에서 페이지버튼 클릭 후 FIND ORDER GUEST를 클릭하면 비회원 찾기 폼으로 넘어가는 거
	 */

	@GetMapping("/find_order_guest")
	public String guestOrderList(Model model) {
		OrdersGuestDetailDto ordersGuestDetailDto = new OrdersGuestDetailDto();
		model.addAttribute("ordersGuestDetailDto", ordersGuestDetailDto);
		return "orders/find_order_guest";
	}

	/*
	 * 주문+주문아이템 목록(비회원) 주문List보기(비회원) 비회원 찾는 폼에서 데이터를 보내줘서 이 url로 받으면 list뿌려주고 디테일까지
	 */
	@GetMapping("/order_guest_detail")
	public String guestOrderList(@ModelAttribute("ordersGuestDetailDto") OrdersGuestDetailDto ordersGuestDetailDto,
			Model model) {
		try {
			log.info("orderNo={}", "name={}", "phoneNumber={}", ordersGuestDetailDto.getOrderNo(),
					ordersGuestDetailDto.getName(), ordersGuestDetailDto.getPhoneNumber());
			log.info("ordersGuestDetailDto={}", ordersGuestDetailDto);
			System.out.println("@@@@@@getOrderNo: " + ordersGuestDetailDto.getOrderNo());

			List<OrdersDto> ordersDtoList = orderService.guestOrderList(ordersGuestDetailDto.getOrderNo(),
					ordersGuestDetailDto.getPhoneNumber(), ordersGuestDetailDto.getName());
			System.out.println("@@@@@@@@@@@@@@@@ordersDtoList: " + ordersDtoList);
			System.out.println("@@@@@@@@@@@@@@@@ordersItemDtoList: " + ordersDtoList.get(0).getOrderItemDtoList());
			List<OrderItemDto> orderItemDtoList = ordersDtoList.get(0).getOrderItemDtoList();
			model.addAttribute("ordersDtoList", ordersDtoList);
			model.addAttribute("orderItemDtoList", orderItemDtoList);
			return "orders/order_guest_detail";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return null;
		}
	}

	static Integer gradePoint(String value) {
		String grade = value;
		Integer gradePoint = 0;
		switch (grade) {
		case "Rookie": {
			gradePoint = 1;
			break;
		}
		case "Bronze": {
			gradePoint = 2;
			break;
		}
		case "Silver": {
			gradePoint = 3;
			break;
		}
		case "Gold": {
			gradePoint = 4;
			break;
		}
		case "Platinum": {
			gradePoint = 5;
			break;
		}
		case "Diamond": {
			gradePoint = 6;
			break;
		}
		}
		return gradePoint;
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