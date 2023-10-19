package com.danaga.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardGroup is a Querydsl query type for BoardGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardGroup extends EntityPathBase<BoardGroup> {

    private static final long serialVersionUID = -2002670355L;

    public static final QBoardGroup boardGroup = new QBoardGroup("boardGroup");

    public final ListPath<Board, QBoard> boards = this.<Board, QBoard>createList("boards", Board.class, QBoard.class, PathInits.DIRECT2);

    public final NumberPath<Long> configId = createNumber("configId", Long.class);

    public final StringPath name = createString("name");

    public QBoardGroup(String variable) {
        super(BoardGroup.class, forVariable(variable));
    }

    public QBoardGroup(Path<? extends BoardGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardGroup(PathMetadata metadata) {
        super(BoardGroup.class, metadata);
    }

}

