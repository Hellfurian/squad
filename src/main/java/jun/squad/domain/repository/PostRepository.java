package jun.squad.domain.repository;

import jun.squad.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryDsl {

    Page<Post> findAll (Pageable pageable);

}
