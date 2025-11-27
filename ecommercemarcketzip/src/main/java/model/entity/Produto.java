package model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Produto {
    private int idProduto;
    private String nome;
    private String desc;
    private double preco;
    private String categoria;

    public Produto (int idProduto, String nome, String desc, double preco, String categoria){
        this.idProduto = idProduto;
        this.nome = nome;
        this.desc = desc;
        this.preco = preco;
        this.categoria = categoria;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto\tidProduto: " + idProduto + "\nNome = " + nome + "\nDescrição = " + desc + "\nPreco = " + preco
                + "\nCategoria =" + categoria;
    }

       public static Produto criarProduto(int idProduto, String nome, String desc, double preco, String categoria) {
        return new Produto(idProduto, nome, desc, preco, categoria);
    }
    
    public void atualizarProduto(String nome, String desc, double preco, String categoria) {
        this.nome = nome;
        this.desc = desc;
        this.preco = preco;
        this.categoria = categoria;
    }

        public static List<Produto> listarProdutos(List<Produto> produtos) {
        return new ArrayList<>(produtos);
    }

    public static Produto buscarProduto(List<Produto> produtos, int idProduto) {
        for (Produto produto : produtos) {
            if (produto.getIdProduto() == idProduto) {
                return produto;
            }
        }
        return null;
    }
    
    public static boolean excluirProduto(List<Produto> produtos, int idProduto) {
        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getIdProduto() == idProduto) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
