package control;

import model.dao.ItemDePedidoDAO;
import model.dao.ProdutoDAO;
import model.entity.ItemDePedido;
import model.entity.Pedido;
import model.entity.Produto;

import java.util.List;

public class ItemDePedidoController {

    public ItemDePedido criarItem(Pedido pedido, int idProduto, int quantidade) {
        if (pedido == null) {
            System.out.println("Erro ao inserir: pedido nulo");
            return null;
        } else if (idProduto <= 0) {
            System.out.println("Erro ao inserir: id do produto inválido");
            return null;
        } else if (quantidade <= 0) {
            System.out.println("Erro ao inserir: quantidade inválida");
            return null;
        }

        Produto produto = ProdutoDAO.buscarPorId(idProduto);
        if (produto == null) {
            System.out.println("Erro ao inserir: produto não encontrado");
            return null;
        }

        System.out.println("Cadastrado!");
        return ItemDePedidoDAO.criarItem(pedido, produto, quantidade);
    }

    public List<ItemDePedido> buscarItensPorPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("Pedido nulo");
            return null;
        }
        return ItemDePedidoDAO.listarItens(pedido);
    }

    public boolean atualizarItem(ItemDePedido item, int novaQuantidade) {
        if (item == null) {
            System.out.println("Objeto nulo");
            return false;
        } else if (novaQuantidade <= 0) {
            System.out.println("Erro ao atualizar: quantidade inválida");
            return false;
        }
        ItemDePedidoDAO.atualizarItem(item, novaQuantidade);
        System.out.println("Atualizado!");
        return true;
    }

    public boolean deletarItem(ItemDePedido item) {
        if (item == null) {
            System.out.println("Objeto nulo");
            return false;
        }
        ItemDePedidoDAO.excluirItem(item);
        System.out.println("Excluído!");
        return true;
    }

    public void listarTodosItens() {
        ItemDePedidoDAO.mostrarItem();
    }
}
