package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStatistic is a Querydsl query type for Statistic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStatistic extends EntityPathBase<Statistic> {

    private static final long serialVersionUID = -400704228L;

    public static final QStatistic statistic = new QStatistic("statistic");

    public final NumberPath<Long> dailyBoardInquiry = createNumber("dailyBoardInquiry", Long.class);

    public final NumberPath<Long> dailyNewMember = createNumber("dailyNewMember", Long.class);

    public final NumberPath<Long> dailySalesRevenue = createNumber("dailySalesRevenue", Long.class);

    public final NumberPath<Long> dailySalesTotQty = createNumber("dailySalesTotQty", Long.class);

    public final StringPath id = createString("id");

    public QStatistic(String variable) {
        super(Statistic.class, forVariable(variable));
    }

    public QStatistic(Path<? extends Statistic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStatistic(PathMetadata metadata) {
        super(Statistic.class, metadata);
    }

}

