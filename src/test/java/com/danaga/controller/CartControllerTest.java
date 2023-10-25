package com.danaga.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.CartCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
@ExtendWith(SpringExtension.class)
public class CartControllerTest {
	private final OptionSetDao os;
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;

	MockHttpSession session = new MockHttpSession();

	@BeforeEach
	@Disabled
	void 세션담기() {

		List<CartCreateDto> fUserCarts = new ArrayList<>();
		CartCreateDto a = CartCreateDto.builder().optionSet(os.findById(12L)).qty(3).build();
		CartCreateDto b = CartCreateDto.builder().optionSet(os.findById(12L)).qty(5).build();
		CartCreateDto c = CartCreateDto.builder().optionSet(os.findById(12L)).qty(1).build();
		CartCreateDto d = CartCreateDto.builder().optionSet(os.findById(12L)).qty(7).build();
		fUserCarts.add(a);
		fUserCarts.add(b);
		fUserCarts.add(c);
		fUserCarts.add(d);
		session.setAttribute("fUserCarts", fUserCarts);
	}

	@DisplayName("회원 + 세션 장바구니 X 경우")
	@Test
	void 카트추가(MockHttpSession session) throws Exception {
		session.setAttribute("sUserId", "User1");
		os.findById(12L).getClass();
		CartCreateDto dto = CartCreateDto.builder().qty(4).optionSet(os.findById(12L)).build();
		String jsonString = objectMapper.writeValueAsString(dto);
		mockMvc.perform(post("/cart").contentType(MediaType.APPLICATION_JSON).param(jsonString))
				.andExpect(status().isOk());

	}

	@Test
	@Disabled
	public void testAddCartForLoggedInUserWithNonEmptySessionCart() throws Exception {
		// 세션 장바구니에 제품이 있는 경우에 대한 테스트 코드 작성
	}

	@Test
	@Disabled
	public void testAddCartForNonLoggedInUserWithEmptySessionCart() throws Exception {
		// 비로그인 상태에서 세션 장바구니가 비어있는 경우에 대한 테스트 코드 작성
	}

	@Test
	@Disabled
	public void testAddCartForNonLoggedInUserWithNonEmptySessionCart() throws Exception {
		// 비로그인 상태에서 세션 장바구니에 제품이 있는 경우에 대한 테스트 코드 작성
	}

}
