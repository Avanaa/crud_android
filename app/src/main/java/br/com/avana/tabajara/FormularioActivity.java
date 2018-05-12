package br.com.avana.tabajara;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.avana.tabajara.helper.FormularioHelper;
import br.com.avana.tabajara.model.Pessoa;

public class FormularioActivity extends AppCompatActivity {

    FormularioHelper helper;
    private Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        pessoa = (Pessoa) getIntent().getSerializableExtra("pessoa");
        if (pessoa != null){
            helper.setPessoa(pessoa);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.formulario_action_save:

                Intent resultIntent = new Intent();
                resultIntent.putExtra("pessoa", helper.getPessoa());

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
