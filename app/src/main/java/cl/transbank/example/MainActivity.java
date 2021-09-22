package cl.transbank.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;


import java.util.List;

import cl.transbank.example.model.CreateTransactionResponse;
import cl.transbank.example.model.Post;
import cl.transbank.example.service.PostService;
import cl.transbank.example.service.WebPayService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "cl.transbank.example.MESSAGE";
    public static final String BACKEND_URL = "https://transbank-android-backend-node.herokuapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadImage();
    }

    private void loadImage(){
        ImageView imageView = (ImageView) findViewById(R.id.productImgView);
        String imageUrl = "https://ed.team/sites/default/files/styles/16_9_medium/public/2018-04/guia-de-estilos.jpg?itok=73JysFzx";
        try {
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void openWebPay(int productId){
        String url = "https://transbank-android-backend-node.herokuapp.com/webpay-plus/create?from=browser_android&producId=" + productId;
        Log.i("openWebPay",url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void pay(View view) {
        openWebPay(888);
    }

    public void openWebPayInWebView(View view) {
        Intent intent = new Intent(this, WebPayInWebViewActivity.class);
        startActivity(intent);
    }








    /*
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.txtTest);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/

    /*
    public void get(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //EditText editText = (EditText) findViewById(R.id.txtTest);
                        //editText.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }*/

    /*
    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<Post>> call = postService.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, retrofit2.Response<List<Post>> response) {
                for(Post post : response.body()) {
                    Log.i("Titulo",post.getTitle());
                    //titles.add(post.getTitle());
                }
                //arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            }
        });
    }
    */

    /*
    private void createTransaction() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebPayService webPayService = retrofit.create(WebPayService.class);
        Call<CreateTransactionResponse> call = webPayService.createTransaction();
        call.enqueue(new Callback<CreateTransactionResponse>() {
            @Override
            public void onResponse(Call<CreateTransactionResponse> call, retrofit2.Response<CreateTransactionResponse> response) {
                Log.i("createTransaction",response.body().getUrl());
                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(response.body().getUrl()));
                startActivity(i);
            }
            @Override
            public void onFailure(Call<CreateTransactionResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
                t.printStackTrace();
            }
        });
    }*/
}