package br.com.avana.tabajara.helper;

import android.view.View;
import android.widget.EditText;

import br.com.avana.tabajara.FormularioActivity;
import br.com.avana.tabajara.R;
import br.com.avana.tabajara.model.Pessoa;
import br.com.avana.tabajara.model.Endereco;
import br.com.avana.tabajara.model.EnderecoNet;
import br.com.avana.tabajara.util.MaskType;
import br.com.avana.tabajara.util.MaskUtil;

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

        pessoa = new Pessoa();
    }

    public Pessoa getPessoa(){

        pessoa.setNumero(editTextCodigo.getText().toString());
        pessoa.setNome(editTextNome.getText().toString());
        pessoa.setCpf(MaskUtil.unmask(editTextCpf.getText().toString()));
        pessoa.setEmail(editTextEmail.getText().toString());
        pessoa.setTelefone(MaskUtil.unmask(editTextTelefone.getText().toString()));

        Endereco endereco = getEndereco();
        pessoa.setEndereco(endereco);

        return pessoa;
    }

    private Endereco getEndereco() {

        Endereco endereco = new Endereco();

        endereco.setCep(MaskUtil.unmask(editTextCep.getText().toString()));
        endereco.setLogradouro(editTextRua.getText().toString());
        endereco.setNumero(editTextNumero.getText().toString());
        endereco.setComplemento(editTextComplemento.getText().toString());
        endereco.setBairro(editTextBairro.getText().toString());
        endereco.setLocalidade(editTextCidade.getText().toString());
        endereco.setUf(editTextEstado.getText().toString());

        return endereco;
    }

    public void setPessoa(Pessoa pessoa){

        if (pessoa != null){
            editTextCodigo.setText(pessoa.getNumero());
            editTextNome.setText(pessoa.getNome());
            editTextCpf.setText(MaskUtil.masker(pessoa.getCpf(), MaskType.CPF_MASK_TYPE));
            editTextTelefone.setText(MaskUtil.masker(pessoa.getTelefone(), MaskType.PHONE_MASK_TPYE));
            editTextEmail.setText(pessoa.getEmail());

            setEndereco(pessoa.getEndereco());
        }
    }

    private void setEndereco(Endereco endereco){

        editTextCep.setText(MaskUtil.masker(endereco.getCep(), MaskType.CEP_MASK_TYPE));
        editTextRua.setText(endereco.getLogradouro());
        editTextNumero.setText(endereco.getNumero());
        editTextComplemento.setText(endereco.getComplemento());
        editTextBairro.setText(endereco.getBairro());
        editTextCidade.setText(endereco.getLocalidade());
        editTextEstado.setText(endereco.getUf().toUpperCase());
    }

    public String getCep() {
        return  MaskUtil.unmask(editTextCep.getText().toString());
    }

    public void setEnderecoByCep(EnderecoNet enderecoNet) {

        Endereco endereco = new Endereco();

        endereco.setCep(enderecoNet.getCep().replaceAll("-", ""));
        endereco.setLogradouro(enderecoNet.getLogradouro());
        endereco.setComplemento(enderecoNet.getComplemento());
        endereco.setBairro(enderecoNet.getBairro());
        endereco.setLocalidade(enderecoNet.getLocalidade());
        endereco.setUf(enderecoNet.getUf());

        setEndereco(endereco);
    }

    public boolean validarFormulario() {

        boolean valid;

        if (validaCampo(editTextCodigo, 1, 9)
                && validaCampo(editTextNome, 3, 30)
                && validaCampo(editTextCpf, 11, 11)
                && validaCampo(editTextEmail, 10, 30)
                && validaCampo(editTextTelefone, 11, 11)
                && validaCampo(editTextCep, 8, 8)
                && validaCampo(editTextRua, 2, 30)
                && validaCampo(editTextBairro, 5, 30)
                && validaCampo(editTextCidade, 3, 20)
                && validaCampo(editTextEstado, 2, 2)){
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    private boolean validaCampo(EditText editText, int min, int max){
        if ((editText.getText().toString().equals(""))
                || (editText.getText().toString().length() == 0)){

            editText.setError(activity.getString(R.string.validate_campo_vazio));
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        } else {
            return validaTamanhoCampo(editText, min, max);
        }
    }

    private boolean validaTamanhoCampo(EditText editText, int min, int max){
        if ((editText.getText().toString().replaceAll("[^0-9A-Za-z]*", "").length() < min)
                || (editText.getText().toString().replaceAll("[^0-9a-zA-Z]*", "").length() > max)){

            editText.setError(activity.getString(R.string.validate_campo_tamanho));
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}
