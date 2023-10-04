package jun.squad.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketService {

    private final WebSocketRepository webSocketRepository;

    public void sendAllNewPost () {

    }
}
