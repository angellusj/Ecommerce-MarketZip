package view;

import model.entity.*;

import java.util.List;
import java.util.Scanner;
import utils.Logg;
import control.ClienteController;
import control.PedidoController;
import control.ProdutoController;

public class TelaCliente {
    public static void menuCliente(Cliente cliente, Scanner leitor) {
        int opcao;
        String nome, email, senha, endereco, telefone;

        do {
            Logg.info("=== MENU DE CLIENTE ===");
            System.out.println("1 - Criar Pedido");
            System.out.println("2 - Adicionar um item a um pedido");
            System.out.println("3 - Remover um item de um pedido");
            System.out.println("4 - Excluir um pedido");
            System.out.println("5 - Listar pedidos ativos");
            System.out.println("6 - Listar pedidos concluídos");
            System.out.println("7 - excluir minha conta");
            System.out.println("8 - Atualizar minhas informações");
            System.out.println("9 - Sair");
            opcao = leitor.nextInt();

            switch (opcao) {
                case 1:
                    PedidoController.criarPedido(cliente);
                    Logg.info("Criado!");
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
                    System.out.println("Qual remover? Digite o ID");
                    idPedido = leitor.nextInt();
                    PedidoController.excluirPedido(idPedido);
                    break;

                case 5:
                    if (PedidoController.listarPedidosAtivosDoCliente(cliente).isEmpty()) {
                        Logg.info("Não há pedidos");
                    } else {
                        for (Pedido pedidos : PedidoController.listarPedidosAtivosDoCliente(cliente)) {
                            System.out.println(pedidos);
                        }

                    }
                    break;

                case 6:
                    List<Pedido> concluidos = PedidoController.listarPedidosConcluidosDoCliente(cliente);

                    if (concluidos.isEmpty()) {
                        Logg.info("Não há pedidos concluídos!");
                    } else {
                        for (Pedido p : concluidos) {
                            System.out.println(p);
                        }
                    }
                case 7:
                    if (ClienteController.deletarCliente(cliente)) {
                        Logg.info("Deletado!");
                        return;
                    } else {
                        Logg.warning("Algo deu errado!");
                    }

                    break;
                case 8:
                    leitor.nextLine();
                    System.out.println("Nome novo: ");
                    nome = leitor.nextLine();

                    System.out.println("Email novo: ");
                    email = leitor.nextLine();

                    System.out.println("Senha nova: ");
                    senha = leitor.nextLine();

                    System.out.println("Telefone novo: ");
                    telefone = leitor.nextLine();

                    System.out.println("Endereço novo: ");
                    endereco = leitor.nextLine();

                    cliente = new Cliente(cliente.getIdUsuario(), nome, cliente.getCpf(), email, telefone, senha,
                            endereco);
                    if (ClienteController.atualizarCliente(cliente)) {
                        Logg.info("Atualizado!");
                    }
                    break;
                default:
                    Logg.warning("Opção inválida, tente de novo!");
                    break;
            }
        } while (opcao != 9);
    }
}
