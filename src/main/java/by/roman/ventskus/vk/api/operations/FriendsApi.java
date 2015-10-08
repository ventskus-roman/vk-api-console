package by.roman.ventskus.vk.api.operations;

import by.roman.ventskus.vk.api.auth.Auth;
import by.roman.ventskus.vk.api.constants.Constants;
import by.roman.ventskus.vk.api.entity.Status;
import by.roman.ventskus.vk.api.entity.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Ventskus on 08.10.2015.
 */
public class FriendsApi {

    private Auth auth;

    public FriendsApi(Auth auth) {
        this.auth = auth;
    }

    public List<User> getAllFriends(Integer userId) {
        List<User> friends = new ArrayList<User>();
        try {
            Connection.Response connection = getConnection(0, userId);
            Document document = connection.parse();
            Elements tabs = document.select(".tab_item .tab_counter");
            Integer friendsCount = tabs.size() > 0 ? Integer.valueOf(tabs.get(0).text()) : 0;
            int step = 20;
            for (int offset = 0; offset <= friendsCount; offset += step) {
                friends.addAll(getFriendsPartial(offset, userId));
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return friends;
    }

    public List<User> getAllFriends() {
        return getAllFriends(null);
    }

    private List<User> getFriendsPartial(Integer offset, Integer userId) {
        List<User> friends = new ArrayList<User>();
        try {
            Connection.Response connection = getConnection(offset, userId);
            Document document = connection.parse();
            Elements list = document.select(".simple_fit_item");
            for (Element next : list) {
                User user = new User();
                Element photo = next.select(".si_img").get(0);
                Element owner = next.select(".si_owner").get(0);
                Elements elements = next.select(".si_links a[href]");
                //Ignoring for removed users
                if (elements.isEmpty()) {
                    continue;
                }
                Element id = next.select(".si_links a[href]").get(0);
                Elements statusElement = next.select("b");
                String photoURL = photo.attr("src");
                String name = owner.text();
                int idUser = Integer.valueOf(id.attr("href").substring(6));
                Status status = statusElement.size() == 0 ? Status.OFFLINE : (statusElement.get(0).attr("class").contains("mlvi") ? Status.ONLINE_MOBILE
                        : Status.ONLINE);
                user.setId(idUser);
                user.setName(name);
                user.setPhotoUrl(photoURL);
                user.setStatus(status);
                friends.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return friends;
    }

    private Connection.Response getConnection(Integer offset, Integer userId) throws IOException {
        String url = userId == null ? Constants.getFriendsUrlWithOffset(offset) : Constants.getFriendsUrlWithOffsetAndId(offset, userId);
        return Jsoup.connect(url).cookies(auth.getCookies()).execute();
    }
}
