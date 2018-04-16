package com.alexpfragosoatgmail.mensageirows.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alexpfragosoatgmail.mensageirows.R;
import com.alexpfragosoatgmail.mensageirows.api.MensageiroApi;
import com.alexpfragosoatgmail.mensageirows.model.Contato;
import com.alexpfragosoatgmail.mensageirows.model.Mensagem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContatosActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button abrirAppBT;
    private ListView contatosLV;
    private List<String> nomeContatos;
    private List<Contato> listaContatos;

    private List<Mensagem> listaMensagensFinal;
    private String idUsuario = "3";
    private String idAmigo = "1";
    private String apelidoContato = "Contato";


    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        contatosLV = (ListView)findViewById(R.id.lv_mostrar_contatos);
        contatosLV.setOnItemClickListener(this);


        abrirAppBT= (Button) findViewById(R.id.bt_abrir_app);
        abrirAppBT.setOnClickListener(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();

        mensageiroApi = retrofit.create(MensageiroApi.class);



        Call<List<Contato>> listaContatosCall = mensageiroApi.getContatos();

        listaContatosCall.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {


                listaContatos = response.body();


                nomeContatos = new ArrayList<>();

               for (Contato contato : listaContatos) {

                   nomeContatos.add(contato.getApelido());

                }
               ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ContatosActivity.this,android.R.layout.simple_list_item_1,nomeContatos);

                contatosLV.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {
                Toast.makeText(ContatosActivity.this, "Erro AQUI na recuperação dos contatos!",Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View view) {

        if (view == abrirAppBT) {

            Intent telePrincipalIntent = new Intent(this,MainActivity.class);
            startActivity(telePrincipalIntent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        if (listaContatos != null) {

            Contato contato = listaContatos.get(position);

            if (contato != null) {
                idAmigo = contato.getId();
                apelidoContato = contato.getApelido();


                //PRIMEIRA CHAMADA PARA RECUPERAR MENSAGENS

                Call<List<Mensagem>> listaMensagensCall = mensageiroApi.getMensagens("0", idUsuario, idAmigo);

                listaMensagensCall.enqueue(new Callback<List<Mensagem>>() {
                    @Override
                    public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {

                        final List<Mensagem> listaMensagens = response.body();
                        listaMensagensFinal = response.body();

                        //SEGUNDA   CHAMADA PARA RECUPERAR MENSAGENS
                        Call<List<Mensagem>> listaMensagensCall2 = mensageiroApi.getMensagens("0", idAmigo, idUsuario);


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
                                for (Mensagem mensagem : listaMensagensFinal) {
                                    corpoMensagens.add(mensagem.getCorpo());

                                }
                                ArrayList<Mensagem> listaMensagensAL = new ArrayList<>();
                                listaMensagensAL = (ArrayList<Mensagem>) listaMensagensFinal;

                                Intent mostraMensagensIntent = new Intent(ContatosActivity.this, MostrarMensagensOrdenadasActivity.class);
                                mostraMensagensIntent.putExtra("apelido_do_contato",apelidoContato);
                                mostraMensagensIntent.putExtra(getString(R.string.mensagens_string_array_extra), listaMensagensAL);
                                startActivity(mostraMensagensIntent);
                            }

                            @Override
                            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                                Toast.makeText(ContatosActivity.this, "Erro AQUI na recuperação das mensagens!", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                        Toast.makeText(ContatosActivity.this, "Erro AQUI na recuperação das mensagens!", Toast.LENGTH_SHORT).show();

                    }
                });

            }else {Toast.makeText(ContatosActivity.this, "Não existe mensagens entre este contato!", Toast.LENGTH_SHORT).show();}
        } else{Toast.makeText(ContatosActivity.this, "Não existe mensagens!", Toast.LENGTH_SHORT).show();}
    }






}
