package com.danaga.dao.product;

import java.util.List;

import com.danaga.dto.product.RecentViewDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.RecentView;

public interface RecentViewDao {
	void delete(RecentView entity);
	void deleteAll(Long memberId);
	RecentView save(RecentView entity);
}
