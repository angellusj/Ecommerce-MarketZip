package model.dao.test;

import model.dao.ClienteDAO;
import model.entity.Cliente;

public class ClienteDAOTest {
    public static void main(String[] args) {

        Cliente cliente = new Cliente(
                0,
                "Carlos Silva",
                "12345678900",
                "carlos@email.com",
                "9999999",
                "senha123",
                "rua rua rua, 123"
        );

        System.out.println("\nTESTE INSERIR CLIENTE");
        int idGerado = ClienteDAO.inserirCliente(cliente);
        System.out.println("ID gerado: " + idGerado);

        System.out.println("\nTESTE BUSCAR");
        Cliente c1 = ClienteDAO.buscarPorCpf(cliente.getCpf());
        System.out.println(c1 != null ? "encontrado: " + c1.getNome() : "cliente nao encontrado");

        System.out.println("\nTESTE ATUALIZAR CLIENTE");
        c1.setNome("carlos novo");
        c1.setEndereco("ota rua, 555");

        boolean atualizado = ClienteDAO.atualizarCliente(c1);
        System.out.println("atualizado? " + atualizado);

        Cliente c2 = ClienteDAO.buscarPorCpf(cliente.getCpf());
        System.out.println(c2 != null ? "novo nome: " + c2.getNome() : "erro ao buscar despois de atualizar");

        System.out.println("\nTESTE EXCLUIR CLIENTE");
        ClienteDAO.excluirCliente(c2);

    }

}
