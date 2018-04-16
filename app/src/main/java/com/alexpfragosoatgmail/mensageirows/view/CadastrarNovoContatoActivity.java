package com.alexpfragosoatgmail.mensageirows.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alexpfragosoatgmail.mensageirows.R;
import com.alexpfragosoatgmail.mensageirows.api.MensageiroApi;
import com.alexpfragosoatgmail.mensageirows.model.Contato;
import com.alexpfragosoatgmail.mensageirows.model.Mensagem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastrarNovoContatoActivity extends AppCompatActivity {

    private EditText nomeNovoContatoET;
    private EditText apelidoNovoContatoEt;

    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_novo_contato);

        nomeNovoContatoET = (EditText) findViewById(R.id.et_nome_novo_contato_adicionado);
        apelidoNovoContatoEt = (EditText) findViewById(R.id.et_apelido_novo_contato);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();

        mensageiroApi = retrofit.create(MensageiroApi.class);
    }


    public void onClick(View view) {

        if(view.getId() == R.id.bt_cadastrar_novo_contato){

            Contato novoContato = new Contato();
            novoContato.setNomeCompleto(nomeNovoContatoET.getText().toString());
            novoContato.setApelido(apelidoNovoContatoEt.getText().toString());

            RequestBody novoContatoRB = RequestBody.create(MediaType.parse("application/json"),gson.toJson(novoContato));

            mensageiroApi.postContato(novoContatoRB).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(CadastrarNovoContatoActivity.this,"Contato cadastrado!",Toast.LENGTH_SHORT).show();
                    limparCampos();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(CadastrarNovoContatoActivity.this,"Erro no cadastro do contato!",Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private void limparCampos(){
        nomeNovoContatoET.setText("");
        apelidoNovoContatoEt.setText("");

    }
}

