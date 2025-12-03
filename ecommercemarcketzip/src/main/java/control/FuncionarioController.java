package control;

import model.dao.FuncionarioDAO;
import model.entity.Funcionario;
import model.dao.UsuarioDAO;

import java.util.List;

public class FuncionarioController {

    public static boolean cadastrarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Erro ao inserir: objeto nulo");
            return false;
        } else if (UsuarioDAO.buscarIdPorCpf(funcionario.getCpf()) != null) {
            System.out.println("CPF ja existe");
            return false;
        } else if (UsuarioDAO.buscarIdPorEmail(funcionario.getEmail()) != null) {
            System.out.println("Email ja existe");
            return false;
        } else if (funcionario.getCpf().isBlank()) {
            System.out.println("Erro ao inserir: cpf vazio");
            return false;
        } else if (funcionario.getEmail().isBlank() || !(funcionario.getEmail().contains("@"))) {
            System.out.println("Erro ao inserir: email vazio ou invalido");
            return false;
        } else if (funcionario.getCargo().isBlank()) {
            System.out.println("Erro ao inserir: cargo vazio");
            return false;
        }
        System.out.println("Cadastrado!");
        return FuncionarioDAO.inserirFuncionario(funcionario) > 0;
    }

    public static Funcionario buscarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            System.out.println("CPF vazio");
            return null;
        }
        return FuncionarioDAO.buscarPorCpf(cpf);
    }

    public Funcionario buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome vazio");
            return null;
        }
        return FuncionarioDAO.buscarPorNome(nome);
    }

    public static boolean atualizarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Objeto nulo");
            return false;
        }
        System.out.println("Atualizado!");
        return FuncionarioDAO.atualizarFuncionario(funcionario);
    }

    public static boolean deletarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Objeto nulo");
            return false;
        }
        FuncionarioDAO.excluirFuncionario(funcionario);
        System.out.println("excluido!");
        return true;
    }

    public static List<Funcionario> listarFuncionarios() {
        return FuncionarioDAO.listarFuncionarios();
    }

    public static Funcionario realizarLogin(String login, String senha) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser nulo ou vazio.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        }

        Funcionario funcionario = FuncionarioDAO.buscarPorCpf(login);

        if (funcionario == null) {
            return null;
        }

        if (funcionario.getSenha().equals(senha)) {
            return funcionario;
        }

        return null;
    }

}
