package jun.squad.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponsePost {
    private String nickname;
    private String tag;

    private String map;
    private String server;

    private String memo;
}
