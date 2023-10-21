package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.OptionSetCreateDto;
import com.danaga.dto.QueryStringDataDto;
import com.danaga.entity.OptionSet;
import com.danaga.repository.OptionSetRepository;
import com.danaga.repository.OptionSetSearchQuery;

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

}

