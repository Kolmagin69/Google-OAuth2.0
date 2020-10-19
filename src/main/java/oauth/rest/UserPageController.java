package oauth.rest;

import oauth.rest.item.TokenJson;
import oauth.rest.item.UserInfo;
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
import java.util.logging.Logger;

@Controller
@PropertySource("classpath:user_info_local.properties")
@RequestMapping("user")
public class UserPageController {

    private static final Logger logger = Logger.getLogger(UserPageController.class.getName());

    @GetMapping
    public String idUser(final Model model,
            @RequestParam(value="code", required=false, defaultValue="none") String code) throws URISyntaxException {
        final TokenJson tokenJson = postToGoogle(code).getBody();
        final UserInfo userInfo = getUserInfo(tokenJson.getId_token()).getBody();
        model.addAttribute("picture", userInfo.getPicture());
        model.addAttribute("name", userInfo.getName());
        model.addAttribute("email", userInfo.getEmail());
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
        MultiValueMap<String, String> map = new LinkedMultiValueMap() ;
        map.add("code", code);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("redirect_uri", redirectUri);
        map.add("grant_type", grantType);
        logger.info("post to google with code");
        HttpEntity<MultiValueMap<String, String>> entity    = new HttpEntity<>(map, headers);
        return restTemplate.exchange(urlToken, HttpMethod.POST, entity, TokenJson.class);
    }

    private @Value("${url.token.info}") String urlTokenInfo;

    private ResponseEntity<UserInfo> getUserInfo(final String idToken) throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final URI uri = new URI(urlTokenInfo + idToken);
        logger.info("get user info from google.com");
        return restTemplate.getForEntity(uri, UserInfo.class);
    }

}


