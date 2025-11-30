package model.dao.test;

import model.dao.PedidoDAO;
import model.entity.Cliente;
import model.entity.Pedido;
import model.entity.Produto;

import java.sql.Date;
import java.util.List;

public class TestePedidoDAO {
    public static void main(String[] args) {
        System.out.println("=== Mostrando pedidos ===");
        PedidoDAO.mostrarPedido();

        System.out.println("\n=== Finalizando pedidos abertos e recalculando totais ===");
        PedidoDAO.finalizarPedido();

        System.out.println("\n=== Detalhamento de pedidos ===");
        String detalhamento = PedidoDAO.detalharPedido();
        System.out.println(detalhamento);

        // IDs de exemplo - ajuste conforme seu banco
        int idPedidoExemplo = 1;
        int idProdutoExemplo = 1;
        int idClienteExemplo = 1; // corresponde a cliente.id_cli
        int idUsuarioExemplo = 1; // corresponde a usuario.id_usu

        Pedido pedido = new Pedido(idPedidoExemplo, new Date(System.currentTimeMillis()), false, 0.0);
        Produto produto = new Produto(idProdutoExemplo, "Produto Teste", "Desc", 10.0, "Cat");
        Cliente cliente = new Cliente(idClienteExemplo, idUsuarioExemplo, "Nome", "000.000.000-00", "email@ex.com", "(00)0000-0000", "senha", "Endereco");

        System.out.println("\n=== Adicionando item ao pedido ===");
        PedidoDAO.adicionarItemAoPedido(pedido, produto);

        System.out.println("\n=== Removendo item do pedido ===");
        PedidoDAO.removerItemDoPedido(pedido, produto);

        System.out.println("\n=== Atualizando data do pedido ===");
        PedidoDAO.atualizarPedido(pedido, idPedidoExemplo, new Date(System.currentTimeMillis()));

        System.out.println("\n=== Listar pedidos feitos (finalizados) ===");
        List<Pedido> feitos = PedidoDAO.listarPedidosFeitos();
        feitos.forEach(p -> System.out.println(p.toString()));

        System.out.println("\n=== Listar pedidos ativos do cliente ===");
        List<Pedido> ativosCliente = PedidoDAO.listarPedidosAtivosDoCliente(cliente);
        ativosCliente.forEach(p -> System.out.println(p.toString()));

        System.out.println("\n=== Listar todos pedidos ativos ===");
        List<Pedido> ativos = PedidoDAO.listarTodosPedidosAtivos();
        ativos.forEach(p -> System.out.println(p.toString()));

        System.out.println("\n=== Listar pedidos concluídos do cliente ===");
        List<Pedido> concluidosCliente = PedidoDAO.listarPedidosConcluidosDoCliente(cliente);
        concluidosCliente.forEach(p -> System.out.println(p.toString()));

        System.out.println("\n=== Calcular valor total de todos os pedidos ===");
        double total = PedidoDAO.calcularValorTotal();
        System.out.println("Total: " + total);

        System.out.println("\n=== Excluir pedido (cuidado) ===");
        // PedidoDAO.excluirPedido(pedido);
        // System.out.println("Pedido excluído: " + idPedidoExemplo);
    }
}
