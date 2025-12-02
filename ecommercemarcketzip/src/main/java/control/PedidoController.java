package control;

import java.sql.Date;
import java.util.List;

import model.dao.PedidoDAO;
import model.entity.Cliente;
import model.entity.Pedido;

public class PedidoController {
    public static Pedido criarPedido(int idPedido, Date data, Boolean finalizar, double valorTotal, Cliente cliente) {
        if (data == null) {
            throw new IllegalArgumentException("Data do pedido não pode ser nula.\n");
        }

        if (finalizar == null) {
            throw new IllegalArgumentException("Status de finalização do pedido não pode ser nulo.\n");
        }

        if (valorTotal < 0) {
            throw new IllegalArgumentException("Valor total do pedido não pode ser negativo.\n");
        }

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente do pedido não pode ser nulo.\n");
        }

        Pedido pedido = new Pedido(idPedido, data, finalizar, valorTotal, cliente);
        PedidoDAO.criarPedido(pedido);
        return pedido;
    }

    public static boolean excluirPedido(int idPedido) {
        Pedido pedido = PedidoDAO.buscarPorId(idPedido);
        if (pedido == null) {
            return false;
        }
        PedidoDAO.excluirPedido(pedido);
        return true;
    }

    public static Pedido atualizarPedido(int idPedido, Date data, Boolean finalizar, double valorTotal,
            Cliente cliente) {
        var p = new Pedido(idPedido, data, finalizar, valorTotal, cliente);
        PedidoDAO.atualizarPedido(p);
        return p;
    }

    public static List<Pedido> listarPedidos() {
        return PedidoDAO.listarTodosPedidosAtivos();
    }

    public static List<Pedido> listarPedidosFeitos() {
        return PedidoDAO.listarPedidosFeitos();
    }

    public static List<Pedido> listarPedidosConcluidosDoCliente(Cliente cliente) {
        return PedidoDAO.listarPedidosConcluidosDoCliente(cliente);
    }

    public static List<Pedido> listarPedidosAtivosDoCliente(Cliente cliente) {
        return PedidoDAO.listarPedidosAtivosDoCliente(cliente);
    }

    public static List<Pedido> listarPedidosPorConsulta(String consulta) {
        return PedidoDAO.listarPedidosPorConsulta(consulta);
    }

    public static double calcularValorTotal() {
        return PedidoDAO.calcularValorTotal();
    }

    public static Pedido buscarPedidoPorId(int idPedido) {
        return PedidoDAO.buscarPorId(idPedido);
    }

    public static boolean finalizarPedido() {
        try {
            PedidoDAO.finalizarPedido();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String visualizarPedido(int idPedido ) {
        return PedidoDAO.visualizarPedido(idPedido);
    }

    public static boolean adicionarItemAoPedido(Pedido pedido, model.entity.Produto produto) {
        try {
            PedidoDAO.adicionarItemAoPedido(pedido, produto);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean removerItemDoPedido(Pedido pedido, model.entity.Produto produto) {
        try {
            PedidoDAO.removerItemDoPedido(pedido, produto);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
