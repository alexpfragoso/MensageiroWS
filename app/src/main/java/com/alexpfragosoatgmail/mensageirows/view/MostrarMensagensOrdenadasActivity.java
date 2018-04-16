package com.alexpfragosoatgmail.mensageirows.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexpfragosoatgmail.mensageirows.R;
import com.alexpfragosoatgmail.mensageirows.adapter.ListaDeMensagensAdapter;
import com.alexpfragosoatgmail.mensageirows.api.MensageiroApi;
import com.alexpfragosoatgmail.mensageirows.model.Contato;
import com.alexpfragosoatgmail.mensageirows.model.Mensagem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarMensagensOrdenadasActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView nomeContatoTV;
    private ListView mostrarMensagensLv;
    private  ArrayList<Mensagem> listaMensagensAL;
    private List<Mensagem> listaMensagensFinal;
    private Contato contato;

    private Button enviarMensagemBT;
    private EditText mensagemProntaParaEnvioET;
    private  ListaDeMensagensAdapter arrayAdapter;

    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mensagens_ordenadas);

        mostrarMensagensLv = findViewById(R.id.lv_mostrar_mensagens_ordenadas);

        nomeContatoTV = (TextView)findViewById(R.id.tv_nome_contato_em_conversa);

        contato = ((Contato)getIntent().getSerializableExtra("contato"));
        nomeContatoTV.setText("       "+ contato.getApelido());

        listaMensagensAL = (ArrayList<Mensagem>) getIntent().getSerializableExtra(getString(R.string.mensagens_string_array_extra));

        arrayAdapter = new ListaDeMensagensAdapter(this,(List<Mensagem>) listaMensagensAL);

        mostrarMensagensLv.setAdapter(arrayAdapter);
        mostrarMensagensLv.setSelection(listaMensagensAL.size());

        enviarMensagemBT = (Button)findViewById(R.id.bt_enviar_mensagem);
        enviarMensagemBT.setOnClickListener(this);
        mensagemProntaParaEnvioET = (EditText)findViewById(R.id.et_mensagem_pronta_para_envio);

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


        if(view.getId() == R.id.bt_enviar_mensagem){

            Mensagem mensagem = new Mensagem();
            mensagem.setCorpo(mensagemProntaParaEnvioET.getText().toString());
            mensagem.setAssunto("texto");
            mensagem.setOrigemId("3");
            mensagem.setDestinoId(contato.getId());

            RequestBody mensagemRB = RequestBody.create(MediaType.parse("application/json"),gson.toJson(mensagem));

            mensageiroApi.postMensagem(mensagemRB).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(MostrarMensagensOrdenadasActivity.this,"Mensagem enviada!",Toast.LENGTH_SHORT).show();
                    limparCampos();
                    atualizarMensagens();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MostrarMensagensOrdenadasActivity.this,"Erro no envio da mensagem!",Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private void limparCampos(){
        mensagemProntaParaEnvioET.setText("");

    }

    private void atualizarMensagens(){
        //PRIMEIRA CHAMADA PARA RECUPERAR MENSAGENS

        Call<List<Mensagem>> listaMensagensCall = mensageiroApi.getMensagens("0", "3", contato.getId());

        listaMensagensCall.enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {

                listaMensagensFinal = response.body();

                //SEGUNDA   CHAMADA PARA RECUPERAR MENSAGENS
                Call<List<Mensagem>> listaMensagensCall2 = mensageiroApi.getMensagens("0", contato.getId(), "3");

                listaMensagensCall2.enqueue(new Callback<List<Mensagem>>() {
                    @Override
                    public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {

                        List<Mensagem> listaMensagens2 = response.body();

                        for (Mensagem mensagem : listaMensagens2) {
                            listaMensagensFinal.add(mensagem);

                        }

                        //ORDENANDO LIST DE MENSAGENS
                        Collections.sort(listaMensagensFinal);

                        listaMensagensAL = (ArrayList<Mensagem>) listaMensagensFinal;

                        //arrayAdapter.notifyDataSetChanged();
                        arrayAdapter = new ListaDeMensagensAdapter(MostrarMensagensOrdenadasActivity.this,listaMensagensAL);

                        mostrarMensagensLv.setAdapter(arrayAdapter);
                        mostrarMensagensLv.setSelection(listaMensagensAL.size());
                    }

                    @Override
                    public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                        Toast.makeText(MostrarMensagensOrdenadasActivity.this, "Erro AQUI na recuperação das mensagens!", Toast.LENGTH_SHORT).show();

                    }
                });


            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                Toast.makeText(MostrarMensagensOrdenadasActivity.this, "Erro AQUI na recuperação das mensagens!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
