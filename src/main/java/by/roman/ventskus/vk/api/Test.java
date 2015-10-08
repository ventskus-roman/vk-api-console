package by.roman.ventskus.vk.api;

import by.roman.ventskus.vk.api.auth.Auth;
import by.roman.ventskus.vk.api.entity.User;
import by.roman.ventskus.vk.api.operations.FriendsApi;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Roman Ventskus on 08.10.2015.
 */
public class Test {

    public static void main(String[] args) {
        Auth auth = new Auth("user", "pass");
        auth.init();
        FriendsApi friendsApi = new FriendsApi(auth);
        List<User> friends = friendsApi.getAllFriends();
        System.out.println(friends.stream().map(User::getName).collect(Collectors.joining(", ")));
        System.out.println(friends.size());
    }
}
