package br.com.avana.tabajara.interfaces;

import br.com.avana.tabajara.model.Pessoa;

public interface RepositorioArray {

    /*
     * Retorna a pessoa identificada pelo número passado como parâmetro, retornando
     * null se não encontrar.
     * */
    Pessoa procurar(String numero);

    /*
     * Cadastra uma pessoa, retornando falso se o vetor já estiver cheio.
     * */
    boolean inserir(Pessoa pessoa);

    /*
     * Atualiza a pessoa passada como parâmetro, retornando falso se não encontrar.
     * */
    boolean atualizar(Pessoa pessoa);

    /*
     * Remove a pessoa identificada pelo número passado como parâmetro, retornando
     * falso se não encontrar.
     * */
    boolean remover(Pessoa pessoa);

    /*
     * Retorna verdadeiro se localizou no repositório a pessoa identificada pelo número
     * passado como parâmetro ou falso se não encontrar.
     * */
    boolean existe(String numero);
}
