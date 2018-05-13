package br.com.avana.tabajara.helper;

import android.view.View;
import android.widget.EditText;

import br.com.avana.tabajara.FormularioActivity;
import br.com.avana.tabajara.R;
import br.com.avana.tabajara.model.Pessoa;
import br.com.avana.tabajara.model.Endereco;
import br.com.avana.tabajara.model.EnderecoNet;

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

    private Pessoa pessoa;

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

        pessoa = new Pessoa();
    }

    public Pessoa getPessoa(){

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
        endereco.setLogradouro(editTextRua.getText().toString());
        endereco.setNumero(editTextNumero.getText().toString());
        endereco.setComplemento(editTextComplemento.getText().toString());
        endereco.setBairro(editTextBairro.getText().toString());
        endereco.setLocalidade(editTextCidade.getText().toString());
        endereco.setUf(editTextEstado.getText().toString());

        return endereco;
    }

    public void setPessoa(Pessoa pessoa){

        editTextCodigo.setText(pessoa.getNumero());
        editTextNome.setText(pessoa.getNome());
        editTextCpf.setText(pessoa.getCpf());
        editTextTelefone.setText(pessoa.getTelefone());
        editTextEmail.setText(pessoa.getEmail());

        setEndereco(pessoa.getEndereco());
    }

    private void setEndereco(Endereco endereco){

        editTextCep.setText(endereco.getCep());
        editTextCep.setVisibility(View.VISIBLE);

        editTextRua.setText(endereco.getLogradouro());
        editTextRua.setVisibility(View.VISIBLE);

        editTextNumero.setText(endereco.getNumero());
        editTextNumero.setVisibility(View.VISIBLE);

        editTextComplemento.setText(endereco.getComplemento());
        editTextComplemento.setVisibility(View.VISIBLE);

        editTextBairro.setText(endereco.getBairro());
        editTextBairro.setVisibility(View.VISIBLE);

        editTextCidade.setText(endereco.getLocalidade());
        editTextCidade.setVisibility(View.VISIBLE);

        editTextEstado.setText(endereco.getUf());
        editTextEstado.setVisibility(View.VISIBLE);
    }

    public String getCep() {
        return  editTextCep.getText().toString();
    }

    public void setEnderecoByCep(EnderecoNet enderecoNet) {
        Endereco endereco = new Endereco();

        endereco.setCep(enderecoNet.getCep());
        endereco.setLogradouro(enderecoNet.getLogradouro());
        endereco.setComplemento(enderecoNet.getComplemento());
        endereco.setBairro(enderecoNet.getBairro());
        endereco.setLocalidade(enderecoNet.getLocalidade());
        endereco.setUf(enderecoNet.getUf());

        setEndereco(endereco);
    }
}
