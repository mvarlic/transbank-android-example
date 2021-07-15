package cl.transbank.example.service;

import cl.transbank.example.model.CreateTransactionResponse;
import retrofit2.Call;
import retrofit2.http.POST;

public interface WebPayService {

    String API_ROUTE = "/webpay-plus/create-transaction";

    @POST(API_ROUTE)
    Call<CreateTransactionResponse> createTransaction();

}
