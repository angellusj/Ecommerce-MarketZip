package model.dao;

import model.db.DB;
import model.entity.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    public static void criarProduto(Produto produto){
        String nome = produto.getNome();
        String desc = produto.getDesc();
        double preco = produto.getPreco();
        String categoria = produto.getCategoria();

        var sql = "INSERT INTO produto(id_prod, nome_prod, descricao_prod, preco_prod, categoria_prod) VALUES (?,?,?,?,?);";
        try (var conn = DB.getConnection()){
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstmt.setString(1, nome);
                pstmt.setString(2, desc);
                pstmt.setDouble(3, preco);
                pstmt.setString(4, categoria);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    produto.setIdProduto(rs.getInt(1));
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void atualizarProduto(Produto produto){
        String nome = produto.getNome();
        String desc = produto.getDesc();
        double preco = produto.getPreco();
        String categoria = produto.getCategoria();

        var sql = "UPDATE produto SET nome_prod = ?, descricao_prod = ?, preco_prod = ?, categoria_prod = ? WHERE id_prod = ?";
        try (var conn = DB.getConnection()){
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, nome);
                pstmt.setString(2, desc);
                pstmt.setDouble(3, preco);
                pstmt.setString(4, categoria);
                pstmt.setInt(5, produto.getIdProduto());
                pstmt.executeUpdate();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void excluirProduto(Produto produto){
        var sql = "DELETE FROM produto WHERE id_prod = ?;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)){

                pstmt.setInt(1, produto.getIdProduto());
                pstmt.executeUpdate();

            }
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
    }

   public static Produto mostrarProduto (String nome_prod){
        var sql = "SELECT id_prod, nome_prod, descricao_prod, preco_prod, categoria_prod FROM produto WHERE id_prod = ?;";
        try(var conn = DB.getConnection()) {
            assert conn != null;
            try(var pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, nome_prod);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    int idp = rs.getInt("id_prod");
                    String nomep = rs.getString("nome_prod");
                    String desc = rs.getString("descricao_prod");
                    double preco = rs.getDouble("preco_prod");
                    String categoria = rs.getString("categoria_prod");

                    return new Produto(idp, nomep, desc, preco, categoria);
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static List<Produto> listarProdutos(){
        List<Produto> produtos = new ArrayList<Produto>();

        var sql = "SELECT id_prod, nome_prod, descricao_prod, preco_prod, categoria_prod FROM produto;";

        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)){
                var rs = pstmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id_prod");
                    String nomeProduto = rs.getString("nome_prod");
                    String desc = rs.getString("descricao_prod");
                    double preco = rs.getDouble("preco_prod");
                    String categoria = rs.getString("categoria_prod");

                    produtos.add(new Produto(id, nomeProduto, desc, preco, categoria));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return produtos;
    }
}
