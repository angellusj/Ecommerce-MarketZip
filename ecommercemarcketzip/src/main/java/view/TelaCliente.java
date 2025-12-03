package view;

import model.entity.*;

import java.util.List;
import java.util.Scanner;
import utils.Logg;
import control.PedidoController;
import control.ProdutoController;

public class TelaCliente {
    public static void menuCliente(Cliente cliente, Scanner leitor) {
        int opcao;

        do {
            Logg.info("=== MENU DE CLIENTE ===");
            System.out.println("1 - Criar Pedido");
            System.out.println("2 - Adicionar um item a um pedido");
            System.out.println("3 - Remover um item de um pedido");
            System.out.println("4 - Excluir um pedido");
            System.out.println("5 - Listar pedidos ativos");
            System.out.println("6 - Listar pedidos concluidos");
            System.out.println("7 - Sair");
            opcao = leitor.nextInt();

            switch (opcao) {
                case 1:
                    PedidoController.criarPedido(cliente);
                    break;

                case 2:
                    for (Pedido pedido : PedidoController.listarPedidosAtivosDoCliente(cliente)) {
                        System.out.println(pedido);
                    }
                    System.out.println("Em qual pedido adicionar? Digite o ID");
                    int idPedido = leitor.nextInt();
                    Pedido pedido = PedidoController.buscarPedidoPorId(idPedido);

                    for (Produto produto : ProdutoController.listarProdutos()) {
                        System.out.println(produto);
                    }
                    System.out.println("Qual produto adicionar? Digite o ID");
                    int idProduto = leitor.nextInt();
                    Produto produto = ProdutoController.buscarProdutoPorId(idProduto);

                    PedidoController.adicionarItemAoPedido(pedido, produto);
                    break;

                case 3:
                    for (Pedido pedidoR : PedidoController.listarPedidosAtivosDoCliente(cliente)) {
                        System.out.println(pedidoR);
                    }
                    System.out.println("Em qual pedido remover? Digite o ID");
                    idPedido = leitor.nextInt();
                    Pedido pedidoR = PedidoController.buscarPedidoPorId(idPedido);

                    ProdutoController.listarProdutos();
                    System.out.println("Qual produto remover? Digite o ID");
                    idProduto = leitor.nextInt();

                    Produto produtoR = ProdutoController.buscarProdutoPorId(idProduto);
                    PedidoController.removerItemDoPedido(pedidoR, produtoR);
                    break;

                case 4:
                    for (Pedido pedidos : PedidoController.listarPedidosAtivosDoCliente(cliente)) {
                        System.out.println(pedidos);
                    }
                    System.out.println("Qual remover?");
                    idPedido = leitor.nextInt();
                    PedidoController.excluirPedido(idPedido);
                    break;

                case 5:
                    for (Pedido pedidos : PedidoController.listarPedidosAtivosDoCliente(cliente)) {
                        System.out.println(pedidos);
                    }
                    break;

                case 6:
                    List<Pedido> concluidos = PedidoController.listarPedidosConcluidosDoCliente(cliente);

                    if (concluidos.isEmpty()) {
                        Logg.info("Nao ha pedidos concluidos!");
                    } else {
                        for (Pedido p : concluidos) {
                            System.out.println(p);
                        }
                    }

                default:
                    Logg.warning("Opçao invalida, tente de novo!");
                    break;
            }
        } while (opcao != 7);
    }
}
