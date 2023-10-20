package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategorySet is a Querydsl query type for CategorySet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategorySet extends EntityPathBase<CategorySet> {

    private static final long serialVersionUID = 1645346640L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategorySet categorySet = new QCategorySet("categorySet");

    public final QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProduct product;

    public QCategorySet(String variable) {
        this(CategorySet.class, forVariable(variable), INITS);
    }

    public QCategorySet(Path<? extends CategorySet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategorySet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategorySet(PathMetadata metadata, PathInits inits) {
        this(CategorySet.class, metadata, inits);
    }

    public QCategorySet(Class<? extends CategorySet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category"), inits.get("category")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
    }

}

