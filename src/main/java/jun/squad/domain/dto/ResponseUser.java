package jun.squad.domain.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseUser {

    private String username;
    private String token;
}
