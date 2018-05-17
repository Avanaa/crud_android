package br.com.avana.tabajara.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.avana.tabajara.FormularioActivity;
import br.com.avana.tabajara.ListaPessoasActivity;
import br.com.avana.tabajara.R;
import br.com.avana.tabajara.model.Pessoa;
import br.com.avana.tabajara.util.Constants;

public class ListaPessoasAdapter extends BaseAdapter {

    private List<Pessoa> pessoas;
    private  Pessoa[] pessoasArray;
    private ListaPessoasActivity activity;

    public ListaPessoasAdapter(Pessoa[] pessoas, ListaPessoasActivity activity) {

        pessoasArray = pessoas;

        this.pessoas = new ArrayList<Pessoa>();

        this.pessoas.addAll(Arrays.asList(pessoas));
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pessoas.size();
    }

    @Override
    public Pessoa getItem(int position) {
        return pessoas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = activity.getLayoutInflater().inflate(R.layout.item_lista_pessoas, parent, false);

        final Pessoa pessoa = getItem(position);

        TextView descricao = view.findViewById(R.id.item_lista_descricao);
        descricao.setText(pessoa.toString());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.goForm(pessoa, Constants.UPDATE_REGISTER);
            }
        });
        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase();

        pessoas.clear();
        if (charText.equals("")){
            pessoas.addAll(Arrays.asList(pessoasArray));
        } else {
            for (Pessoa pessoa : pessoasArray){
                if (pessoa.toString().toLowerCase().contains(charText)){
                    pessoas.add(pessoa);
                }
            }
        }
        notifyDataSetChanged();
    }
}
