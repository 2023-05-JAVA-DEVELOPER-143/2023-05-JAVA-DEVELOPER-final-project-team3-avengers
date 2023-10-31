package com.danaga.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

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
	//로그인 한 후에 메뉴에서 주문목록보기 클릭하면 나오게하기
	@LoginCheck
	@GetMapping("/member_order_List")
	public String memberOrderList(Model model, HttpServletRequest request) {// model은 데이터를 담아서 넘겨주는역활

		// public String memberOrderList(Model model, HttpSession session) {// model은
		// 데이터를 담아서 넘겨주는역활
		try {
//		   String sUserId = "User1";
			String loginUser = (String) request.getSession().getAttribute("sUserId");
			List<OrdersDto> orderDtoList = orderService.memberOrderList(loginUser);
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

		
		model.addAttribute("id",cartDto.getId());
		model.addAttribute("qty",cartDto.getQty());
		model.addAttribute("totalPrice",productDto.getTotalPrice());
		

		return "orders/order_save_form";

	}

	/*
	 * 상품에서 주문(action)(공통)
	 */

	
	@PostMapping("/product_order_action")//modelAttribute html에서 보낸 데이터를 받는곳
	public String memberProductOrderAddAction(@ModelAttribute("ordersProductDto") OrdersProductDto ordersProductDto,@ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto, Model model,
			HttpSession session) {

		String sUserId = (String) session.getAttribute("sUserId");
		if(sUserId==null) {//비회원
			try {
				orderService.guestProductOrderSave(ordersProductDto,orderGuestDto);
				return "redirect:orders/guest/order_list";
			} catch (Exception e) {
				model.addAttribute("msg", e.getMessage());
				e.printStackTrace();
				return "product/product_detail/" + ordersProductDto.getOptionSetId();
			}
		}else { //회원
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
	 * 카트에서 보내온 데이터로 주문(form)(공통) //form에서 sUserId에 유무에 따라서 뿌려지는 data가 달라지게해야함(회원,비회원)
	 */

	@PostMapping("/cart_order_form")
	public String memberCartOrderAddForm(@ModelAttribute List<SUserCartOrderDto> sUserCartOrderDto ,Model model,HttpSession session) throws Exception {
		
		String sUserId = (String) session.getAttribute("sUserId");
		if(sUserId==null) {
			//비회원(전체,선택)
			model.addAttribute("sUserCartOrderDto",sUserCartOrderDto);
			session.setAttribute("sUserCartOrderDto", sUserCartOrderDto);
		}else {
			//회원(전체,선택) 
			model.addAttribute("sUserCartOrderDto", sUserCartOrderDto);
			session.setAttribute("sUserCartOrderDto", sUserCartOrderDto);
		}
//		List<SUserCartOrderDto> sUserCartOrderDto = new ArrayList<>();
//		SUserCartOrderDto userCartOrderDto= SUserCartOrderDto.builder()
//						.id(2L)
//						.qty(3)
//						.productName("dd")
//						.totalPrice(300000)
//						.build();
//		sUserCartOrderDto.add(userCartOrderDto);
		model.addAttribute("sUserCartOrderDto", sUserCartOrderDto);
		return "orders/order_save_form";
	}


//	/*
//	 * 카트에서 보내온 데이터로 주문(action)(회원)
//	 */
//	@PostMapping("/member_cart_order_save_action")
//	public String memberCartOrderAddAction(@ModelAttribute("deliveryDto") DeliveryDto deliveryDto, Model model, HttpSession session)
//			throws Exception {
//
//		String sUserId = (String) session.getAttribute("sUserId");
//		try {
//			orderService.memberCartOrderSave(sUserId, deliveryDto);
//			return "redirect:orders/order_list";
//		} catch (Exception e) {
//			model.addAttribute("msg", e.getMessage());
//			e.printStackTrace();
//			return "cart/list";
//		}
//	}
	
	/*
	 * 카트에서 보내온 데이터로 주문(action)(공통)
	 */
	@PostMapping("/cart_order_action")
	public String memberCartSelectOrderAddAction(@ModelAttribute("deliveryDto") DeliveryDto deliveryDto,@ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto, Model model, HttpSession session) {
		
		String sUserId = (String) session.getAttribute("sUserId");
		
		if(sUserId==null) { //비회원주문
			try {
				List<SUserCartOrderDto> sUserCartOrderDto = (List<SUserCartOrderDto>)session.getAttribute("sUserCartOrderDto");
				List<CartDto> fUserCarts =new ArrayList<>();
				for (int i = 0; i < sUserCartOrderDto.size(); i++) {
					CartDto cartDto =CartDto.builder()
										   .id(sUserCartOrderDto.get(i).getId())
										   .qty(sUserCartOrderDto.get(i).getQty())
										   .build();
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
		}else{ //회원주문
			try {
				List<SUserCartOrderDto> sUserCartOrderDto = (List<SUserCartOrderDto>)session.getAttribute("sUserCartOrderDto");
				List<CartDto> fUserCarts =new ArrayList<>();
				for (int i = 0; i < sUserCartOrderDto.size(); i++) {
					CartDto cartDto =CartDto.builder()
										   .id(sUserCartOrderDto.get(i).getId())
										   .qty(sUserCartOrderDto.get(i).getQty())
										   .build();
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
//	/*
//	 * 주문상세보기(비회원)
//	 */
//	public String guestDetail(@ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto ,Model model) {
//		try {
//			orderService.guestOrderDetail(orderGuestDto.getOrderNo(),orderGuestDto.getName(),orderGuestDto.getPhoneNo());
//			return "orders/order_guest_detail";
//		}catch (Exception e) {
//			e.getStackTrace();
//			model.addAttribute("errorMsg",e.getMessage());
//			return null;
//		}
//	}				---->rest로 가야할듯;;
	
	/*
	 * 주문+주문아이템 목록(비회원)
	 * 주문List보기(비회원) 비회원 찾는 폼에서 데이터를 보내줘서 이 url로 받으면 list뿌려주고 디테일까지 나오게만들기
	 */
	@GetMapping("/guest_order_detail")
	public String guestOrderList(@ModelAttribute OrdersGuestDetailDto ordersGuestDetailDto ,Model model) {
		try {
			List<OrdersDto> ordersDtoList = orderService.guestOrderList(ordersGuestDetailDto.getOrderNO(), ordersGuestDetailDto.getPhoneNumber(),ordersGuestDetailDto.getName());
			model.addAttribute("ordersDtoList", ordersDtoList);
			return "orders/order_guest_detail";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", e.getMessage());
			return null;
		}
	}

//	/*
//	 * 상품에서 주문(action)(비회원)
//	 */
//	@PostMapping("/guest_product_order_save_action")//modelAttribute html에서 보낸 데이터를 받는곳
//	public String guestProductOrderAddAction(@ModelAttribute("ordersProductDto") OrdersProductDto ordersProductDto, @ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto
//			, Model model) {
//
//		try {
//			orderService.guestProductOrderSave(ordersProductDto,orderGuestDto);
//			return "redirect:orders/guest/order_list";
//		} catch (Exception e) {
//			model.addAttribute("msg", e.getMessage());
//			e.printStackTrace();
//			return "product/product_detail/" + ordersProductDto.getOptionSetId();
//		}
//	}

//	/*
//	 * 카트에서 주문(action)(비회원) (선택주문까지됨)
//	 */
//	@PostMapping("/guest_cart_order_save_action")
//	public String guestCartOrderAddAction(@ModelAttribute("deliveryDto") DeliveryDto deliveryDto,@ModelAttribute("orderGuestDto") OrderGuestDto orderGuestDto, Model model, HttpSession session)
//			throws Exception {
//
//		List<CartDto> fUserCarts =(List<CartDto>)session.getAttribute("cartDtoList");
//		try {
//			orderService.guestCartOrderSave(fUserCarts, deliveryDto, orderGuestDto);
////			session.invalidate();
//			return "redirect:orders/guest/order_list";
//		} catch (Exception e) {
//			model.addAttribute("msg", e.getMessage());
//			e.printStackTrace();
//			return "cart/cart_form";
//		}
//	}
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

	
//결국엔 전체,선택 주문이 합쳐짐 그리고 form도 같이 씀
//그래서 form 하나, 주문 하나로 합쳐짐!!

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