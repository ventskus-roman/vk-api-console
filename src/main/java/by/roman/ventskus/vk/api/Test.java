package by.roman.ventskus.vk.api;

import by.roman.ventskus.vk.api.auth.Auth;
import by.roman.ventskus.vk.api.entity.User;
import by.roman.ventskus.vk.api.operations.MessageApi;

/**
 * Created by Roman Ventskus on 08.10.2015.
 */
public class Test {

    public static void main(String[] args) {
        Auth auth = new Auth("", "");
        auth.init();
        MessageApi messageApi = new MessageApi(auth);
        User user = new User();
        user.setId(111);
        user.setName("roman");
        messageApi.sendMessage(user, "test, hello");

    }
}
