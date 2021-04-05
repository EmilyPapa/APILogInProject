package com.emily.androidproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


public interface BookService {
      //Get for books
      //Data interpretation as Json
      // in my Call I am missing the @Header where the Authorization token takes place
      // I was unable to execute that part even though I knew what it required

      @GET("/Access/Books")
      Call<List<Book>> getBooks();
}
