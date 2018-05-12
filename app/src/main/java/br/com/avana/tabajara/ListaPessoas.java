package br.com.avana.tabajara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.com.avana.tabajara.impl.RepositorioArrayImpl;
import br.com.avana.tabajara.model.Pessoa;

public class ListaPessoas extends AppCompatActivity {

    public RepositorioArrayImpl repo = new RepositorioArrayImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.lista_pessoas_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goForm = new Intent(getApplicationContext(), FormularioActivity.class);
                startActivityForResult(goForm, 1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (repo.getActualSize() > 0){
            ListView listaPessoas = (ListView) findViewById(R.id.lista_pessoas_listview);
            ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(
                    this, android.R.layout.simple_expandable_list_item_1, repo.getPessoas());
            listaPessoas.setAdapter(adapter);
        }
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
}
