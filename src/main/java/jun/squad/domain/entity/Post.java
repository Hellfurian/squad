package jun.squad.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer")
    @ManyToOne
    private User writer;

    @Column(name = "anonymous_writer")
    private String anonymous;

    private String map;
    private String server;
    private String memo;

    @CreatedDate
    private LocalDateTime created;

    public Post (String anonymous, String map, String server, String memo) {
        this.anonymous = anonymous;
        this.map = map;
        this.server = server;
        this.memo = memo;
    }

    public Post (User writer, String map, String server, String memo) {
        this.writer = writer;
        this.map = map;
        this.server = server;
        this.memo = memo;
    }

}
