package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptionSet is a Querydsl query type for OptionSet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptionSet extends EntityPathBase<OptionSet> {

    private static final long serialVersionUID = -768987271L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptionSet optionSet = new QOptionSet("optionSet");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<Cart, QCart> carts = this.<Cart, QCart>createList("carts", Cart.class, QCart.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Options, QOptions> options = this.<Options, QOptions>createList("options", Options.class, QOptions.class, PathInits.DIRECT2);

    public final NumberPath<Integer> orderCount = createNumber("orderCount", Integer.class);

    public final ListPath<OrderItem, QOrderItem> orderItems = this.<OrderItem, QOrderItem>createList("orderItems", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final QProduct product;

    public final ListPath<RecentView, QRecentView> recentViews = this.<RecentView, QRecentView>createList("recentViews", RecentView.class, QRecentView.class, PathInits.DIRECT2);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QOptionSet(String variable) {
        this(OptionSet.class, forVariable(variable), INITS);
    }

    public QOptionSet(Path<? extends OptionSet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptionSet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptionSet(PathMetadata metadata, PathInits inits) {
        this(OptionSet.class, metadata, inits);
    }

    public QOptionSet(Class<? extends OptionSet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
    }

}

