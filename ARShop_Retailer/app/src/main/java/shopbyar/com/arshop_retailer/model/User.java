package shopbyar.com.arshop_retailer.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zijiantang on 29/1/16.
 */
public class User {
    @SerializedName("user_name")
    public String username;
    @SerializedName("access_token")
    public String authToken;
    @SerializedName("user_shops")
    public List<UserShop> shops;
    @SerializedName("code")
    public String code;
    public String message;

    public static User currentUser = null;

    public User() {
        shops = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }
    public String getAuthToken() {
        return authToken;
    }
    public List<UserShop> getShops() {
        return shops;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public void setShops(List<UserShop> shops) {
        this.shops = shops;
    }

    public static void saveCurrentUser(SharedPreferences sp) {
        if (currentUser == null) {
            return;
        }
        SharedPreferences.Editor prefsEditor = sp.edit();
        prefsEditor.remove("prefs_current_user");
        Gson gson = new Gson();
        String json = gson.toJson(currentUser);
        prefsEditor.putString("prefs_current_user", json);
        prefsEditor.commit();
    }

    public static User fetchCurrentUser(SharedPreferences sp) {
        Gson gson = new Gson();
        String json = sp.getString("prefs_current_user", "");
        if (json.isEmpty()) {
            return null;
        }
        currentUser = gson.fromJson(json, User.class);
        return currentUser;
    }

    public static void destoryCurrentUser(SharedPreferences sp) {
        currentUser = null;
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("prefs_current_user");
        editor.commit();
    }
}
