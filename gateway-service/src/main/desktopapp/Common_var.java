package desktopapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.*;

public class Common_var {

    public static int userID = -1;
    public static String password = "";
    public static String URLBase = "http://localhost:8080/Gateway-Service";
    public static String getAllFriendsURL = "/app/gateway/getAllFriends";
    public static String postExpenseURL = "/app/gateway/postExpense";
    public static String getExpenseAll = "/app/gateway/expense/all";
    public static String addFriendURL = "/app/gateway/addFriend?username=";
    public static String getAllGroupsURL = "/app/gateway/group/all";
    public static String createGroupURL = "/app/gateway/group/create";
    public static String updateGroupExpenseURL = "/app/gateway/postSplitExpense";

    public static int getUserID()
    {
        return userID;
    }

    public static void updateUserID(int id) {
        userID = id;
    }

    public static String getPassword()
    {
        return password;
    }

    public static void updatePassword(String pass) {
        password = pass;
    }

    public static String btoa()
    {
        String s = getUserID() + ":" + getPassword();
        String encoded = new String(Base64.getEncoder().encode(s.getBytes()));
        return encoded;
    }

    public static Map<String, Integer> getFriendsListAndID()
    {
        Map<String, Integer> friendsNameAndId = new HashMap<>();
        String url = Common_var.URLBase + Common_var.getAllFriendsURL;
        HttpResponse<String> friendsExpenses = null;
        try{
            friendsExpenses = HttpInterface.GET(url);
        }
        catch (Exception e)
        {

        }
        String rw_body = friendsExpenses.body();
        JSONArray jsonArray = new JSONArray(rw_body);
        for(int i=0; i < jsonArray.length() ; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            friendsNameAndId.put(obj.getString("name"), obj.getInt("id") );
        }
        return friendsNameAndId;
    }

    public static List<String> get_MutuallyExclusive_Or_Overlapping_Friends(Map<String , Integer> getFriendsListAndID, JSONArray userIDList, boolean overlapping)
    {
        List<String> overlappingNames = new ArrayList<>();
        List<String> non_overlappingNames = new ArrayList<>();
        for (String name: getFriendsListAndID.keySet()) {
            int i;
            for (i=0; i < userIDList.length(); i++)
            {
                if( (getFriendsListAndID.get(name) == userIDList.get(i)) && overlapping)
                {
                    overlappingNames.add(name);
                    break;
                }
            }
        }

        for (String name: getFriendsListAndID.keySet()) {
            int i;
            for (i=0; i < userIDList.length(); i++)
            {
                if( (getFriendsListAndID.get(name) == userIDList.get(i)))
                    break;
            }
            if(i == userIDList.length() && !overlapping)
                non_overlappingNames.add(name);
        }

        if (overlapping)
            return overlappingNames;
        return non_overlappingNames;
    }
}
