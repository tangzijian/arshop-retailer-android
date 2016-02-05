package shopbyar.com.arshop_retailer.rest.service;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.POST;
import retrofit.http.PUT;
import shopbyar.com.arshop_retailer.model.LoginUser;
import shopbyar.com.arshop_retailer.model.RegisterUser;
import shopbyar.com.arshop_retailer.model.User;

/**
 * Created by zijiantang on 29/1/16.
 */
public interface APIService {
    @POST("users/signup/")
    Call<User> userSignup(@Body RegisterUser user);

    @PUT("users/login/")
    Call<User> userLogin(@Body LoginUser user);

    @POST("users/login_check/")
    Call<User> userVerifyToken(@Body User user);
}
