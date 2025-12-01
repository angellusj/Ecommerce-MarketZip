package model.dao.test;

import model.dao.ItemDePedidoDAO;
import model.dao.PedidoDAO;
import model.dao.ProdutoDAO;
import model.entity.ItemDePedido;
import model.entity.Pedido;
import model.entity.Produto;

import java.util.List;

public class TesteItemDePedidoDAO implements TesteDaoComponent{
    @Override
    public boolean teste() {
        try{
        // Get an existing active pedido or create one
        List<Pedido> pedidos = PedidoDAO.listarTodosPedidosAtivos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido ativo encontrado. Pulando teste ItemDePedidoDAO.");
            return false;
        }
        
        Pedido pedido = pedidos.getFirst();
        
        // Create a test product
        Produto produto = new Produto(0, "Produto Teste ItemDePedido", "Desc", 10.0, "Categoria");
        ProdutoDAO.criarProduto(produto);

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
        return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Falhou em ItemDePedidoDAO");
        }
        return false;
    }
}
