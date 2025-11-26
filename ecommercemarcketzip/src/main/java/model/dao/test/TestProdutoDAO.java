package model.dao.test;

import java.util.List;

import model.dao.ProdutoDAO;
import model.entity.Produto;

public class TestProdutoDAO implements TestDaoComponent{
    
    @Override
    public boolean teste(){
        try{
            System.out.println("\nTestando ProdutoDAO");
            //testando criação de produto
            Produto produto = new Produto(0, "pippos", "xilito ", 5.50, "guloseima");
            ProdutoDAO.criarProduto(produto);
            System.out.println("\nProduto criado com sucesso!");

            //testando atualização de produto
            produto.setNome("pippos de churrasco");
            ProdutoDAO.atualizarProduto(produto);
            System.out.println("Produto atualizado com sucesso!");

            //testando mostragem de produto
            System.out.println("Inserindo nome do produto");
            produto = ProdutoDAO.mostrarProduto("pippos de churrasco");
            System.out.println(produto);

            //testando listagem de produtos
            List<Produto> produtos = ProdutoDAO.listarProdutos();

            //testando exclusão de produtos
            ProdutoDAO.excluirProduto(produto);
            System.out.println("Produto excluido com sucesso!");

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Falhou em ProdutoDAO");
        }
        return false;
    }
}
