package com.danaga.service.product;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.RecentViewDto;
import com.danaga.exception.product.FoundNoObjectException.FoundNoMemberException;
import com.danaga.exception.product.FoundNoObjectException.FoundNoOptionSetException;

public interface RecentViewService {
	ResponseDto<?> addRecentView(RecentViewDto dto) throws FoundNoMemberException, FoundNoOptionSetException;
	ResponseDto<?> removeMyRecentViews(Long memberId);
	ResponseDto<?> removeRecentView(RecentViewDto dto);
	ResponseDto<?> myAllRecentViews(Long memberId) throws FoundNoMemberException;
	ResponseDto<?> removeOldRecents();
}
