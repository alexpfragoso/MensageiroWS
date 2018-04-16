package com.alexpfragosoatgmail.mensageirows.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alexpfragosoatgmail.mensageirows.R;
import com.alexpfragosoatgmail.mensageirows.adapter.ListaDeMensagensAdapter;
import com.alexpfragosoatgmail.mensageirows.model.Mensagem;

import java.util.ArrayList;
import java.util.List;

public class MostrarMensagensOrdenadasActivity extends AppCompatActivity {

    private TextView nomeContatoTV;
    private ListView mostrarMensagensLv;
    private  ArrayList<Mensagem> listaMensagensAL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mensagens_ordenadas);

        mostrarMensagensLv = findViewById(R.id.lv_mostrar_mensagens_ordenadas);

        nomeContatoTV = (TextView)findViewById(R.id.tv_nome_contato_em_conversa);

        nomeContatoTV.setText((String)getIntent().getSerializableExtra("apelido_do_contato"));

        listaMensagensAL = (ArrayList<Mensagem>) getIntent().getSerializableExtra(getString(R.string.mensagens_string_array_extra));

       /* ArrayList<String> corpoMensagens = new ArrayList<>();
        for(Mensagem mensagem : listaMensagensAL){
            corpoMensagens.add(mensagem.getCorpo());

        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,corpoMensagens);

        mostrarMensagensLv.setAdapter(arrayAdapter);*/

        ListaDeMensagensAdapter arrayAdapter = new ListaDeMensagensAdapter(this,(List<Mensagem>) listaMensagensAL);

        mostrarMensagensLv.setAdapter(arrayAdapter);
    }
}
