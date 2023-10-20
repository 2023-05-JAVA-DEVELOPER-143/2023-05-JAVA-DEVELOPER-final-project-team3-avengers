package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecentView is a Querydsl query type for RecentView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecentView extends EntityPathBase<RecentView> {

    private static final long serialVersionUID = 907773268L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecentView recentView = new QRecentView("recentView");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QOptionSet optionSet;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QRecentView(String variable) {
        this(RecentView.class, forVariable(variable), INITS);
    }

    public QRecentView(Path<? extends RecentView> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecentView(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecentView(PathMetadata metadata, PathInits inits) {
        this(RecentView.class, metadata, inits);
    }

    public QRecentView(Class<? extends RecentView> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.optionSet = inits.isInitialized("optionSet") ? new QOptionSet(forProperty("optionSet"), inits.get("optionSet")) : null;
    }

}

