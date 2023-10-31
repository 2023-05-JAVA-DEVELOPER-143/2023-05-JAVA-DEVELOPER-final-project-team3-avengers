package com.danaga.repository.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.entity.OptionSet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OptionSetQueryRepository {
	@PersistenceContext
	private EntityManager em;
	
	public List<OptionSet> findByFilter(QueryStringDataDto dataDto){
		String jpql = new OptionSetSearchQuery(dataDto).build();
		TypedQuery<OptionSet> query = em.createQuery(jpql,OptionSet.class);
		return query.getResultList();
	}
	public List<ProductDto> findForMemberByFilter(QueryStringDataDto dataDto, String username){
		String mainJpql = new OptionSetSearchQuery(dataDto).build();
		TypedQuery<OptionSet> query = em.createQuery(mainJpql,OptionSet.class);
		String findHeartJpql = "SELECT i.optionSet.id FROM Interest i WHERE i.member.userName= :username";
		TypedQuery<Long> heart = em.createQuery(findHeartJpql,Long.class);
		heart.setParameter("username", username);
		List<Long> heartOptionSetId = heart.getResultList();
		List<OptionSet> searchResult = query.getResultList();
		List<ProductDto> finalResult = searchResult.stream().map(t -> {
			ProductDto productDto = new ProductDto(t);
			productDto.setIsInterested(heartOptionSetId.contains(t.getId()));
			return productDto;
				}).collect(Collectors.toList());
		return finalResult;
	}
	
}
