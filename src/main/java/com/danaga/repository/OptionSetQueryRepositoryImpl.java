package com.danaga.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.danaga.entity.OptionSet;
import com.danaga.entity.QCategory;
import com.danaga.entity.QOptionSet;
import com.danaga.entity.QOptions;
import com.danaga.entity.QProduct;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OptionSetQueryRepositoryImpl implements OptionSetQueryRepository {

	private final JPAQueryFactory query;

	public List<Tuple> findByFilter() {
		QCategory category = QCategory.category;
		QOptionSet optionSet = QOptionSet.optionSet;
		QProduct product = QProduct.product;
		QOptions options = QOptions.options;

		BooleanExpression categoryJoinPredicate = category.id.eq(optionSet.id);
		BooleanExpression productJoinPredicate = optionSet.id.eq(product.id);
		BooleanExpression optionsJoinPredicate = options.id.eq(optionSet.id);

		List<Tuple> resultList = query
				.select(category.name, optionSet.stock, product.price, options.name, options.value, product.rating,
						product.brand)
				.from(category, optionSet, product, options)
				.where(options.name.eq("os"), options.value.eq("windows11"), product.name.eq("samsung desktop"),
						optionSet.stock.isNotNull(), categoryJoinPredicate, productJoinPredicate, optionsJoinPredicate)
				.fetch();
		return resultList;
	}

}
