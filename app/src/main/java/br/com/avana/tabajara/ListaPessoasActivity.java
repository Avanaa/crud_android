package br.com.avana.tabajara;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import br.com.avana.tabajara.adapter.ListaPessoasAdapter;
import br.com.avana.tabajara.impl.RepositorioArrayImpl;
import br.com.avana.tabajara.model.Pessoa;
import br.com.avana.tabajara.util.Constants;

public class ListaPessoasActivity extends AppCompatActivity {

    public RepositorioArrayImpl repo;
    private ListView listaPessoas;
    private ListaPessoasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaPessoas = (ListView) findViewById(R.id.lista_pessoas_listview);
        repo = new RepositorioArrayImpl();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.lista_pessoas_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goForm(null,Constants.CREATE_REGISTER);
            }
        });
        registerForContextMenu(listaPessoas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pessoas, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchview = (SearchView) searchItem.getActionView();

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter(s);
                    listaPessoas.clearTextFilter();
                } else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case Constants.CREATE_REGISTER:

                if (resultCode == Constants.CREATE_REGISTER_OK){

                    Pessoa pessoa = (Pessoa) data.getSerializableExtra("pessoa");

                    if(repo.inserir(pessoa)){
                        Toast.makeText(this, R.string.lista_pessoas_saved, Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder builder = createBuilderNovoRegistro(pessoa);
                        builder.show();
                    }
                }
                break;

            case Constants.UPDATE_REGISTER:

                if (resultCode == Constants.UPDATE_REGISTER_OK){
                    Pessoa pessoa = (Pessoa) data.getSerializableExtra("pessoa");
                    AlertDialog.Builder builder = createBuilderAtualizar(pessoa);
                    builder.show();
                }

                if (resultCode == Constants.DELETE_REGISTER_OK){
                    Pessoa pessoa = (Pessoa) data.getSerializableExtra("pessoa");
                    AlertDialog.Builder builder = createBuilderDeletar(pessoa);
                    builder.show();
                }
        }
    }

    private AlertDialog.Builder createBuilderNovoRegistro(final Pessoa pessoa) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.lista_pessoas_popup_insert);
        builder.setMessage(getString(R.string.lista_pessoas_popup_insert_desc));

        builder.setPositiveButton(R.string.lista_pessoas_popup_yes, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goForm(pessoa, Constants.CREATE_REGISTER);
            }
        });

        builder.setNegativeButton(R.string.lista_pessoas_popup_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.lista_pessoas_popup_canceled, Toast.LENGTH_LONG).show();
            }
        });
        return builder;
    }

    private AlertDialog.Builder createBuilderAtualizar(final Pessoa pessoa) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.lista_pessoas_popup_update);
        builder.setMessage(getString(R.string.lista_pessoas_popup_update_desc));

        builder.setPositiveButton(R.string.lista_pessoas_popup_yes, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (repo.atualizar(pessoa)){
                    Toast.makeText(getApplicationContext(), R.string.lista_pessoas_updated, Toast.LENGTH_LONG).show();
                    createList();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.lista_pessoas_update_err, Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton(R.string.lista_pessoas_popup_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goForm(pessoa, Constants.CREATE_REGISTER);
            }
        });

        return builder;
    }

    private AlertDialog.Builder createBuilderDeletar(final Pessoa pessoa) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.lista_pessoas_popup_delete);
        builder.setMessage(R.string.lista_pessoas_popup_delete_desc);

        builder.setPositiveButton(R.string.lista_pessoas_popup_yes, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (repo.remover(pessoa)){
                    Toast.makeText(getApplicationContext(), R.string.lista_pessoas_deleted, Toast.LENGTH_LONG).show();
                    createList();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.lista_pessoas_delete_err, Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton(R.string.lista_pessoas_popup_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goForm(pessoa, Constants.CREATE_REGISTER);
            }
        });
        return builder;
    }

    public void goForm(Pessoa pessoa, int code) {
        Intent goForm = new Intent(getApplicationContext(), FormularioActivity.class);
        goForm.putExtra("pessoa", pessoa);
        startActivityForResult(goForm, code);
    }

    private void createList() {
        if (repo.getActualSize() >= 0){

            adapter = new ListaPessoasAdapter(repo.getPessoas(), this);
            listaPessoas.setAdapter(adapter);

        } else {
            Toast.makeText(this, R.string.lista_pessoas_lista_vazia, Toast.LENGTH_LONG).show();
        }
    }
}
