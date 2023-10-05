package jun.squad.domain;

import jun.squad.domain.dto.RequestCreatePost;
import jun.squad.domain.dto.ResponsePost;
import jun.squad.domain.dto.socket.Packet;
import jun.squad.domain.dto.socket.PacketType;
import jun.squad.domain.entity.Post;
import jun.squad.domain.entity.User;
import jun.squad.domain.repository.PostRepository;
import jun.squad.domain.repository.PostSearchCond;
import jun.squad.domain.util.JwtProvider;
import jun.squad.domain.util.TimeUtil;
import jun.squad.ws.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final WebSocketService webSocketService;

    private final JwtProvider jwtProvider;

    @Transactional
    public void add (RequestCreatePost dto) {
        String uuid = dto.getToken() != null ? jwtProvider.getSubject(dto.getToken()) : null;
        String nickname;
        String avatarUrl;
        boolean verify = false;
        Post post;

        if (uuid == null) {
            post = new Post(dto.getNickname(), dto.getMap(), dto.getServer(), dto.getMemo());
            nickname = dto.getNickname();
            avatarUrl = "https://mblogthumb-phinf.pstatic.net/MjAxODExMDZfMjk1/MDAxNTQxNDgzMjQ2Njkx.nfpggc9vN0l5-p8vjtc9ddl9JZXnqDZseeiBxSEIzeMg.Dv8HMLHaYQNVqobLTNJZ-GgcsF0uylmECBzzBN1ZfJgg.JPEG.kidarinusu/1541483245673.jpg?type=w800";
        }
        else {
            User findUser = userService.get(uuid);
            post = new Post(findUser, dto.getMap(), dto.getServer(), dto.getMemo());
            nickname = findUser.getNickname();
            avatarUrl = "https://cdn.discordapp.com/avatars/" + findUser.getUuid() + "/" + findUser.getAvatar_url();
            verify = true;
        }

        Post save = postRepository.save(post);
        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        webSocketService.sendAllNewPost(new Packet<ResponsePost>(PacketType.UPDATE, new ResponsePost(avatarUrl, nickname, null, dto.getMap(), dto.getServer(), dto.getMemo(), TimeUtil.txtDate(date), verify)));
    }

    @Transactional(readOnly = true)
    public Page<ResponsePost> getAll (Pageable pageable, PostSearchCond cond) {
        Page<Post> results = postRepository.getPostDynamic(pageable, cond);
        List<ResponsePost> posts = results.getContent().stream().map(p -> {
            String avatarUrl = p.getWriter() != null ?
                    "https://cdn.discordapp.com/avatars/" + p.getWriter().getUuid() + "/" + p.getWriter().getAvatar_url()
                    : "https://mblogthumb-phinf.pstatic.net/MjAxODExMDZfMjk1/MDAxNTQxNDgzMjQ2Njkx.nfpggc9vN0l5-p8vjtc9ddl9JZXnqDZseeiBxSEIzeMg.Dv8HMLHaYQNVqobLTNJZ-GgcsF0uylmECBzzBN1ZfJgg.JPEG.kidarinusu/1541483245673.jpg?type=w800";
            String nickname = p.getWriter() != null ? p.getWriter().getNickname() : p.getAnonymous();
            boolean verify = p.getWriter() != null;

            Date date = Date.from(p.getCreated().atZone(ZoneId.systemDefault()).toInstant());
            return new ResponsePost(avatarUrl, nickname, null, p.getMap(), p.getServer(), p.getMemo(), TimeUtil.txtDate(date), verify);
        }).toList();

        return new PageImpl<>(posts, pageable, results.getTotalElements());
    }
}
