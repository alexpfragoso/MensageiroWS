package com.alexpfragosoatgmail.mensageirows.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alexpfragosoatgmail.mensageirows.R;

public class TelaLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button entrarBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        //getSupportActionBar().hide();

        entrarBT = (Button)findViewById(R.id.bt_entrar);
        entrarBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == entrarBT) {

            Intent mostrarContatosIntent = new Intent(this,ContatosActivity.class);
            startActivity(mostrarContatosIntent);
        }
    }
}
