package com.danaga.service.product;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.RecentViewDto;

public interface RecentViewService {
	ResponseDto<?> addRecentView(RecentViewDto dto);
	ResponseDto<?> removeMyRecentViews(Long memberId);
	ResponseDto<?> removeRecentView(RecentViewDto dto);
	ResponseDto<?> myAllRecentViews(Long memberId);
}
