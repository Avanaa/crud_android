package br.com.avana.tabajara.impl;

import br.com.avana.tabajara.interfaces.RepositorioArray;
import br.com.avana.tabajara.model.Pessoa;

public class RepositorioArrayImpl implements RepositorioArray {

    private static final int NOT_FOUND = -1;
    private static final int MAX_LENGTH_ARRAY = 50;

    private Pessoa[] pessoas;
    private int actualSize;

    public  RepositorioArrayImpl(){
        this.pessoas = new Pessoa[MAX_LENGTH_ARRAY];
        this.actualSize = 0;
    }

    /*
    * Retorna a posição no
    * vetor onde se encontra a pessoa correspondente ao número
    * passado como parâmetro, ou -1 se não existir no repositório.
    * */
    private int procurarIndice(String numero){
        for(int i = 0; i < pessoas.length; i++){
            if (pessoas[i].getNumero() == numero){
                return i;
            }
        }
        return NOT_FOUND;
    }

    @Override
    public Pessoa procurar(String numero) {
        int index = this.procurarIndice(numero);
        if(index != NOT_FOUND) {
            return this.pessoas[index];
        } else {
         return null;
        }
    }

    @Override
    public boolean inserir(Pessoa pessoa) {
        if(this.actualSize < MAX_LENGTH_ARRAY){
            this.pessoas[this.actualSize] = pessoa;
            this.actualSize++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean atualizar(Pessoa pessoa) {
        int index = this.procurarIndice(pessoa.getNumero());
        if (index != NOT_FOUND){
            this.pessoas[index] = pessoa;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remover(Pessoa pessoa) {
        int index = this.procurarIndice(pessoa.getNumero());
        if (index != NOT_FOUND){
            for (int i = index; i < this.getActualSize(); i++){
                this.pessoas[index] = this.pessoas[i+1];
            }
            this.actualSize --;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean existe(String numero) {
        if(this.procurarIndice(numero) != NOT_FOUND){
            return true;
        } else {
            return false;
        }
    }

    public int getActualSize() {
        return actualSize;
    }

    public Pessoa[] getPessoas() {
        Pessoa[] pessoas = new Pessoa[this.getActualSize()];
        for (int i = 0; i < this.getActualSize(); i++){
            pessoas[i] = this.pessoas[i];
        }
        return pessoas;
    }
}
