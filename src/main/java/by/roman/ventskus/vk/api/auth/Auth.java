package by.roman.ventskus.vk.api.auth;

import by.roman.ventskus.vk.api.constants.Constants;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman Ventskus on 08.10.2015.
 */
public class Auth {

    private static final String FORM = "form";
    private static final String FORM_ACTION = "action";
    private static final String FORM_EMAIL = "email";
    private static final String FORM_PASS = "pass";
    private static final String CHECKED_PROPERTIES = "p";

    private String login;
    private String password;
    private Map<String, String> cookies;

    public Auth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void init() {
        try {
            Connection.Response connection = Jsoup.connect(Constants.VK_URL).execute();
            Document document = connection.parse();
            Element form = document.getElementsByTag(FORM).get(0);
            String url = form.attr(FORM_ACTION);
            Map<String, String> data = new HashMap<String, String>();
            data.put(FORM_EMAIL, login);
            data.put(FORM_PASS, password);
            cookies = connection.cookies();
            connection = Jsoup.connect(url).cookies(cookies).data(data).execute();
            cookies = connection.cookies();
            if (!cookies.containsKey(CHECKED_PROPERTIES)) {
                throw new RuntimeException("Invalid login/password");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getCookies() {
        if (cookies == null) {
            throw new RuntimeException("Not initialized");
        }
        return cookies;
    }
}
