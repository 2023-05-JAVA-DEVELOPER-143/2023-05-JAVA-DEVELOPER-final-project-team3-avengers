package com.danaga.repository;

import com.danaga.dto.RecentViewDto;

public interface RecentViewDao {
	void delete(RecentViewDto dto);
	void deleteAll(Long memberId);
	RecentViewDto save(RecentViewDto dto);
}
