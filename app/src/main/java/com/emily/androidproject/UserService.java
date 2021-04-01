package com.emily.androidproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/Access/Login")
  Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);


}
