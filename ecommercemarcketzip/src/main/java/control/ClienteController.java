package control;

import java.util.List;
import model.dao.ClienteDAO;
import model.dao.UsuarioDAO;
import model.entity.Cliente;

public class ClienteController {

    public static Cliente cadastrarCliente(int idUsuario, String nome, String cpf, String email, String telefone, String senha, String endereco) {

        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.\n");
        }

        if (nome == null) {
            throw new IllegalArgumentException("Nome do cliente não pode ser nulo.\n");
        }

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF do cliente não pode ser nulo ou vazio.\n");
        }

        if (email == null || email.isBlank() || !(email.contains("@"))) {
            throw new IllegalArgumentException("Email do cliente não pode ser nulo, vazio ou inválido.\n");
        }

        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone do cliente não pode ser nulo ou vazio.\n");
        }

        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha do cliente não pode ser nula ou vazia.\n");
        }

        if (endereco == null || endereco.isBlank()) {
            throw new IllegalArgumentException("Endereço do cliente não pode ser nulo ou vazio.\n");
        }

        Cliente cliente = new Cliente(idUsuario, nome.toUpperCase(), cpf, email.toUpperCase(), telefone.toUpperCase(), senha.toUpperCase(),
                endereco.toUpperCase());
        ClienteDAO.inserirCliente(cliente);
        return cliente;
    }

    public static Cliente buscarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            System.out.println("CPF vazio");
            return null;
        }

        return ClienteDAO.buscarPorCpf(cpf);
    }

    public boolean atualizarCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("objeto nulo");
            return false;
        }

        System.out.println("atualizado!");
        return ClienteDAO.atualizarCliente(cliente);
    }

    public boolean deletarCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("objeto nulo");
            return false;
        }

        ClienteDAO.excluirCliente(cliente);
        System.out.println("deletado!");
        return true;
    }

    public static List<Cliente> listarClientes() {
        return ClienteDAO.listarClientes();
    }
}
