package jun.squad.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketRepository {

    private static final ConcurrentHashMap<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    public void save (WebSocketSession session) {
        SESSIONS.put(session.getId(), session);
    }

    public void delete (WebSocketSession session) {
        SESSIONS.remove(session.getId());
    }

    public List<WebSocketSession> findAll () {
        return SESSIONS.values().stream().toList();
    }

}
