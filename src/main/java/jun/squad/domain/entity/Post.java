package jun.squad.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer")
    @ManyToOne
    private User writer;

    private String map;
    private String server;
    private String memo;
    private LocalDateTime created;

    public Post (User writer, String map, String server, String memo) {
        this.writer = writer;
        this.map = map;
        this.server = server;
        this.memo = memo;
    }
}
