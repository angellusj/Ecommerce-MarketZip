package model.dao;

import model.db.DB;
import model.entity.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public static int inserirFuncionario(Funcionario funcionario) {

        String sqlInserirFuncionario = """
                    INSERT INTO funcionario (id_usu, cargo_func)
                    VALUES (?, ?)
                    RETURNING id_func
                """;

        Connection conn = null;

        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            int idUsuarioGerado = UsuarioDAO.inserirUsuario(funcionario, conn);

            if (idUsuarioGerado == -1) {
                return -1;
            }

            if (idUsuarioGerado == -2) {
                return -2;
            }

            funcionario.setIdUsuario(idUsuarioGerado);

            int idFuncGerado;

            try (PreparedStatement ps = conn.prepareStatement(sqlInserirFuncionario)) {
                ps.setInt(1, idUsuarioGerado);
                ps.setString(2, funcionario.getCargo());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idFuncGerado = rs.getInt("id_func");
                } else {
                    throw new RuntimeException("erro ao gerar id_func.");
                }
            }

            conn.commit();
            return idFuncGerado;

        } catch (SQLException e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ignored) {
            }
            throw new RuntimeException("Erro ao inserir funcionario: " + e.getMessage(), e);

        } finally {
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    public static Funcionario buscarPorCpf(String cpf) {

        String sql = """
                    SELECT f.id_func, u.id_usu, u.nome_usu, u.cpf_usu, u.email_usu,
                           u.telefone_usu, u.senha_usu, f.cargo_func
                    FROM funcionario f
                    JOIN usuario u ON u.id_usu = f.id_usu
                    WHERE u.cpf_usu = ?
                """;

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Funcionario(
                        rs.getInt("id_usu"),
                        rs.getString("nome_usu"),
                        rs.getString("cpf_usu"),
                        rs.getString("email_usu"),
                        rs.getString("telefone_usu"),
                        rs.getString("senha_usu"),
                        rs.getString("cargo_func"));
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionario: " + e.getMessage());
        }
    }

    public static List<Funcionario> listarFuncionarios() {

        String sql = """
                    SELECT f.id_func, u.id_usu, u.nome_usu, u.cpf_usu, u.email_usu,
                           u.telefone_usu, u.senha_usu, f.cargo_func
                    FROM funcionario f
                    JOIN usuario u ON u.id_usu = f.id_usu
                    ORDER BY f.id_func
                """;

        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Funcionario func = new Funcionario(
                        rs.getInt("id_usu"),
                        rs.getString("nome_usu"),
                        rs.getString("cpf_usu"),
                        rs.getString("email_usu"),
                        rs.getString("telefone_usu"),
                        rs.getString("senha_usu"),
                        rs.getString("cargo_func"));

                funcionarios.add(func);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage(), e);
        }

        return funcionarios;
    }

    public static boolean atualizarFuncionario(Funcionario funcionario) {

        String sqlAtualizarFuncionario = """
                    UPDATE funcionario
                    SET cargo_func = ?
                    WHERE id_usu = ?
                """;

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            boolean usuarioOk = UsuarioDAO.atualizarUsuario(funcionario);
            if (!usuarioOk) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlAtualizarFuncionario)) {
                ps.setString(1, funcionario.getCargo());
                ps.setInt(2, funcionario.getIdUsuario());

                if (ps.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionario: " + e.getMessage());

        }
    }

    public static void excluirFuncionario(Funcionario funcionario) {
        var sql = "DELETE FROM usuario u USING funcionario f WHERE u.id_usu = f.id_usu AND u.cpf_usu = ?;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, funcionario.getCpf());
                pstmt.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
