package br.com.avana.tabajara;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.avana.tabajara.async.EnderecoAsyncTask;
import br.com.avana.tabajara.helper.FormularioHelper;
import br.com.avana.tabajara.model.Pessoa;
import br.com.avana.tabajara.util.Constants;
import br.com.avana.tabajara.util.MaskType;
import br.com.avana.tabajara.util.MaskUtil;

public class FormularioActivity extends AppCompatActivity {

    public FormularioHelper helper;
    private Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Toolbar toolbar = findViewById(R.id.formulario_toolbar);
        setSupportActionBar(toolbar);

        helper = new FormularioHelper(this);

        Button btnValidaCep = findViewById(R.id.formulario_action_valida_cep);
        btnValidaCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] params = {helper.getCep()};
                new EnderecoAsyncTask(FormularioActivity.this).execute(params);
            }
        });

        EditText editTextCpf = findViewById(R.id.formulario_cpf);
        editTextCpf.addTextChangedListener(MaskUtil.insert(editTextCpf, MaskType.CPF_MASK_TYPE));

        EditText editTextTel = findViewById(R.id.formulario_telefone);
        editTextTel.addTextChangedListener(MaskUtil.insert(editTextTel, MaskType.PHONE_MASK_TPYE));

        EditText editTextCep = findViewById(R.id.formulario_end_cep);
        editTextCep.addTextChangedListener(MaskUtil.insert(editTextCep, MaskType.CEP_MASK_TYPE));
    }

    @Override
    protected void onResume() {
        super.onResume();

        pessoa = (Pessoa) getIntent().getSerializableExtra("pessoa");
        if (pessoa != null){
            helper.setPessoa(pessoa);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (pessoa == null){
            MenuItem delete = menu.findItem(R.id.formulario_action_delete);
            delete.setVisible(false);
        }
        return true;
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
                if(helper.validarFormulario()){

                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);

                    Pessoa p = helper.getPessoa();

                    if (pessoa != null){
                        p.setId(pessoa.getId());
                    }
                    resultIntent.putExtra("pessoa", p);
                    finish();
                }
                break;

            case R.id.formulario_action_delete:

                if (pessoa != null){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("pessoa", pessoa);

                    setResult(Constants.DELETE_REGISTER, resultIntent);
                    finish();
                }
        }
        return true;
    }
}
