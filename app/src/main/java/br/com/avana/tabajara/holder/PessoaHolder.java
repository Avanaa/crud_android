package br.com.avana.tabajara.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.avana.tabajara.R;

public class PessoaHolder extends RecyclerView.ViewHolder {

    private TextView descricao;

    public PessoaHolder(View itemView) {
        super(itemView);

        descricao = itemView.findViewById(R.id.item_lista_descricao);
    }

    public TextView getDescricao() {
        return descricao;
    }
}
