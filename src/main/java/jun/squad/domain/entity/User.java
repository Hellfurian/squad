package jun.squad.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    private String uuid;

    private String nickname;
    private String tag;
    private String email;
    private String avatar_url;

    public User (String uuid, String nickname, String tag, String email, String avatar_url) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.tag = tag;
        this.email = email;
        this.avatar_url = avatar_url;
    }
}
