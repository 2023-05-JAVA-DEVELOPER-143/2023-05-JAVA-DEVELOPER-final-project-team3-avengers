package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.Product;
import com.danaga.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao{
	private final ProductRepository productRepository;
	
	public List<Product> findAllProducts(){
		return productRepository.findAll();
	}
	
	public List<Product> update(final Product entity){
		//1.저장할 엔티티가 유효한지 확인
		validate(entity);
		//2.넘겨받은 엔티티 id를 이용해 todoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트할 수 없기 때문
		final Optional<Product> original = productRepository.findById(entity.getId());
		original.ifPresent(todo ->{
			//3.반환된 todoEntity가 존재하면 값을 새entity의 값으로 덮어 씌운다.
			todo.setBrand(entity.getBrand());
			//4.데이터베이스에 새 값을 저장
			productRepository.save(todo);
		});
		return retrieve(entity.getId());//유저의 모든 todo리스트 리턴
	}
	
	private List<Product> retrieve(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	private void validate(final Product entity) {//entity의 조작(재할당)을 막기 위해 final
		if(entity==null) {
			log.warn("entity cannot be null");
			throw new RuntimeException("entity cannot be null");
		}
		if(entity.getId()==null) {
			log.warn("unknown user");
			throw new RuntimeException("unknown user");
		}
	}
}
