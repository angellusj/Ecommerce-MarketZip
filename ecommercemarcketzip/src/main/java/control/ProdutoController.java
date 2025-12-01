package control;

import java.util.ArrayList;
import java.util.List;

import model.dao.ProdutoDAO;
import model.entity.Produto;

public class ProdutoController {
    public static Produto criarProduto(int idProduto, String nome, String descricao, double preco, String categoria) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.\n");
        }

        if (preco < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo.\n");
        }

        if (categoria == null || categoria.isEmpty()) {
            throw new IllegalArgumentException("Categoria do produto não pode ser nula ou vazia.\n");
        }

        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição do produto não pode ser nula ou vazia.\n");
        }

        Produto produto = new Produto(idProduto, nome.toUpperCase(), descricao.toUpperCase(), preco,
                categoria.toUpperCase());
        ProdutoDAO.criarProduto(produto);
        return produto;
    }

    public static Produto atualizarProduto(int idProduto, String nome, String descricao, double preco,
            String categoria) {
        if (idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido. ID deve ser maior que 0.\n");
        }

        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.\n");
        }

        if (preco < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo.\n");
        }

        if (categoria == null || categoria.isEmpty()) {
            throw new IllegalArgumentException("Categoria do produto não pode ser nula ou vazia.\n");
        }

        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição do produto não pode ser nula ou vazia.\n");
        }

        var prod = new Produto(idProduto, nome.toUpperCase(), descricao.toUpperCase(), preco, categoria.toUpperCase());
        ProdutoDAO.atualizarProduto(prod);
        return prod;
    }

    public static boolean excluirProduto(int idProduto) {
        Produto produto = ProdutoDAO.buscarPorId(idProduto);

        if (produto == null) {
            return false;
        }

        try {
            ProdutoDAO.excluirProduto(produto);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }

    public static List<Produto> listarProdutos() {
        try {
            List<Produto> produtos = ProdutoDAO.listarProdutos();
            return produtos != null ? produtos : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static Produto buscarProdutoPorId(int idProduto) {
        if (idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido. ID deve ser maior que 0.\n");
        }
        try {
            Produto produto = ProdutoDAO.buscarPorId(idProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado para o ID: " + idProduto + "\n");
            }

            return produto;
        } catch (Exception e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
            return null;
        }
    }

    public static Produto mostraProduto(String nomeProd) {
        if (nomeProd == null || nomeProd.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.\n");
        }
        try {
            Produto produto = ProdutoDAO.mostrarProduto(nomeProd.toUpperCase());
            if (produto == null) {
                System.out.println("Produto não encontrado para o nome: " + nomeProd + "\n");
            }

            return produto;
        } catch (Exception e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
            return null;
        }
    }
}
