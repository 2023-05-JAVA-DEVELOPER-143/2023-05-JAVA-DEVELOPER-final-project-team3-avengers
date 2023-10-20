package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1657190990L;

    public static final QMember member = new QMember("member1");

    public final StringPath address = createString("address");

    public final DateTimePath<java.util.Date> birthday = createDateTime("birthday", java.util.Date.class);

    public final ListPath<Board, QBoard> boardList = this.<Board, QBoard>createList("boardList", Board.class, QBoard.class, PathInits.DIRECT2);

    public final ListPath<Cart, QCart> cartList = this.<Cart, QCart>createList("cartList", Cart.class, QCart.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath grade = createString("grade");

    public final NumberPath<Integer> gradePoint = createNumber("gradePoint", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Interest, QInterest> interestList = this.<Interest, QInterest>createList("interestList", Interest.class, QInterest.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> joinDate = createDateTime("joinDate", java.time.LocalDateTime.class);

    public final ListPath<LikeConfig, QLikeConfig> lConfigs = this.<LikeConfig, QLikeConfig>createList("lConfigs", LikeConfig.class, QLikeConfig.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final ListPath<Orders, QOrders> orderList = this.<Orders, QOrders>createList("orderList", Orders.class, QOrders.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNo = createString("phoneNo");

    public final ListPath<RecentView, QRecentView> recentViewList = this.<RecentView, QRecentView>createList("recentViewList", RecentView.class, QRecentView.class, PathInits.DIRECT2);

    public final StringPath role = createString("role");

    public final StringPath userName = createString("userName");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

