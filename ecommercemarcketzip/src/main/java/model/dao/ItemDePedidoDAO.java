package model.dao;

import model.db.DB;
import model.entity.ItemDePedido;
import model.entity.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDePedidoDAO {

    public static void mostrarItem() {
        String sql = "SELECT * FROM item_pedido";
        
        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            System.out.println("=== ITENS DE PEDIDO ===\n");
            
            boolean encontrouItens = false;
            while (rs.next()) {
                encontrouItens = true;
                int idItem = rs.getInt("id_item");
                int idProduto = rs.getInt("id_prod");
                int idPedido = rs.getInt("id_pedido");
                int quantidade = rs.getInt("quantidade_item");
                
                ItemDePedido item = new ItemDePedido(idItem, idProduto, idPedido, quantidade);
                System.out.println(item);
                System.out.println("-------------------");
            }
            
            if (!encontrouItens) {
                System.out.println("Nenhum item de pedido encontrado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao mostrar itens de pedido: " + e.getMessage(), e);
        }
    }

    public static void atualizarItem(ItemDePedido item, int quantidade) {
        String sql = """
                    UPDATE item_pedido
                    SET quantidade_item = ?
                    WHERE id_item = ?
                """;

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, item.getIdItem());

            int linhasAfetadas = ps.executeUpdate();
            
            if (linhasAfetadas > 0) {
                item.setQuantidade(quantidade);
                System.out.println("Item atualizado com sucesso!");
            } else {
                System.out.println("Item não encontrado para atualização.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item: " + e.getMessage(), e);
        }
    }

    public static void excluirItem(ItemDePedido item) {
        String sql = "DELETE FROM item_pedido WHERE id_item = ?";

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getIdItem());

            int linhasAfetadas = ps.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Item excluído com sucesso!");
            } else {
                System.out.println("Item não encontrado para exclusão.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir item: " + e.getMessage(), e);
        }
    }

    public static List<ItemDePedido> listarItens(Pedido pedido) {
        List<ItemDePedido> itens = new ArrayList<>();
        String sql = "SELECT * FROM item_pedido WHERE id_pedido = ?";

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pedido.getIdPedido());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idItem = rs.getInt("id_item");
                int idProduto = rs.getInt("id_prod");
                int idPedido = rs.getInt("id_pedido");
                int quantidade = rs.getInt("quantidade_item");

                ItemDePedido item = new ItemDePedido(idItem, idProduto, idPedido, quantidade);
                itens.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens do pedido: " + e.getMessage(), e);
        }

        return itens;
    }

}
