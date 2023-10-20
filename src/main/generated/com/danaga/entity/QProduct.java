package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1425042501L;

    public static final QProduct product = new QProduct("product");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath brand = createString("brand");

    public final ListPath<CategorySet, QCategorySet> categorySets = this.<CategorySet, QCategorySet>createList("categorySets", CategorySet.class, QCategorySet.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final StringPath descImage = createString("descImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    public final StringPath name = createString("name");

    public final ListPath<OptionSet, QOptionSet> optionSets = this.<OptionSet, QOptionSet>createList("optionSets", OptionSet.class, QOptionSet.class, PathInits.DIRECT2);

    public final StringPath prevDesc = createString("prevDesc");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

