package shopbyar.com.arshop_retailer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zijiantang on 29/1/16.
 */
public class User {
    @SerializedName("user_name")
    private String username;
    @SerializedName("access_token")
    private String authToken;

    public String getUsername() {
        return username;
    }
    public String getAuthToken() {
        return authToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
