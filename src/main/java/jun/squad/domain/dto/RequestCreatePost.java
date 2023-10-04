package jun.squad.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestCreatePost {
    
    //토큰이 있으면 토큰으로 처리
    private String token;

    //토큰이 없으면 nickname 으로 처리
    private String nickname;

    private String map;
    private String server;

    private String memo;
}
