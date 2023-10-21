package com.danaga.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
	
}
