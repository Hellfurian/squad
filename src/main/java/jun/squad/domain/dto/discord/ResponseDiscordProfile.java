package jun.squad.domain.dto.discord;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDiscordProfile {

    private String id;
    private String username;
    private String discriminator;
    private String avatar;
    private String email;
    private boolean verified;

}
