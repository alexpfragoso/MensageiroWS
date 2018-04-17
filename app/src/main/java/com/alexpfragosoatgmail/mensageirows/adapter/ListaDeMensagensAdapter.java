package com.alexpfragosoatgmail.mensageirows.adapter;

import android.content.Context;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import com.alexpfragosoatgmail.mensageirows.R;
import com.alexpfragosoatgmail.mensageirows.model.Mensagem;

import java.util.ArrayList;
import java.util.List;

import static com.alexpfragosoatgmail.mensageirows.R.drawable.gradiente1;


/**
 * Created by Alex Fragoso on 29/03/2018.
 */

public class ListaDeMensagensAdapter extends ArrayAdapter<Mensagem>{

    //VARIÁVEIS
    private LayoutInflater layoutInflater;
    String idOrigem;

    //CONSTRUTOR
    public ListaDeMensagensAdapter(@NonNull Context contexto, List<Mensagem> listaMensagens) {
        super(contexto, R.layout.item_lista_mensagem,listaMensagens);
        this.layoutInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.idOrigem = listaMensagens.get(1).getOrigemId();
    }

    @NonNull
    @Override
   public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;


        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.item_lista_mensagem,null);
            holder = new Holder();

            holder.tvMensagemRemetente =(TextView)convertView.findViewById(R.id.tv_mensagem_remetente);
            holder.tvMensagemDestinatario = (TextView)convertView.findViewById(R.id.tv_mensagem_destinatario);
            holder.tvMensagemRemetenteBalao =(TextView)convertView.findViewById(R.id.tv_mensagem_remetente_balao);
            holder.tvMensagemDestinatarioBalao = (TextView)convertView.findViewById(R.id.tv_mensagem_destinatario_balao);
            convertView.setTag(holder);
        }
        else{
            holder = (Holder)convertView.getTag();
        }

        Mensagem mensagem = getItem(position);

        if(mensagem.getOrigemId().equals(idOrigem)){

            holder.tvMensagemDestinatario.setVisibility(View.VISIBLE);
            holder.tvMensagemDestinatarioBalao.setVisibility(View.VISIBLE);
            holder.tvMensagemDestinatario.setText(mensagem.getCorpo());
            holder.tvMensagemRemetente.setVisibility(View.GONE);
            holder.tvMensagemRemetenteBalao.setVisibility(View.GONE);
        }else{

            holder.tvMensagemRemetente.setVisibility(View.VISIBLE);
            holder.tvMensagemRemetenteBalao.setVisibility(View.VISIBLE);
           // holder.tvMensagemRemetente.setText("  " + mensagem.getOrigem().getApelido() + "\n   " + mensagem.getCorpo());
            holder.tvMensagemRemetente.setText(mensagem.getCorpo());
            holder.tvMensagemDestinatario.setVisibility(View.GONE);
            holder.tvMensagemDestinatarioBalao.setVisibility(View.GONE);
        }


        return convertView;
    }

    //CLASSE AUXILIAR
    private static class Holder{
        public TextView tvMensagemRemetente;
        public TextView tvMensagemDestinatario;
        public TextView tvMensagemRemetenteBalao;
        public TextView tvMensagemDestinatarioBalao;

    }
}
