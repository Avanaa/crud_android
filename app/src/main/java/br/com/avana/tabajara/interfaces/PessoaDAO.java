package br.com.avana.tabajara.interfaces;

import java.util.List;

import br.com.avana.tabajara.model.Pessoa;

public interface PessoaDAO {

    /*
     * Retorna a pessoa identificada pelo número passado como parâmetro, retornando
     * null se não encontrar.
     * */
    //Pessoa procurar(String numero);

    /**
     *
     * Retorna todos os registros
     */
    List<Pessoa> getAll();

    /*
     * Salva
     * */
    void insert(Pessoa pessoa);

    /*
     * Atualiza
     * */
    void update(Pessoa pessoa);

    /*
     * Remove
     * */
    void delete(Pessoa pessoa);

    /*
     * Retorna verdadeiro se localizou no repositório a pessoa identificada pelo número
     * passado como parâmetro ou falso se não encontrar.
     * */
    //boolean existe(String numero);
}
