package com.danaga.dao.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.product.ProductSaveDto;
import com.danaga.entity.Product;
import com.danaga.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao{
	private final ProductRepository repository;

	@Override
	public Product findById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Product findByOptionSetId(Long optionSetId) {
		
		return repository.findByOptionSets_Id(optionSetId);
	}

	@Override
	public Product create(ProductSaveDto dto) {
		return repository.save(dto.toEntity());
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

//	private void validate(final Product entity) {// entity의 조작(재할당)을 막기 위해 final
//		if (entity == null) {
//			log.warn("entity cannot be null");
//			throw new RuntimeException("entity cannot be null");
//		}
//		if (entity.getId() == null) {
//			log.warn("unknown user");
//			throw new RuntimeException("unknown user");
//		}
//	}
}
