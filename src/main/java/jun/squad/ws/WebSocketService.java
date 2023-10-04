package jun.squad.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import jun.squad.domain.dto.socket.Packet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketService {

    private final WebSocketRepository webSocketRepository;
    private final ObjectMapper objectMapper;

    public void sendAllNewPost (Packet<?> packet) {
        webSocketRepository.findAll().forEach(session -> {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(packet)));
            }
            catch (IOException e) {
                log.warn("웹 소켓 업데이트 패킷 전송 실패");
                throw new RuntimeException("웹 소켓 데이터 전송에 실패");
            }
        });
    }
}
