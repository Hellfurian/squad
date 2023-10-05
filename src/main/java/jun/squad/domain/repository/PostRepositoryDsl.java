package jun.squad.domain.repository;

import jun.squad.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryDsl {

    Page<Post> getPostDynamic (Pageable pageable, PostSearchCond cond);
}
