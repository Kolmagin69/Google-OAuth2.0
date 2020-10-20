package oauth.rest;

import oauth.rest.item.TokenJson;
import oauth.rest.item.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@PropertySource("classpath:user_info_local.properties")
@RequestMapping("user")
public class UserPageController {

    private static final Logger logger = LoggerFactory.getLogger(UserPageController.class);

    @GetMapping
    public String idUser(final Model model,
            @RequestParam(value="code", required=false, defaultValue="none") String code) {
        final TokenJson tokenJson = postToGoogle(code).getBody();
        final UserInfo userInfo = getUserInfo(tokenJson.getId_token()).getBody();
        model.addAttribute("picture", userInfo.getPicture());
        model.addAttribute("name", userInfo.getName());
        model.addAttribute("email", userInfo.getEmail());
        logger.info(String.format("Redirect client email: %s to home page", userInfo.getEmail()) );
        return "home_page";
    }

    private @Value("${url.token}") String urlToken;

    private @Value("${client_id}") String clientId;

    private @Value("${client_secret}") String clientSecret;

    private @Value("${redirect_uri}") String redirectUri;

    private @Value("${grant_type}") String grantType;

    private ResponseEntity<TokenJson> postToGoogle(final String code) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>() ;
        map.add("code", code);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("redirect_uri", redirectUri);
        map.add("grant_type", grantType);
        HttpEntity<MultiValueMap<String, String>> entity    = new HttpEntity<>(map, headers);
        return restTemplate.exchange(urlToken, HttpMethod.POST, entity, TokenJson.class);
    }

    private @Value("${url.token.info}") String urlTokenInfo;

    private ResponseEntity<UserInfo> getUserInfo(final String idToken){
        final RestTemplate restTemplate = new RestTemplate();
        URI uri = null;
        try {
            uri = new URI(urlTokenInfo + idToken);
        } catch (URISyntaxException e) {
            logger.error("Incorrect uri", URISyntaxException.class);
        }
        return restTemplate.getForEntity(uri, UserInfo.class);
    }

}


