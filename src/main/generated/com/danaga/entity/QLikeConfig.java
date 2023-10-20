package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeConfig is a Querydsl query type for LikeConfig
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeConfig extends EntityPathBase<LikeConfig> {

    private static final long serialVersionUID = -1025374355L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeConfig likeConfig = new QLikeConfig("likeConfig");

    public final QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QLikeConfig(String variable) {
        this(LikeConfig.class, forVariable(variable), INITS);
    }

    public QLikeConfig(Path<? extends LikeConfig> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeConfig(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeConfig(PathMetadata metadata, PathInits inits) {
        this(LikeConfig.class, metadata, inits);
    }

    public QLikeConfig(Class<? extends LikeConfig> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

