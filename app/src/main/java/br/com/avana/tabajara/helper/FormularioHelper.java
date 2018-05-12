package br.com.avana.tabajara.helper;

import android.widget.EditText;

import br.com.avana.tabajara.FormularioActivity;
import br.com.avana.tabajara.R;
import br.com.avana.tabajara.model.Pessoa;
import br.com.avana.tabajara.model.Endereco;

public class FormularioHelper {

    private FormularioActivity activity;

    private final EditText editTextCodigo;
    private final EditText editTextNome;
    private final EditText editTextCpf;
    private final EditText editTextEmail;
    private final EditText editTextTelefone;

    private final EditText editTextCep;
    private final EditText editTextRua;
    private final EditText editTextNumero;
    private final EditText editTextComplemento;
    private final EditText editTextBairro;
    private final EditText editTextCidade;
    private final EditText editTextEstado;
    private final EditText editTextPais;

    public FormularioHelper(FormularioActivity activity){

        this.activity = activity;

        editTextCodigo = activity.findViewById(R.id.formulario_numero);
        editTextNome = activity.findViewById(R.id.formulario_nome);
        editTextCpf = activity.findViewById(R.id.formulario_cpf);
        editTextEmail = activity.findViewById(R.id.formulario_email);
        editTextTelefone = activity.findViewById(R.id.formulario_telefone);
        editTextCep = activity.findViewById(R.id.formulario_end_cep);
        editTextRua = activity.findViewById(R.id.formulario_end_rua);
        editTextNumero = activity.findViewById(R.id.formulario_end_numero);
        editTextComplemento = activity.findViewById(R.id.formulario_end_compl);
        editTextBairro = activity.findViewById(R.id.formulario_end_bairro);
        editTextCidade = activity.findViewById(R.id.formulario_end_cidade);
        editTextEstado = activity.findViewById(R.id.formulario_end_estado);
        editTextPais = activity.findViewById(R.id.formulario_end_pais);
    }

    public Pessoa getPessoa(){

        Pessoa pessoa = new Pessoa();

        pessoa.setNumero(editTextCodigo.getText().toString());
        pessoa.setNome(editTextNome.getText().toString());
        pessoa.setCpf(editTextCpf.getText().toString());
        pessoa.setEmail(editTextEmail.getText().toString());
        pessoa.setTelefone(editTextTelefone.getText().toString());

        Endereco endereco = getEndereco();
        pessoa.setEndereco(endereco);

        return pessoa;
    }

    private Endereco getEndereco() {

        Endereco endereco = new Endereco();

        endereco.setCep(editTextCep.getText().toString());
        endereco.setRua(editTextRua.getText().toString());
        endereco.setNumero(editTextNumero.getText().toString());
        endereco.setComplemento(editTextComplemento.getText().toString());
        endereco.setBairro(editTextBairro.getText().toString());
        endereco.setCidade(editTextCidade.getText().toString());
        endereco.setEstado(editTextEstado.getText().toString());
        endereco.setPais(editTextPais.getText().toString());

        return endereco;
    }

    public void setPessoa(Pessoa pessoa){

    }
}
