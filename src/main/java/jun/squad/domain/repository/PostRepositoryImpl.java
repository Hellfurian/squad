package jun.squad.domain.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jun.squad.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static jun.squad.domain.entity.QPost.post;

@Component
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryDsl {

    private final JPAQueryFactory query;

    @Override
    public Page<Post> getPostDynamic (Pageable pageable, PostSearchCond cond) {
        QueryResults<Post> results = query.selectFrom(post)
                .where(mapEq(cond), serverEq(cond), typeEq(cond))
                .orderBy(post.created.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression mapEq (PostSearchCond cond) {
        if (cond == null)  {
            return null;
        }
        return cond.getMap() != null ? post.map.eq(cond.getMap()) : null;
    }

    private BooleanExpression serverEq (PostSearchCond cond) {
        if (cond == null) {
            return null;
        }
        return cond.getServer() != null ? post.server.eq(cond.getServer()) : null;
    }

    private BooleanExpression typeEq (PostSearchCond cond) {
        if (cond == null) {
            return null;
        }
        return cond.getType() != null ? post.type.eq(cond.getType()) : null;
    }
}
