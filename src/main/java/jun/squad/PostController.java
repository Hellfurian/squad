package jun.squad;

import jun.squad.domain.PostService;
import jun.squad.domain.dto.RequestCreatePost;
import jun.squad.domain.dto.ResponsePost;
import jun.squad.domain.repository.PostSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public HttpEntity<?> create (@RequestBody RequestCreatePost dto) {
        postService.add(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<ResponsePost> getAll (@PageableDefault(size = 100) Pageable pageable,
                                      @RequestParam(required = false) String map,
                                      @RequestParam(required = false) String server,
                                      @RequestParam(required = false) String type) {
        return postService.getAll(pageable, new PostSearchCond(map, server, type));
    }
}
