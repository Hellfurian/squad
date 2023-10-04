package jun.squad.ws;

import jun.squad.domain.dto.socket.Packet;
import jun.squad.domain.dto.socket.PacketType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final WebSocketRepository webSocketRepository;
    private final WebSocketService webSocketService;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketRepository.delete(session);
        webSocketService.sendAllNewPost(new Packet<Integer>(PacketType.ONLINE, webSocketRepository.findAll().size()));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketRepository.save(session);
        webSocketService.sendAllNewPost(new Packet<Integer>(PacketType.ONLINE, webSocketRepository.findAll().size()));
    }
}
