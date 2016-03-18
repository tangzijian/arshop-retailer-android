package shopbyar.com.arshop_retailer.rest.service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import shopbyar.com.arshop_retailer.model.LoginUser;
import shopbyar.com.arshop_retailer.model.User;

/**
 * Created by zijiantang on 29/1/16.
 */
public interface APIService {
    @FormUrlEncoded
    @POST("users/signup/")
    Call<User> userSignup(@FieldMap Map<String, String> params);

    @PUT("users/login/")
    Call<User> userLogin(@Body LoginUser user);

    @POST("users/login_check/")
    Call<User> userVerifyToken(@Body User user);
}
