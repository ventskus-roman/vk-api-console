package by.roman.ventskus.vk.api;

import by.roman.ventskus.vk.api.auth.Auth;

/**
 * Created by Roman Ventskus on 08.10.2015.
 */
public class Test {

    public static void main(String[] args) {
        Auth auth = new Auth("login", "pass");
        auth.init();
        System.out.println(auth.getCookies());
    }
}
