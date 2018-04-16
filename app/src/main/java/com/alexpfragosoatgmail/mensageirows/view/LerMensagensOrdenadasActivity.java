package com.alexpfragosoatgmail.mensageirows.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alexpfragosoatgmail.mensageirows.R;
import com.alexpfragosoatgmail.mensageirows.api.MensageiroApi;
import com.alexpfragosoatgmail.mensageirows.model.Mensagem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LerMensagensOrdenadasActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText origemEt;
    private EditText destinoEt;
    private EditText ultimaMensagemEt;
    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;
    private List<Mensagem> listaMensagensFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_mensagens_ordenadas);

        origemEt = findViewById(R.id.et_origem_ler_ordenada);
        destinoEt = findViewById(R.id.et_destino_ler_ordenada);
        ultimaMensagemEt = findViewById(R.id.et_ultima_mensagem_ler_ordenada);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();

        mensageiroApi = retrofit.create(MensageiroApi.class);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.bt_ler_ordenada){


            //PRIMEIRA CHAMADA PARA RECUPERAR MENSAGENS

            Call<List<Mensagem>> listaMensagensCall = mensageiroApi.getMensagens(ultimaMensagemEt.getText().toString(),origemEt.getText().toString(),destinoEt.getText().toString());

            listaMensagensCall.enqueue(new Callback<List<Mensagem>>() {
                @Override
                public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {

                    final List<Mensagem> listaMensagens = response.body();
                    listaMensagensFinal = response.body();

                    //SEGUNDA   CHAMADA PARA RECUPERAR MENSAGENS
                    Call<List<Mensagem>> listaMensagensCall2 = mensageiroApi.getMensagens(ultimaMensagemEt.getText().toString(),destinoEt.getText().toString(),origemEt.getText().toString());


                    listaMensagensCall2.enqueue(new Callback<List<Mensagem>>() {
                    @Override
                    public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {


                        List<Mensagem> listaMensagens2 = response.body();

                        for (Mensagem mensagem : listaMensagens2) {
                            listaMensagensFinal.add(mensagem);

                        }

                        //ORDENANDO LIST DE MENSAGENS

                        Collections.sort(listaMensagensFinal);
                        ArrayList<String> corpoMensagens = new ArrayList<>();
                        for(Mensagem mensagem : listaMensagensFinal){
                            corpoMensagens.add(mensagem.getCorpo());

                        }
                        ArrayList<Mensagem> listaMensagensAL = new ArrayList<>();
                        listaMensagensAL = (ArrayList<Mensagem>) listaMensagensFinal;

                        Intent mostraMensagensIntent = new Intent(LerMensagensOrdenadasActivity.this,MostrarMensagensOrdenadasActivity.class);
                        mostraMensagensIntent.putExtra(getString(R.string.mensagens_string_array_extra),listaMensagensAL);
                        startActivity(mostraMensagensIntent);
                    }

                    @Override
                    public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                        Toast.makeText(LerMensagensOrdenadasActivity.this, "Erro AQUI na recuperação das mensagens!",Toast.LENGTH_SHORT).show();

                    }
                    });


                }

                @Override
                public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                    Toast.makeText(LerMensagensOrdenadasActivity.this, "Erro AQUI na recuperação das mensagens!",Toast.LENGTH_SHORT).show();

                }
            });



        }

    }
}
