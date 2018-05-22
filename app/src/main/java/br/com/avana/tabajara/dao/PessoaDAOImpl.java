package br.com.avana.tabajara.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.avana.tabajara.interfaces.PessoaDAO;
import br.com.avana.tabajara.model.Endereco;
import br.com.avana.tabajara.model.Pessoa;

public class PessoaDAOImpl extends SQLiteOpenHelper implements PessoaDAO{

    public PessoaDAOImpl(Context context) {
        super(context, "CLIENTES", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String pessoa =
                "CREATE TABLE PESSOAS (" +
                        "ID_PESSOA INTEGER PRIMARY KEY," +
                        "NOME TEXT NOT NULL," +
                        "CPF TEXT NOT NULL," +
                        "TELEFONE TEXT NOT NULL," +
                        "EMAIL TEXT NOT NULL" +
                        ");";

        String endereco =
                "CREATE TABLE ENDERECO(" +
                        "ID_ENDERECO INTEGER PRIMARY KEY," +
                        "CEP TEXT," +
                        "LOGRADOURO TEXT NOT NULL," +
                        "NUMERO TEXT," +
                        "COMPLEMENTO TEXT," +
                        "BAIRRO TEXT NOT NULL," +
                        "LOCALIDADE TEXT NOT NULL," +
                        "UF TEXT NOT NULL," +
                        "ID_PESSOA INT NOT NULL," +
                        "FOREIGN KEY(ID_PESSOA) REFERENCES PESSOA(ID)"+
                        ")";

        db.execSQL(pessoa);
        db.execSQL(endereco);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public List<Pessoa> getAll(){

        List<Pessoa> pessoas =  new ArrayList<Pessoa>();

        String sql = "SELECT * FROM PESSOAS P INNER JOIN ENDERECO E ON P.ID_PESSOA = E.ID_PESSOA;";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){

            Pessoa pessoa = new Pessoa();

            pessoa.setId(c.getLong(c.getColumnIndex("ID_PESSOA")));
            pessoa.setNome(c.getString(c.getColumnIndex("NOME")));
            pessoa.setCpf(c.getString(c.getColumnIndex("CPF")));
            pessoa.setEmail(c.getString(c.getColumnIndex("EMAIL")));
            pessoa.setTelefone(c.getString(c.getColumnIndex("TELEFONE")));

            Endereco endereco = new Endereco();

            endereco.setId(c.getLong(c.getColumnIndex("ID_ENDERECO")));
            endereco.setCep(c.getString(c.getColumnIndex("CEP")));
            endereco.setLogradouro(c.getString(c.getColumnIndex("LOGRADOURO")));
            endereco.setNumero(c.getString(c.getColumnIndex("NUMERO")));
            endereco.setComplemento(c.getString(c.getColumnIndex("COMPLEMENTO")));
            endereco.setBairro(c.getString(c.getColumnIndex("BAIRRO")));
            endereco.setLocalidade(c.getString(c.getColumnIndex("LOCALIDADE")));
            endereco.setUf(c.getString(c.getColumnIndex("UF")));

            pessoa.setEndereco(endereco);
            pessoas.add(pessoa);
        }
        c.close();

        return pessoas;
    }

    @Override
    public void insert(Pessoa pessoa){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues content = getContentValuesPessoa(pessoa);
        pessoa.setId(db.insert("PESSOAS", null, content));

        content = getContentValuesEndereco(pessoa);
        pessoa.getEndereco().setId(db.insert("ENDERECO",null, content));
    }

    @Override
    public void update(Pessoa pessoa){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = getContentValuesPessoa(pessoa);
        String[] params = {Double.toString(pessoa.getId())};
        db.update("PESSOAS", contentValues, "ID_PESSOA = ?", params);

        contentValues = getContentValuesEndereco(pessoa);
        db.update("ENDERECO", contentValues, "ID_PESSOA = ?", params);

    }

    @Override
    public void delete(Pessoa pessoa){

        SQLiteDatabase db = getWritableDatabase();
        String[] params = {Double.toString(pessoa.getId())};
        db.delete("ENDERECO", "ID_PESSOA = ?", params);
        db.delete("PESSOAS", "ID_PESSOA = ?", params);
    }

    @NonNull
    private ContentValues getContentValuesPessoa(Pessoa pessoa) {

        ContentValues content = new ContentValues();

        content.put("NOME", pessoa.getNome());
        content.put("CPF", pessoa.getCpf());
        content.put("TELEFONE", pessoa.getTelefone());
        content.put("EMAIL", pessoa.getEmail());

        return content;
    }

    @NonNull
    private ContentValues getContentValuesEndereco(Pessoa pessoa) {

        ContentValues content = new ContentValues();

        content.put("CEP", pessoa.getEndereco().getCep());
        content.put("LOGRADOURO", pessoa.getEndereco().getLogradouro());
        content.put("NUMERO", pessoa.getEndereco().getNumero());
        content.put("COMPLEMENTO", pessoa.getEndereco().getComplemento());
        content.put("BAIRRO", pessoa.getEndereco().getBairro());
        content.put("LOCALIDADE", pessoa.getEndereco().getLocalidade());
        content.put("UF", pessoa.getEndereco().getUf());
        content.put("ID_PESSOA", pessoa.getId());

        return content;
    }
}
