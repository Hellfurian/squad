package jun.squad.domain.dto;

import lombok. AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreatePost {
    
     //If there is a token, process it as a token
     private String token;

     //If there is no token, it is treated as nickname
     private String nickname;

     private String map;
     private String server;
     private String type;

     private String memo;


     public RequestCreatePost (String token, String nickname, String map, String server, String memo, String type) {
         this.token = token;
         this.nickname = nickname;
         this.map = map;
         this.server = server;
         this.memo = memo;
         this.type = type;
     }
}
