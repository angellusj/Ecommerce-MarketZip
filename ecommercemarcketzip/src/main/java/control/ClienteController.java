package control;

import model.dao.ClienteDAO;
import model.dao.UsuarioDAO;
import model.entity.Cliente;

public class ClienteController {

    public boolean cadastrarCliente(Cliente cliente) {

        if (cliente == null) {
            System.out.println("erro ao inserir: objeto nulo");
            return false;
        } else if (UsuarioDAO.buscarIdPorCpf(cliente.getCpf()) != null) {
            System.out.println("CPF ja existe");
            return false;
        } else if (UsuarioDAO.buscarIdPorEmail(cliente.getEmail()) != null) {
            System.out.println("Email ja existe");
            return false;
        } else if (cliente.getCpf().isBlank()) {
            System.out.println("erro ao inserir: cpf vazio");
            return false;
        } else if (cliente.getEmail().isBlank() || !(cliente.getEmail().contains("@"))) {
            System.out.println("erro ao inserir: email vazio ou invalido");
            return false;
        } else if (cliente.getEndereco().isBlank()) {
            System.out.println("erro ao inserir: endereco vazio");
            return false;
        }
        
        System.out.println("cadastrado!");
        return ClienteDAO.inserirCliente(cliente) > 0;
    }

    public Cliente buscarPorCpf(String cpf) {
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
}
