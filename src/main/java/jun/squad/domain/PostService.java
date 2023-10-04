package jun.squad.domain;

import jun.squad.domain.dto.RequestCreatePost;
import jun.squad.domain.dto.ResponsePost;
import jun.squad.domain.entity.Post;
import jun.squad.domain.repository.PostRepository;
import jun.squad.ws.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final WebSocketService webSocketService;

    public void add (RequestCreatePost dto) {

    }

    public Page<ResponsePost> getAll (Pageable pageable) {
        Page<Post> results = postRepository.findAll(pageable);
        List<ResponsePost> posts = results.getContent().stream().map(p ->
                new ResponsePost(p.getWriter().getNickname(), p.getWriter().getTag(), p.getMap(), p.getServer(), p.getMemo())).toList();

        return new PageImpl<>(posts, pageable, results.getTotalElements());
    }
}
