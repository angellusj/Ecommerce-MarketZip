package model.dao.test;

import java.util.List;

import model.dao.ClienteDAO;
import model.entity.Cliente;

public class TestClienteDAO implements TesteDaoComponent {
    @Override
    public boolean teste() {
        try {
            System.out.println("\nTestando ClienteDAO");

            System.out.println("Pegando os clientes no banco e inserindo numa lista");
            List<Cliente> lista = ClienteDAO.listarClientes();
            Cliente cliente;
            if (lista.isEmpty()) {
                cliente = new Cliente(0, "Paulo", "00000000002", "paulo@email.com", "02020202020", "senhateste",
                        "Rua B, 12");
                System.out.println("Inserindo cliente no banco:");
                ClienteDAO.inserirCliente(cliente);
                cliente = ClienteDAO.buscarPorCpf(cliente.getCpf());
                System.out.println(cliente);
            } else {
                cliente = lista.getFirst();
            }

            System.out.println("Editando cliente:");
            cliente.setNome("Victor");
            ClienteDAO.atualizarCliente(cliente);
            System.out.println(cliente);

            System.out.println("Buscando cliente por cpf:");
            cliente = ClienteDAO.buscarPorCpf(cliente.getCpf());
            System.out.println(cliente);
            System.out.println("Cliente encontrado!");

            //System.out.println("Deletar cliente:");
            //ClienteDAO.excluirCliente(cliente);;
            //System.out.println("Cliente deletado com sucesso!");

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Falhou em ClienteDAO");
        }
        return false;
    }
}
