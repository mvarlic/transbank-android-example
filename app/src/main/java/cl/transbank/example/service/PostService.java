package cl.transbank.example.service;

import java.util.List;

import cl.transbank.example.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {

    String API_ROUTE = "/posts";

    @GET(API_ROUTE)
    Call<List<Post>> getPost();

}
