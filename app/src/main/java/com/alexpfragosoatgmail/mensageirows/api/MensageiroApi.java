package com.alexpfragosoatgmail.mensageirows.api;

import com.alexpfragosoatgmail.mensageirows.model.Contato;
import com.alexpfragosoatgmail.mensageirows.model.Mensagem;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Alex Fragoso on 23/03/2018.
 */

public interface MensageiroApi {

    @POST("mensagem")
    Call<ResponseBody> postMensagem(@Body RequestBody novoContato);

    @POST("contato")
    Call<ResponseBody> postContato(@Body RequestBody novoContato);

    @GET("rawmensagens/{ultimaMensagemId}/{origemId}/{destinoId}")
    Call<List<Mensagem>> getMensagens(@Path("ultimaMensagemId") String ultimaMensagemId, @Path("origemId") String origemId, @Path("destinoId") String destinoId);

    @GET("rawcontatos")
    Call<List<Contato>> getContatos();
}
