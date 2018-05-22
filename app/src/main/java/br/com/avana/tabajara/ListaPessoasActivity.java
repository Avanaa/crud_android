package br.com.avana.tabajara;

import android.app.Activity;
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
import br.com.avana.tabajara.dao.PessoaDAOImpl;
import br.com.avana.tabajara.model.Pessoa;
import br.com.avana.tabajara.util.Constants;

public class ListaPessoasActivity extends AppCompatActivity {

    private ListView listaPessoas;
    private ListaPessoasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaPessoas = (ListView) findViewById(R.id.lista_pessoas_listview);

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

        PessoaDAOImpl dao = new PessoaDAOImpl(this);

        switch (requestCode){

            case Constants.CREATE_REGISTER:

                if (resultCode == Activity.RESULT_OK){
                    Pessoa pessoa = (Pessoa) data.getSerializableExtra("pessoa");
                    dao.insert(pessoa);
                }
                break;

            case Constants.UPDATE_REGISTER:

                if (resultCode == Activity.RESULT_OK){
                    Pessoa pessoa = (Pessoa) data.getSerializableExtra("pessoa");
                    dao.update(pessoa);
                }

                if (resultCode == Constants.DELETE_REGISTER){
                    Pessoa pessoa = (Pessoa) data.getSerializableExtra("pessoa");
                    AlertDialog.Builder builder = createBuilderDeletar(pessoa);
                    builder.show();
                }
                break;
        }
        createList();
        dao.close();
    }

    private AlertDialog.Builder createBuilderDeletar(final Pessoa pessoa) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.lista_pessoas_popup_delete);
        builder.setMessage(R.string.lista_pessoas_popup_delete_desc);

        builder.setPositiveButton(R.string.lista_pessoas_popup_yes, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PessoaDAOImpl dao = new PessoaDAOImpl(ListaPessoasActivity.this);
                dao.delete(pessoa);
                dao.close();
                Toast.makeText(getApplicationContext(), R.string.lista_pessoas_deleted, Toast.LENGTH_LONG).show();
                createList();
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

        PessoaDAOImpl dao = new PessoaDAOImpl(this);
        adapter = new ListaPessoasAdapter(dao.getAll(), this);
        listaPessoas.setAdapter(adapter);
        dao.close();
    }
}
