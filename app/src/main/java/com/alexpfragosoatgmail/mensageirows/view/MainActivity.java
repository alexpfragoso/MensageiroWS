package com.alexpfragosoatgmail.mensageirows.view;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alexpfragosoatgmail.mensageirows.R;
import com.alexpfragosoatgmail.mensageirows.service.BuscaNovoContatoService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Intent serviceIntent;

    private String[]opcoes = new String[]{"Cadastrar novo contato","Enviar mensagem","Ler mensagens","Ler mensagens ordenadas","Sair","Tela de Entrada"};

    private ListView principalLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        principalLV = (ListView)findViewById(R.id.lv_principal);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,opcoes);

        principalLV.setAdapter(arrayAdapter);
        principalLV.setOnItemClickListener(this);

        serviceIntent = new Intent(getApplicationContext(),BuscaNovoContatoService.class);
        startService(serviceIntent); //novidade

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        switch (principalLV.getAdapter().getItem(position).toString()){

            case "Ler mensagens ordenadas":
                startActivity(new Intent(this,LerMensagensOrdenadasActivity.class));
                break;
            case "Cadastrar novo contato":
                startActivity(new Intent(this,CadastrarNovoContatoActivity.class));
                break;

            case "Enviar mensagem":
                startActivity(new Intent(this,EnviarActivity.class));
                break;

            case "Ler mensagens":
                startActivity(new Intent(this,LerActivity.class));
                break;

            case "Tela de Entrada":
                startActivity(new Intent(this,TelaLoginActivity.class));
                break;

            case "Sair":
                default:
                    finish();
        }

    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
        super.onDestroy();

    }
}
