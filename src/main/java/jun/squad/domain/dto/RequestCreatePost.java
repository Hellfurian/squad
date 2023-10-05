package jun.squad.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreatePost {
    
    //토큰이 있으면 토큰으로 처리
    private String token;

    //토큰이 없으면 nickname 으로 처리
    private String nickname;

    private String map;
    private String server;

    private String memo;


    public RequestCreatePost (String token, String nickname, String map, String server, String memo) {
        this.token = token;
        this.nickname = nickname;
        this.map = map;
        this.server = server;
        this.memo = memo;
    }
}
