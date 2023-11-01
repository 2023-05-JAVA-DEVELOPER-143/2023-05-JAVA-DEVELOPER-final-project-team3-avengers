package com.danaga.dao.product;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.product.RecentViewDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.RecentView;
import com.danaga.repository.product.RecentViewRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RecentViewDaoImpl implements RecentViewDao{

	private final RecentViewRepository repository;
	
	@PersistenceContext
	private EntityManager em;
	
	//나의 최근본 상품 하나 선택 삭제
	@Override
	public void delete(RecentView entity) {
		repository.deleteByMemberIdAndOptionSetId(entity.getMember().getId(),entity.getOptionSet().getId());
	}
	//나의 최근본 상품 전체 삭제
	@Override
	public void deleteAll(Long memberId) {
		repository.deleteByMemberId(memberId);
	}

	//recentView 추가
	@Override
	public RecentView save(RecentView entity) {
		return repository.save(entity);
	}
	
	//30일 지난 최근 본 상품 목록 삭제
	@Override
	public void removeOldRecents() {
		String jpql = "DELETE FROM RecentView rv WHERE rv.createTime < :cutoffDate";
		em.createQuery(jpql)
		  .setParameter("cutoffDate", LocalDateTime.now().minusDays(30))
		  .executeUpdate();
	}
	
}
