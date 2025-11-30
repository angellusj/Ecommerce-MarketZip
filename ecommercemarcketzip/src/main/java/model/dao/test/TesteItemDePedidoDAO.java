package model.dao.test;

import model.dao.ItemDePedidoDAO;
import model.entity.ItemDePedido;
import model.entity.Pedido;
import model.entity.Produto;

import java.sql.Date;
import java.util.List;

public class TesteItemDePedidoDAO {
    public static void main(String[] args) {
        // Ajuste estes IDs para valores existentes no seu banco
        int idPedidoExistente = 1; // id de um pedido existente
        int idProdutoExistente = 1; // id de um produto existente

        Pedido pedido = new Pedido(idPedidoExistente, new Date(System.currentTimeMillis()), false, 0.0);
        Produto produto = new Produto(idProdutoExistente, "Produto Teste", "Desc", 10.0, "Categoria");

        System.out.println("=== TESTE ItemDePedidoDAO ===");

        // Criar item
        ItemDePedido novoItem = ItemDePedidoDAO.criarItem(pedido, produto, 2);
        System.out.println("Criado: " + novoItem);

        // Listar itens do pedido
        List<ItemDePedido> itens = ItemDePedidoDAO.listarItens(pedido);
        System.out.println("Itens do pedido " + pedido.getIdPedido() + ":");
        for (ItemDePedido it : itens) {
            System.out.println(it);
        }

        // Atualizar quantidade
        ItemDePedidoDAO.atualizarItem(novoItem, 5);
        System.out.println("Atualizado: " + novoItem);

        // Excluir item
        ItemDePedidoDAO.excluirItem(novoItem);
        System.out.println("Item excluído: id=" + novoItem.getIdItem());

        // Listar novamente
        List<ItemDePedido> itensDepois = ItemDePedidoDAO.listarItens(pedido);
        System.out.println("Itens após exclusão:");
        for (ItemDePedido it : itensDepois) {
            System.out.println(it);
        }

        System.out.println("=== FIM TESTE ===");
    }
}
