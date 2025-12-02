package model.dao.test;

import model.dao.ClienteDAO;
import model.dao.PedidoDAO;
import model.dao.ProdutoDAO;
import model.entity.Cliente;
import model.entity.Pedido;
import model.entity.Produto;

import java.sql.Date;
import java.util.List;

public class TestePedidoDAO implements TesteDaoComponent {
    @Override
    public boolean teste() {
        try {
            System.out.println("\nTestando PedidoDAO");

            Pedido pedido;
            long uniqueId = System.currentTimeMillis() % 10000000000L;
            String cpfUnique = String.format("%014d", uniqueId);
            Cliente cliente = new Cliente(0, "juninhoteste", cpfUnique, "juninhoteste@teste.com", "1234567", "senhateste", "rua teste");
            ClienteDAO.inserirCliente(cliente);

            // Always create a new pedido for this test
            pedido = new Pedido(0, new Date(0), false, 0, cliente);
            PedidoDAO.criarPedido(pedido);
            System.out.println("Pedido criado com sucesso!");

            PedidoDAO.atualizarPedido(pedido);
            System.out.println("Pedido atualizado com sucesso!");

            System.out.println("=== Mostrando pedidos ===");
            PedidoDAO.mostrarPedido();
            System.out.println("Pedidos mostrados com sucesso!");

            System.out.println("\n=== Finalizando pedidos abertos e recalculando totais ===");
            PedidoDAO.finalizarPedido();
            System.out.println("Pedidos finalizados com sucesso!");

            System.out.println("\n=== Detalhamento de pedidos ===");
            String detalhamento = PedidoDAO.detalharPedido();
            System.out.println(detalhamento);
            System.out.println("Detalhamento realizado com sucesso!");

            Produto produto = new Produto(1, "Produto Teste", "Descricao", 20.0, "Categoria Teste");
            ProdutoDAO.criarProduto(produto);

            System.out.println("\n=== Adicionando item ao pedido ===");
            PedidoDAO.adicionarItemAoPedido(pedido, produto);
            System.out.println("Item adicionado com sucesso!");

            System.out.println("\n=== Removendo item do pedido ===");
            PedidoDAO.removerItemDoPedido(pedido, produto);
            System.out.println("Item removido com sucesso!");

            System.out.println("\n=== Atualizando data do pedido ===");
            pedido.setData(new Date(27122024));
            PedidoDAO.atualizarPedido(pedido);

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
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Falhou em PedidoDAO");
        }
        return false;
    }
}