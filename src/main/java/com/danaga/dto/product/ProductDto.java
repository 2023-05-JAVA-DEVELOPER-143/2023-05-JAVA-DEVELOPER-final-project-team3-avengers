package com.danaga.dto.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.danaga.entity.Category;
import com.danaga.entity.CategorySet;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
	private String brand;
	private String name;
	private Integer price;
	private Double rating;
	private LocalDateTime updateTime;
	private String descImage;
	private String prevDesc;
	private String pImage;
	//@Builder.Default
	//private List<Review> reviews = new ArrayList<>();
//	@Builder.Default
//	private List<Category> categorySet=new ArrayList<>();
	private Integer stock;
	private Long osId;
	@Builder.Default
	private List<Options> optionSet = new ArrayList<>();

//	public ProductDto(final Product product, final OptionSet optionSet, 
//			List<Options> options) {// 처리한 후 다시 유저한테 보내는 entity를 dto로 바꿔서 보내줌
//		this.brand = product.getBrand();
//		this.name = product.getName();
//		this.descImage = product.getDescImage();
//		this.pImage = product.getPImage();
//		this.prevDesc = product.getPrevDesc();
//		this.rating = product.getRating();
//		this.updateTime = optionSet.getUpdateTime();
//		this.stock = optionSet.getStock();
//		this.osId = optionSet.getId();
//		//this.reviews = reviews;
//		this.optionSet = options;
//		this.price = product.getPrice();
//		for (int i = 0; i < options.size(); i++) {
//			price += options.get(i).getExtraPrice();
//		}
//	}

	public static Product toEntity(final ProductDto dto) {// dto로 요청을 받아서 entity로 바꿔 서비스 처리하고
		return Product.builder().build();

	}
}