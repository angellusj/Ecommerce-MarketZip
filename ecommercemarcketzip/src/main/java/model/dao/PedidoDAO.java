package model.dao;

import model.db.DB;
import model.entity.Cliente;
import model.entity.ItemDePedido;
import model.entity.Pedido;
import model.entity.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class PedidoDAO {

    public static void criarPedido(Pedido pedido) {

        var sql = """
                    INSERT INTO pedido (data_pedido, finalizar_pedido, id_cli, valor_total_pedido)
                    VALUES (?, ?, ?, ?)
                """;

        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setDate(1, new java.sql.Date(pedido.getData().getTime()));
                pstmt.setBoolean(2, pedido.getFinalizar());
                pstmt.setInt(3, pedido.getCliente().getIdCliente());
                pstmt.setDouble(4, pedido.getValorTotal());

                int insertedRow = pstmt.executeUpdate();
                if (insertedRow > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            pedido.setIdPedido(generatedKeys.getInt(1));
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void finalizarPedido() {
        String calcularTotaisSql = """
                    UPDATE pedido p
                    SET valor_total_pedido = COALESCE(t.total, 0),
                        finalizar_pedido = TRUE
                    FROM (
                        SELECT i.id_pedido, SUM(i.quantidade_item * pr.preco_prod) AS total
                        FROM item_pedido i
                        JOIN produto pr ON pr.id_prod = i.id_prod
                        GROUP BY i.id_pedido
                    ) t
                    WHERE p.id_pedido = t.id_pedido
                    AND p.finalizar_pedido = FALSE
                """;

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(calcularTotaisSql)) {
            int linhas = ps.executeUpdate();
            System.out.println("Pedidos finalizados: " + linhas);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao finalizar pedidos: " + e.getMessage(), e);
        }
    }

    public static String visualizarPedido(int idPedido) {
        String sql = """
                    SELECT p.id_pedido, p.data_pedido, p.finalizar_pedido, p.id_cli, p.valor_total_pedido,
                           i.id_item, i.quantidade_item,
                           pr.id_prod, pr.nome_prod, pr.preco_prod
                    FROM pedido p
                    LEFT JOIN item_pedido i ON i.id_pedido = p.id_pedido
                    LEFT JOIN produto pr ON pr.id_prod = i.id_prod
                    WHERE p.id_pedido = ?
                    ORDER BY p.id_pedido, i.id_item
                """;
        StringBuilder sb = new StringBuilder();
        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                int currentPedido = -1;
                while (rs.next()) {
                    int idPed = rs.getInt("id_pedido");
                    if (idPed != currentPedido) {
                        currentPedido = idPed;
                        sb.append("Pedido ").append(idPed)
                                .append(" | Data: ").append(rs.getDate("data_pedido"))
                                .append(" | Finalizado: ").append(rs.getBoolean("finalizar_pedido"))
                                .append(" | Cliente: ").append(rs.getInt("id_cli"))
                                .append(" | Total: ").append(rs.getDouble("valor_total_pedido")).append("\n");
                    }
                    Integer idItem = (Integer) rs.getObject("id_item");
                    if (idItem != null) {
                        sb.append("  Item ").append(idItem)
                                .append(": Produto ").append(rs.getInt("id_prod"))
                                .append(" (\"").append(rs.getString("nome_prod")).append("\")")
                                .append(", Preço: ").append(rs.getDouble("preco_prod"))
                                .append(", Quantidade: ").append(rs.getInt("quantidade_item"))
                                .append("\n");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao detalhar pedidos: " + e.getMessage(), e);
        }
        return sb.toString();
    }

    public static void adicionarItemAoPedido(Pedido pedido, Produto produto) {
        String sqlExiste = "SELECT id_item, quantidade_item FROM item_pedido WHERE id_pedido = ? AND id_prod = ?";
        String sqlInsert = "INSERT INTO item_pedido (id_pedido, id_prod, quantidade_item) VALUES (?, ?, 1)";
        String sqlUpdate = "UPDATE item_pedido SET quantidade_item = quantidade_item + 1 WHERE id_item = ?";
        try (Connection conn = DB.getConnection()) {
            try (PreparedStatement psExiste = conn.prepareStatement(sqlExiste)) {
                psExiste.setInt(1, pedido.getIdPedido());
                psExiste.setInt(2, produto.getIdProduto());
                try (ResultSet rs = psExiste.executeQuery()) {
                    if (rs.next()) {
                        int idItem = rs.getInt("id_item");
                        try (PreparedStatement psUpd = conn.prepareStatement(sqlUpdate)) {
                            psUpd.setInt(1, idItem);
                            psUpd.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement psIns = conn.prepareStatement(sqlInsert)) {
                            psIns.setInt(1, pedido.getIdPedido());
                            psIns.setInt(2, produto.getIdProduto());
                            psIns.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar item ao pedido: " + e.getMessage(), e);
        }
    }

    public static void removerItemDoPedido(Pedido pedido, Produto produto) {
        String sqlExiste = "SELECT id_item, quantidade_item FROM item_pedido WHERE id_pedido = ? AND id_prod = ?";
        String sqlDelete = "DELETE FROM item_pedido WHERE id_item = ?";
        String sqlUpdate = "UPDATE item_pedido SET quantidade_item = quantidade_item - 1 WHERE id_item = ?";
        try (Connection conn = DB.getConnection();
                PreparedStatement psExiste = conn.prepareStatement(sqlExiste)) {
            psExiste.setInt(1, pedido.getIdPedido());
            psExiste.setInt(2, produto.getIdProduto());
            try (ResultSet rs = psExiste.executeQuery()) {
                if (rs.next()) {
                    int idItem = rs.getInt("id_item");
                    int qtd = rs.getInt("quantidade_item");
                    if (qtd <= 1) {
                        try (PreparedStatement psDel = conn.prepareStatement(sqlDelete)) {
                            psDel.setInt(1, idItem);
                            psDel.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement psUpd = conn.prepareStatement(sqlUpdate)) {
                            psUpd.setInt(1, idItem);
                            psUpd.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover item do pedido: " + e.getMessage(), e);
        }
    }

    public static void excluirPedido(Pedido pedido) {
        String sqlItens = "DELETE FROM item_pedido WHERE id_pedido = ?";
        String sqlPedido = "DELETE FROM pedido WHERE id_pedido = ?";
        Connection conn = null;
        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psItens = conn.prepareStatement(sqlItens)) {
                psItens.setInt(1, pedido.getIdPedido());
                psItens.executeUpdate();
            }
            try (PreparedStatement psPed = conn.prepareStatement(sqlPedido)) {
                psPed.setInt(1, pedido.getIdPedido());
                psPed.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Erro ao fazer rollback: " + ex.getMessage(), ex);
                }
            }
            throw new RuntimeException("Erro ao excluir pedido: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
                }
            }
        }
    }

    public static void atualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedido SET data_pedido = ?, finalizar_pedido = ?, valor_total_pedido = ? WHERE id_pedido = ?";
        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            java.sql.Date dataSQL = new java.sql.Date(pedido.getData().getTime());
            ps.setDate(1, dataSQL);
            ps.setBoolean(2, pedido.getFinalizar());
            ps.setDouble(3, pedido.getValorTotal());
            ps.setInt(4, pedido.getIdPedido());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pedido: " + e.getMessage(), e);
        }
    }

    public static List<Pedido> listarPedidosFeitos() {
        String sql = "SELECT id_pedido, data_pedido, finalizar_pedido, id_cli, valor_total_pedido FROM pedido WHERE finalizar_pedido = TRUE ORDER BY id_pedido";
        return listarPedidosPorConsulta(sql);
    }

    public static List<Pedido> listarPedidosAtivosDoCliente(Cliente cliente) {
        String sql = "SELECT id_pedido, data_pedido, finalizar_pedido, id_cli, valor_total_pedido FROM pedido WHERE finalizar_pedido = FALSE AND id_cli = ? ORDER BY id_pedido";
        return listarPedidosPorConsulta(sql, cliente.getIdCliente());
    }

    public static List<Pedido> listarTodosPedidosAtivos() {
        String sql = "SELECT id_pedido, data_pedido, finalizar_pedido, id_cli, valor_total_pedido FROM pedido WHERE finalizar_pedido = FALSE ORDER BY id_pedido";
        return listarPedidosPorConsulta(sql);
    }

    public static List<Pedido> listarPedidosConcluidosDoCliente(Cliente cliente) {
        String sql = "SELECT id_pedido, data_pedido, finalizar_pedido, id_cli, valor_total_pedido FROM pedido WHERE finalizar_pedido = TRUE AND id_cli = ? ORDER BY id_pedido";
        return listarPedidosPorConsulta(sql, cliente.getIdCliente());
    }

    public static double calcularValorTotal() {
        String sql = "SELECT COALESCE(SUM(valor_total_pedido), 0) AS total FROM pedido";
        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular valor total: " + e.getMessage(), e);
        }
    }

    public static List<Pedido> listarPedidosPorConsulta(String sql, Object... params) {
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_pedido");
                    Date data = rs.getDate("data_pedido");
                    boolean fin = rs.getBoolean("finalizar_pedido");
                    double total = rs.getDouble("valor_total_pedido");
                    Pedido p = new Pedido(id, data, fin, total, null);

                    int idCli = rs.getInt("id_cli");
                    Cliente cliente = ClienteDAO.buscarPorIdCliente(idCli);
                    p.setCliente(cliente);

                    List<ItemDePedido> itens = ItemDePedidoDAO.listarItens(p);
                    p.setItens(itens);

                    // Só adiciona o pedido se tiver pelo menos um item
                    if (!itens.isEmpty()) {
                        pedidos.add(p);
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pedidos: " + e.getMessage(), e);
        }
        return pedidos;
    }

    public static Pedido buscarPorId(int idPedido) {
        String sql = "SELECT id_pedido, data_pedido, finalizar_pedido, valor_total_pedido, id_cli " +
                "FROM pedido WHERE id_pedido = ?";

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int idCli = rs.getInt("id_cli");
                Cliente cliente = ClienteDAO.buscarPorIdCliente(idCli);

                return new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getDate("data_pedido"),
                        rs.getBoolean("finalizar_pedido"),
                        rs.getDouble("valor_total_pedido"),
                        cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido por id: " + e.getMessage(), e);
        }

        return null;
    }

    /*
     * public static Pedido buscarPorId(int idPedido) {
     * 
     * String sql =
     * "SELECT id_pedido, data_pedido, finalizar_pedido, id_cli, valor_total_pedido FROM pedido WHERE id_pedido = ?"
     * ;
     * try (var conn = DB.getConnection()) {
     * assert conn != null;
     * try (var pstmt = conn.prepareStatement(sql)) {
     * pstmt.setInt(1, idPedido);
     * ResultSet rs = pstmt.executeQuery();
     * if (rs.next()) {
     * int id = rs.getInt("id_pedido");
     * Date data = rs.getDate("data_pedido");
     * boolean fin = rs.getBoolean("finalizar_pedido");
     * double total = rs.getDouble("valor_total_pedido");
     * int idCliente = rs.getInt("id_cli");
     * Cliente cliente = ClienteDAO.buscarPorId(idCliente);
     * 
     * Pedido pedido = new Pedido(id, data, fin, total, cliente);
     * 
     * // Carregar itens do pedido
     * java.util.List<ItemDePedido> itens = ItemDePedidoDAO.listarItens(pedido);
     * pedido.setItens(itens);
     * 
     * // Validar se o pedido possui pelo menos um produto
     * if (itens == null || itens.isEmpty()) {
     * return null;
     * }
     * 
     * return pedido;
     * }
     * }
     * } catch (SQLException e) {
     * System.out.println(e.getMessage());
     * }
     * return null;
     * }
     */

    public static Pedido buscarPorIdSemValidacao(int idPedido) {
        String sql = "SELECT id_pedido, data_pedido, finalizar_pedido, id_cli, valor_total_pedido FROM pedido WHERE id_pedido = ?";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idPedido);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id_pedido");
                    Date data = rs.getDate("data_pedido");
                    boolean fin = rs.getBoolean("finalizar_pedido");
                    double total = rs.getDouble("valor_total_pedido");
                    int idCliente = rs.getInt("id_cli");
                    Cliente cliente = ClienteDAO.buscarPorId(idCliente);

                    Pedido pedido = new Pedido(id, data, fin, total, cliente);

                    // Carregar itens do pedido
                    java.util.List<ItemDePedido> itens = ItemDePedidoDAO.listarItens(pedido);
                    pedido.setItens(itens);

                    return pedido;
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido por id: " + e.getMessage(), e);
        }

        return null;
    }

}
