package com.alexpfragosoatgmail.mensageirows.view;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.alexpfragosoatgmail.mensageirows.R;

public class NovoContatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String mensagemDaNotificacao = getIntent().getStringExtra(getString(R.string.mensagem_da_notificacao_extra));

        if(mensagemDaNotificacao != null){
            Toast.makeText(this, mensagemDaNotificacao, Toast.LENGTH_SHORT).show();
        }

        TextView tvNovoContato = new TextView(this);
        tvNovoContato.setText("Informações do novo contato");
        setContentView(tvNovoContato);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        nm.cancelAll();
    }
}
