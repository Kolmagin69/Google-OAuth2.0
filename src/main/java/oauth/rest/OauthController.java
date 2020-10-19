package oauth.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

@Controller
@PropertySource("classpath:connect_param_local.properties")
@RequestMapping("login")
public class OauthController {

    private static final Logger logger = Logger.getLogger(OauthController.class.getName());

    @Value("${main_link}") private String main_link;

    @Value("${scope}") private String scope;

    @Value("${access_type}") private String access_type;

    @Value("${include_granted_scopes}") private String include_granted_scopes;

    @Value("${response_type}") private String response_type;

    @Value("${state}") private String state;

    @Value("${redirect_uri}") private String redirect_uri;

    @Value("${client_id}") private String client_id;

    @GetMapping
    public String authorization(final Model model) {
        final StringBuilder link = new StringBuilder(main_link);
        appendKeyValue(link,"scope", scope);
        appendKeyValue(link,"access_type", access_type);
        appendKeyValue(link,"include_granted_scopes", include_granted_scopes);
        appendKeyValue(link,"response_type", response_type);
        appendKeyValue(link,"state", state);
        appendKeyValue(link,"redirect_uri", redirect_uri);
        appendKeyValue(link,"client_id", client_id);
        model.addAttribute("link", link.toString());
        logger.info("client on \"sing in\" page");
        return "oauth";
    }

    private StringBuilder appendKeyValue(StringBuilder stringBuilder, final String key, final String value) {
        final String str = key + "=" + value + "&";
        return stringBuilder.append(str);
    }



}
