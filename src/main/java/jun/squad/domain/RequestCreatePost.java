package jun.squad.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestCreatePost {
    private String nickname;
    private String tag;

    private String map;
    private String server;

    private String memo;
}
