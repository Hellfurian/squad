package jun.squad.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ResponsePost {
    private String avatar_url;
    private String nickname;
    private String tag;

    private String map;
    private String server;
    private String type;

    private String memo;

    private String time;
    private Boolean verify;
}
