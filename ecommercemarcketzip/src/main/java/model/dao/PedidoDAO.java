package model.dao;

import model.db.DB;
import model.entity.Cliente;

import java.sql.*;

public class PedidoDAO {
    
    public static int criarPedido(Cliente cliente, Date data) {
        
        String sql = """
                    INSERT INTO pedido (data_pedido, finalizar_pedido, id_cli, valor_total_pedido)
                    VALUES (?, ?, ?, ?)
                    RETURNING id_pedido
                """;

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, data);
            ps.setBoolean(2, false); // Pedido começa como não finalizado
            ps.setInt(3, cliente.getIdUsuario()); // id_cli é o id do usuário (cliente)
            ps.setDouble(4, 0.0); // Valor total inicial é 0

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_pedido");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar pedido: " + e.getMessage(), e);
        }
    }
    
}
