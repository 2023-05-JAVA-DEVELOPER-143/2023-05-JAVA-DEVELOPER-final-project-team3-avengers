package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1983248562L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBoardGroup boardGroup;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final NumberPath<Integer> disLike = createNumber("disLike", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img1 = createString("img1");

    public final StringPath img2 = createString("img2");

    public final StringPath img3 = createString("img3");

    public final StringPath img4 = createString("img4");

    public final StringPath img5 = createString("img5");

    public final NumberPath<Integer> isAdmin = createNumber("isAdmin", Integer.class);

    public final NumberPath<Integer> isLike = createNumber("isLike", Integer.class);

    public final ListPath<LikeConfig, QLikeConfig> lConfigs = this.<LikeConfig, QLikeConfig>createList("lConfigs", LikeConfig.class, QLikeConfig.class, PathInits.DIRECT2);

    public final QMember member;

    public final NumberPath<Integer> readCount = createNumber("readCount", Integer.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardGroup = inits.isInitialized("boardGroup") ? new QBoardGroup(forProperty("boardGroup")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

