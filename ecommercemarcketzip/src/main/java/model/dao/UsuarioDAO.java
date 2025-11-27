package model.dao;

import model.db.DB;
import model.entity.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public static int inserirUsuario(Usuario usuario) {

        if (buscarIdPorCpf(usuario.getCpf()) != null) {
            return -1;
        }

        if (buscarIdPorEmail(usuario.getEmail()) != null) {
            return -2;
        }

        String sql = """
                    INSERT INTO usuario (nome_usu, cpf_usu, email_usu, telefone_usu, senha_usu)
                    VALUES (?, ?, ?, ?, ?)
                    RETURNING id_usu
                """;

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefone());
            ps.setString(5, usuario.getSenha());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usu");
            } else {
                return -3;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    public static Integer buscarIdPorCpf(String cpf) {
        String sql = "SELECT id_usu FROM usuario WHERE cpf_usu = ?";
        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return rs.getInt("id_usu");

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar CPF: " + e.getMessage(), e);
        }
    }

    public static Integer buscarIdPorEmail(String email) {
        String sql = "SELECT id_usu FROM usuario WHERE email_usu = ?";
        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return rs.getInt("id_usu");

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar email: " + e.getMessage(), e);
        }
    }

    public static boolean atualizarUsuario(Usuario usuario) {

        String sql = """
                    UPDATE usuario
                    SET nome_usu = ?, cpf_usu = ?, email_usu = ?,
                        telefone_usu = ?, senha_usu = ?
                    WHERE id_usu = ?
                """;

        try (Connection conn = DB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefone());
            ps.setString(5, usuario.getSenha());
            ps.setInt(6, usuario.getIdUsuario());

            return (ps.executeUpdate() > 0 ? true:false);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usaurio: " + e.getMessage());
        }
    }

}