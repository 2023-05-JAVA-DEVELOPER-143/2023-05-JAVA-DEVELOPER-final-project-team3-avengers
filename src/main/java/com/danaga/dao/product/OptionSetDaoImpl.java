package com.danaga.dao.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.product.OptionSetCreateDto;
import com.danaga.dto.product.OptionSetUpdateDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.repository.product.OptionSetRepository;
import com.danaga.repository.product.OptionSetSearchQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OptionSetDaoImpl implements OptionSetDao{

	private final OptionSetRepository repository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<OptionSet> findByFilter(QueryStringDataDto dataDto){
		String jpql = new OptionSetSearchQuery(dataDto).build();
		TypedQuery<OptionSet> query = em.createQuery(jpql,OptionSet.class);
		return query.getResultList();
	}
	@Override
	public OptionSet findById(Long id) {
		return repository.findById(id).get();
	}
	@Override
	public List<OptionSet> findAllByProductId(Long productId) {
		return repository.findByProductId(productId);
	}
	@Override
	public OptionSet create(OptionSetCreateDto dto) {
		return repository.save(dto.toEntity());
	}
	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	@Override
	public void deleteAllByProductId(Long productId) {
		repository.deleteAllByProductId(productId);
		
	}
	@Override
	public List<OptionSet> findAllByRecentView_MemberId(Long memberId) {
		return repository.findAllByRecentViews_MemberId(memberId);
	}
	@Override
	public List<OptionSet> findAllByInterest_MemberId(Long memberId) {
		return repository.findAllByInterests_MemberId(memberId);
	}
	@Override
	public OptionSet findByOptionId(Long optionId) {
		return repository.findByOptions_Id(optionId);
	}
	@Override
	public OptionSet updateStock(OptionSetUpdateDto dto) {
		//존재하는 optionset인지 검증
		OptionSet origin = repository.findById(dto.getId()).get();
		origin.setStock(dto.getStock());
		return repository.save(origin);
	}
	@Override
	public OptionSet updateOrderCount(Long optionSetId, Integer orderCount) {
		OptionSet origin = repository.findById(optionSetId).get();
		origin.setOrderCount(orderCount);
		return repository.save(origin);
	}
	@Override
	public OptionSet updateViewCount(Long optionSetId) {
		OptionSet origin = repository.findById(optionSetId).get();
		origin.setViewCount(origin.getViewCount()+1);
		return repository.save(origin);
	}
	@Override
	public OptionSet calculateTotalPrice(OptionSetUpdateDto dto) {
		OptionSet origin = repository.findById(dto.getId()).get();
		int productPrice = origin.getTotalPrice();
		int totalPrice = productPrice;
		List<Options> options = dto.getOptions().stream().map(t -> t.toEntity()).collect(Collectors.toList());
		for (int i = 0; i < options.size(); i++) {
			totalPrice+=options.get(i).getExtraPrice();
		}
		origin.setTotalPrice(totalPrice);
		return repository.save(origin);
	}

}

