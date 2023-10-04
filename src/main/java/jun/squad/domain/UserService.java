package jun.squad.domain;

import jun.squad.domain.dto.ResponseUser;
import jun.squad.domain.dto.discord.ResponseAccessToken;
import jun.squad.domain.dto.discord.ResponseDiscordProfile;
import jun.squad.domain.entity.User;
import jun.squad.domain.repository.UserRepository;
import jun.squad.domain.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Value("${discord.id}")       private String clientId;
    @Value("${discord.secret}")   private String clientSecret;
    @Value("${discord.redirect}") private String redirect;

    public User get (String uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
    }

    @Transactional
    public ResponseUser login (String code) {
        String accessToken = getAccessToken(code);
        ResponseDiscordProfile profile = getDiscordProfile(accessToken);

        Optional<User> findUser = userRepository.findById(profile.getId());
        if (findUser.isEmpty()) {
            userRepository.save(new User(profile.getId(), profile.getUsername(), profile.getDiscriminator(), profile.getEmail(), profile.getAvatar()));
        }

        return new ResponseUser(
                profile.getUsername(),
                jwtProvider.createToken(3000, profile.getId())
        );
    }

    private MultiValueMap<String, String> getAccessTokenParam (String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("grant_type", "authorization_code");
        map.set("code", code);
        map.set("client_id", clientId);
        map.set("client_secret", clientSecret);
        map.set("redirect_uri", redirect);
        map.set("scope", "identify, email");
        return map;
    }

    private String getAccessToken (String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(getAccessTokenParam(code), headers);

        String TOKEN_URL = "https://discord.com/api/oauth2/token";
        ResponseEntity<ResponseAccessToken> responseEntity = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                requestEntity,
                ResponseAccessToken.class
        );

        String token = responseEntity.getBody().getAccess_token();
        if (token == null) {
            log.warn("디스코드에 연결할 수 없습니다");
            throw new RuntimeException("디스코드에 연결할 수 없습니다");
        }
        return token;
    }

    private ResponseDiscordProfile getDiscordProfile (String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ResponseDiscordProfile> exchange
                = restTemplate.exchange("https://discordapp.com/api/users/@me", HttpMethod.GET, requestEntity, ResponseDiscordProfile.class);

        return exchange.getBody();
    }
}
