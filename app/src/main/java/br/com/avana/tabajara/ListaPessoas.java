package br.com.avana.tabajara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.com.avana.tabajara.impl.RepositorioArrayImpl;
import br.com.avana.tabajara.model.Pessoa;

public class ListaPessoas extends AppCompatActivity {

    public RepositorioArrayImpl repo = new RepositorioArrayImpl();
    private ListView listaPessoas;

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
                Intent goForm = new Intent(getApplicationContext(), FormularioActivity.class);
                startActivityForResult(goForm, 1);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "Ação ainda não disponível", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                repo.inserir((Pessoa) data.getSerializableExtra("pessoa"));
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem menuItem = menu.add("Apagar");

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Pessoa pessoa = (Pessoa) listaPessoas.getItemAtPosition(info.position);
                repo.remover(pessoa);
                createList();
                return false;
            }
        });
    }

    private void createList() {
        if (repo.getActualSize() > 0){
            ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(
                    this, android.R.layout.simple_expandable_list_item_1, repo.getPessoas());
            listaPessoas.setAdapter(adapter);
        }
    }

}
