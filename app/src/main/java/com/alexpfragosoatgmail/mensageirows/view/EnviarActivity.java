package com.alexpfragosoatgmail.mensageirows.view;

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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnviarActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText origemET;
    private EditText destinoET;
    private EditText assuntoET;
    private EditText corpoET;

    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        origemET = (EditText) findViewById(R.id.et_origem_enviar);
        destinoET = (EditText) findViewById(R.id.et_destino_enviar);
        assuntoET = (EditText) findViewById(R.id.et_assunto_enviar);
        corpoET = (EditText) findViewById(R.id.et_corpo_enviar);

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

        if(view.getId() == R.id.bt_enviar){

            Mensagem mensagem = new Mensagem();
            mensagem.setCorpo(corpoET.getText().toString());
            mensagem.setAssunto(assuntoET.getText().toString());
            mensagem.setOrigemId(origemET.getText().toString());
            mensagem.setDestinoId(destinoET.getText().toString());

            RequestBody mensagemRB = RequestBody.create(MediaType.parse("application/json"),gson.toJson(mensagem));

            mensageiroApi.postMensagem(mensagemRB).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(EnviarActivity.this,"Mensagem enviada!",Toast.LENGTH_SHORT).show();
                    limparCampos();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(EnviarActivity.this,"Erro no envio da mensagem!",Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private void limparCampos(){
        origemET.setText("");
        destinoET.setText("");
        assuntoET.setText("");
        corpoET.setText("");

    }
}
