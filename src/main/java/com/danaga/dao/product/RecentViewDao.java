package com.danaga.dao.product;

import com.danaga.entity.RecentView;
import com.danaga.exception.product.AlreadyExistsException.ExistsRecentViewException;
import com.danaga.exception.product.FoundNoObjectException;
import com.danaga.exception.product.FoundNoObjectException.FoundNoMemberException;
import com.danaga.exception.product.FoundNoObjectException.FoundNoOptionSetException;

public interface RecentViewDao {
	void delete(RecentView entity) throws FoundNoObjectException;
	void deleteAll(Long memberId) throws FoundNoObjectException;
	RecentView save(RecentView entity) throws FoundNoMemberException, FoundNoOptionSetException, ExistsRecentViewException;
	void removeOldRecents();
}
