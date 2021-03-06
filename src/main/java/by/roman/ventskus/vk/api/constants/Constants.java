package by.roman.ventskus.vk.api.constants;

/**
 * Created by Roman Ventskus on 08.10.2015.
 */
public class Constants {

    public static final String VK_URL = "https://m.vk.com";
    private static final String FRIENDS_URL = VK_URL + "/friends?offset=";
    private static final String DIALOG_URL = VK_URL + "/im?sel=";

    public static String getFriendsUrlWithOffset(Integer offset) {
        return FRIENDS_URL + offset;
    }

    public static String getFriendsUrlWithOffsetAndId(Integer offset, Integer id) {
        return getFriendsUrlWithOffset(offset) + "&id=" + id;
    }

    public static String getDialogUrlWithUserId(Integer userId) {
        return DIALOG_URL + userId;
    }
}
