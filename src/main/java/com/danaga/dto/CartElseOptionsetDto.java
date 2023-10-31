package com.danaga.dto;

import java.util.List;

import com.danaga.dto.product.OptionDto;
import com.danaga.dto.product.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartElseOptionsetDto {
	private Long osId;
	// private List<CartOptionDto> elseOptions = new ArrayList<>();
	private String osString;
	private Integer totalPrice;

	public CartElseOptionsetDto(ProductDto dto) {
		this.osId = dto.getOsId();
		this.totalPrice = dto.getTotalPrice();
		this.osString = optionset(dto.getOptionSet());
	}

	// this.elseOptions=dto.getOptionSet().stream().map(t ->
	// CartOptionDto.builder().name(t.getName()).value(t.getValue()).build()).collect(Collectors.toList());

	static String optionset(List<OptionDto> list) {
		String os = "";
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				os += list.get(i).getName() + " : ";
				os += list.get(i).getValue() + " , ";
			}
			os = os.substring(0, os.length() - 2);
		}
		return os;
	}

}
