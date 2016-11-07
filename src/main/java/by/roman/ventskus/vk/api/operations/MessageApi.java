package by.roman.ventskus.vk.api.operations;

import by.roman.ventskus.vk.api.auth.Auth;
import by.roman.ventskus.vk.api.constants.Constants;
import by.roman.ventskus.vk.api.entity.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author r.ventskus
 * @date 07.11.2016.
 */
public class MessageApi {

    private Auth auth;

    public MessageApi(Auth auth) {
        this.auth = auth;
    }

    public void sendMessage(User user, String message) {
        try {
            Connection.Response connection = getConnection(user.getId());
            Document document = connection.parse();

            Element form = document.select("#write_form").get(0);
            String url = Constants.VK_URL + form.attr("action");
            Map<String, String> data = new HashMap<String, String>();
            data.put("message", message);

            Jsoup.connect(url).cookies(auth.getCookies()).data(data).execute();
            System.out.println("Message to " + user.getName() + " with text = " + message + " was sent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection.Response getConnection(Integer userId) throws IOException {
        String url = Constants.getDialogUrlWithUserId(userId);
        return Jsoup.connect(url).cookies(auth.getCookies()).execute();
    }
}
