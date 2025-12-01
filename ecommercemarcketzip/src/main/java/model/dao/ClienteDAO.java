package model.dao;

import model.db.DB;
import model.entity.Cliente;

import java.sql.*;

public class ClienteDAO {

    public static int inserirCliente(Cliente cliente) {

        String sqlInserirCliente = """
                    INSERT INTO cliente (id_usu, endereco_cli)
                    VALUES (?, ?)
                    RETURNING id_cli
                """;

        Connection conn = null;

        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            int idUsuarioGerado = UsuarioDAO.inserirUsuario(cliente, conn);

            if (idUsuarioGerado == -1)
                return -1;
            if (idUsuarioGerado == -2)
                return -2;

            cliente.setIdUsuario(idUsuarioGerado);

            int idClienteGerado = -1;
            try (PreparedStatement ps = conn.prepareStatement(sqlInserirCliente)) {
                ps.setInt(1, idUsuarioGerado);
                ps.setString(2, cliente.getEndereco());

                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new RuntimeException("erro ao gerar id do cliente");
                }
                idClienteGerado = rs.getInt(1);
                cliente.setIdCliente(idClienteGerado);
            }

            conn.commit();
            return idClienteGerado;

        } catch (SQLException e) {

            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ignored) {
            }

            throw new RuntimeException("erro ao inserir cliente: " + e.getMessage(), e);

        } finally {
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    public static Cliente buscarPorCpf(String cpf) {

        String sql = """
                    SELECT u.id_usu, u.nome_usu, u.cpf_usu, u.email_usu,
                           u.telefone_usu, u.senha_usu, c.endereco_cli
                    FROM cliente c
                    JOIN usuario u ON u.id_usu = c.id_usu
                    WHERE u.cpf_usu = ?
                """;

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id_usu"),
                        rs.getString("nome_usu"),
                        rs.getString("cpf_usu"),
                        rs.getString("email_usu"),
                        rs.getString("telefone_usu"),
                        rs.getString("senha_usu"),
                        rs.getString("endereco_cli"));
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("eerro ao buscar cliente: " + e.getMessage(), e);
        }
    }

    public static boolean atualizarCliente(Cliente cliente) {

        String sqlAtualizarCli = """
                    UPDATE cliente
                    SET endereco_cli = ?
                    WHERE id_usu = ?
                """;

        String sqlAtualizarUsu = """
                    UPDATE usuario
                    SET nome_usu = ?, email_usu = ?, telefone_usu = ?, senha_usu = ?
                    WHERE id_usu = ?
                """;

        Connection conn = null;

        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlAtualizarUsu)) {
                ps.setString(1, cliente.getNome());
                ps.setString(2, cliente.getEmail());
                ps.setString(3, cliente.getTelefone());
                ps.setString(4, cliente.getSenha());
                ps.setInt(5, cliente.getIdUsuario());

                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlAtualizarCli)) {
                ps.setString(1, cliente.getEndereco());
                ps.setInt(2, cliente.getIdUsuario());

                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {

            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ignored) {
            }

            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);

        } finally {
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    public static void excluirCliente(Cliente cliente) {

        String sql = """
                    DELETE FROM usuario u
                    USING cliente c
                    WHERE u.id_usu = c.id_usu
                    AND u.cpf_usu = ?;
                """;

        try (var conn = DB.getConnection()) {
            assert conn != null;

            try (var pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, cliente.getCpf());
                pstmt.executeUpdate();

            }

        } catch (SQLException e) {
            System.out.println("erro ao excluir cliente: " + e.getMessage());
        }
    }

}
