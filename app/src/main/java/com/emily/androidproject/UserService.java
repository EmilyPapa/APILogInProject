package com.emily.androidproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    //Post request for Login
    //Data interpretation as Json

    @POST("/Access/Login")
  Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);


}
