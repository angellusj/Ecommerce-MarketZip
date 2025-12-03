package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import control.ClienteController;
import control.FuncionarioController;
import model.entity.Cliente;
import model.entity.Funcionario;
import model.entity.Pedido;
import utils.Logg;

public class TelaFuncionario {
    public static void menuFuncionario(Funcionario func, Scanner scanner) {
        int opcao = 0;

        do {
            Logg.info("=== Menu do Funcionário ===");
            System.out.println("1. Gerenciar pedidos de clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar esta conta");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        menuPedido(scanner);
                        break;
                    case 2:
                        menuProduto(scanner);
                        break;
                    case 3:
                        menuConta(scanner, func);
                        break;
                    default:
                        Logg.warning("Opção inválida, tente novamente.");
                }

            } catch (NumberFormatException e) {
                Logg.warning("Informe somente números para a opção.");
            } catch (InputMismatchException e) {
                Logg.warning("Erro: informe um valor válido.");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            } catch (Exception e) {
                Logg.severe("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 4);
    }

    private static void menuConta(Scanner leitor, Funcionario funcionario) {
        int opcao;
        String nome, email, telefone, senha, cargo;
        System.out.println("1. Atualizar informações desta conta");
        System.out.println("2. Excluir esta conta");
        System.out.println("3. voltar");
        opcao = leitor.nextInt();
        leitor.nextLine();

        switch (opcao) {
            case 1:
                System.out.println("Nome novo: ");
                nome = leitor.nextLine();

                System.out.println("Email novo: ");
                email = leitor.nextLine();

                System.out.println("Senha nova: ");
                senha = leitor.nextLine();

                System.out.println("Telefone novo: ");
                telefone = leitor.nextLine();

                System.out.println("Cargo novo: ");
                cargo = leitor.nextLine();

                funcionario = new Funcionario(funcionario.getIdUsuario(), nome, funcionario.getCpf(), email, telefone,
                        senha, cargo);

                if (FuncionarioController.atualizarFuncionario(funcionario)) {
                    Logg.info("Atualizado!");
                }

                break;
                case 2:
                if(FuncionarioController.deletarFuncionario(funcionario)){
                    Logg.info("Deletado! Por favor, deslogue.");
                    return;
                }
                break;
            default:
                break;
        }

    }

    private static void menuPedido(Scanner scanner) {
        int opcao = 0;

        do {
            Logg.info("=== Menu do Pedido ===");
            System.out.println("1. Consultar Pedidos");
            System.out.println("2. Excluir Pedido");
            System.out.println("3. Finalizar Pedido");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        consultarPedido(scanner);
                        break;
                    case 2:
                        if (scanner.hasNextLine())
                            scanner.nextLine();
                        Logg.info("=== Excluir Pedido ===");
                        
                        List<model.entity.Pedido> pedidosAtivos = control.PedidoController.listarPedidos();
                        if (pedidosAtivos == null || pedidosAtivos.isEmpty()) {
                            Logg.warning("Nenhum pedido ativo encontrado.");
                            break;
                        }
                        
                        Logg.info("Pedidos ativos:");
                        pedidosAtivos.forEach(System.out::println);
                        
                        System.out.print("ID do Pedido para excluir: ");
                        int idPedidoExcluir = scanner.nextInt();
                        scanner.nextLine();
                        
                        model.entity.Pedido pedidoExcluir = control.PedidoController.buscarPedidoPorIdSemValidacao(idPedidoExcluir);
                        if (pedidoExcluir != null) {
                            Logg.info("Pedido encontrado:");
                            System.out.println(pedidoExcluir);
                            Logg.warning("Deseja realmente excluir este pedido? (S/n)");
                            System.out.print("Resposta: ");
                            String respostaExcluir = scanner.nextLine().toUpperCase();
                            if (respostaExcluir.equals("S")) {
                                boolean excluido = control.PedidoController.excluirPedido(idPedidoExcluir);
                                if (excluido) {
                                    Logg.info("Pedido excluído com sucesso!");
                                } else {
                                    Logg.warning("Erro ao excluir pedido.");
                                }
                            } else {
                                Logg.info("Operação cancelada.");
                            }
                        } else {
                            Logg.warning("Pedido não encontrado.");
                        }
                        break;
                    case 3:
                        if (scanner.hasNextLine())
                            scanner.nextLine();
                        
                        List<Pedido> pedidosAtivosParaFinalizar = control.PedidoController.listarTodosPedidosAtivos();
                        if (pedidosAtivosParaFinalizar.isEmpty()) {
                            Logg.warning("Nenhum pedido ativo encontrado para finalizar.");
                            break;
                        }
                        
                        Logg.info("=== Lista de Pedidos Ativos ===");
                        System.out.printf("%-5s %-20s%n", "ID", "Status");
                        System.out.println("-".repeat(27));
                        pedidosAtivosParaFinalizar.forEach(p -> System.out.printf("%-5d %-20s%n", p.getIdPedido(), (p.getFinalizar() ? "Finalizado" : "Ativo")));
                        System.out.println();
                        
                        Logg.info("=== Finalizar Pedido ===");
                        System.out.print("ID do Pedido: ");
                        int idPedidoFinalizar = scanner.nextInt();
                        scanner.nextLine();
                        
                        model.entity.Pedido pedidoFinalizar = control.PedidoController.buscarPedidoPorIdSemValidacao(idPedidoFinalizar);
                        if (pedidoFinalizar != null) {
                            Logg.info("Pedido encontrado:");
                            System.out.println(pedidoFinalizar);
                            Logg.warning("Deseja realmente finalizar este pedido? (S/n)");
                            System.out.print("Resposta: ");
                            String respostaFinalizar = scanner.nextLine().toUpperCase();
                            if (respostaFinalizar.equals("S")) {
                                boolean finalizado = control.PedidoController.finalizarPedido();
                                if (finalizado) {
                                    Logg.info("Pedido finalizado com sucesso!");
                                } else {
                                    Logg.warning("Erro ao finalizar pedido.");
                                }
                            } else {
                                Logg.info("Operação cancelada.");
                            }
                        } else {
                            Logg.warning("Pedido não encontrado.");
                        }
                        break;
                    case 4:
                        Logg.info("Voltando ao menu anterior...");
                        break;
                    default:
                        Logg.warning("Opção inválida, tente novamente.");
                }

            } catch (NumberFormatException e) {
                Logg.warning("Informe somente números para a opção.");
            } catch (InputMismatchException e) {
                Logg.warning("Erro: informe um valor válido.");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            } catch (Exception e) {
                Logg.severe("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 4);
    }

    private static void consultarPedido(Scanner scanner) {
        int opcao = 0;

        do {
            Logg.info("=== Consultar Pedidos ===");
            System.out.println("1. Pedidos ativos do cliente");
            System.out.println("2. Buscar pedido por ID");
            System.out.println("3. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        if (scanner.hasNextLine())
                            scanner.nextLine();
                        Logg.info("Informe o CPF do cliente para consultar seus pedidos");
                        System.out.print("CPF: ");
                        String cpfConsulta = scanner.nextLine();
                        Cliente clienteConsulta = ClienteController.buscarPorCpf(cpfConsulta);
                        if (clienteConsulta == null) {
                            Logg.warning("Cliente não encontrado.");
                            break;
                        }
                        List<model.entity.Pedido> pedidos = control.PedidoController
                                .listarPedidosAtivosDoCliente(clienteConsulta);
                        if (pedidos.isEmpty()) {
                            Logg.warning("Nenhum pedido ativo encontrado para este cliente.");
                        } else {
                            Logg.info("Pedidos ativos do cliente " + clienteConsulta.getNome() + ":");
                            pedidos.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        if (scanner.hasNextLine())
                            scanner.nextLine();

                        List<model.entity.Pedido> todosOsPedidos = control.PedidoController.listarPedidosFeitos();
                        if (todosOsPedidos.isEmpty()) {
                            Logg.warning("Nenhum pedido encontrado.");
                            break;
                        }

                        Logg.info("=== Lista de Todos os Pedidos ===");
                        System.out.printf("%-5s %-20s%n", "ID", "Status");
                        System.out.println("-".repeat(27));
                        todosOsPedidos.forEach(p -> System.out.printf("%-5d %-20s%n", p.getIdPedido(),
                                (p.getFinalizar() ? "Finalizado" : "Ativo")));
                        System.out.println();

                        Logg.info("=== Buscar Pedido por ID ===");
                        System.out.print("ID do Pedido: ");
                        int idPedido = scanner.nextInt();
                        scanner.nextLine();

                        model.entity.Pedido pedido = control.PedidoController.buscarPedidoPorId(idPedido);
                        if (pedido != null) {
                            Logg.info("Pedido encontrado:");
                            System.out.println(pedido);
                        } else {
                            Logg.warning("Pedido não encontrado.");
                        }
                        break;
                    case 3:
                        Logg.info("Voltando ao menu anterior...");
                        break;
                    default:
                        Logg.warning("Opção inválida, tente novamente.");
                }

            } catch (NumberFormatException e) {
                Logg.warning("Informe somente números para a opção.");
            } catch (InputMismatchException e) {
                Logg.warning("Erro: informe um valor válido.");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            } catch (Exception e) {
                Logg.severe("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 3);
    }

    public static void menuProduto(Scanner scanner) {
        int opcao = 0;

        do {
            Logg.info("=== Menu de Produtos ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Buscar Produto");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Excluir Produto");
            System.out.println("5. Listar Todos os Produtos");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        cadastrarProduto(scanner);
                        break;
                    case 2:
                        buscarProduto(scanner);
                        break;
                    case 3:
                        atualizarProduto(scanner);
                        break;
                    case 4:
                        excluirProduto(scanner);
                        break;
                    case 5:
                        listarProdutos(scanner);
                        break;
                    case 6:
                        Logg.info("Voltando ao menu anterior...");
                        break;
                    default:
                        Logg.warning("Opção inválida, tente novamente.");
                }

            } catch (NumberFormatException e) {
                Logg.warning("Informe somente números para a opção.");
            } catch (InputMismatchException e) {
                Logg.warning("Erro: informe um valor válido.");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            } catch (Exception e) {
                Logg.severe("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 6);
    }

    private static void cadastrarProduto(Scanner scanner) {
        try {
            if (scanner.hasNextLine())
                scanner.nextLine();

            Logg.info("=== Cadastro de Produto ===");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Preço: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Categoria: ");
            String categoria = scanner.nextLine();

            control.ProdutoController.criarProduto(0, nome, descricao, preco, categoria);
            Logg.info("Produto cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            Logg.warning("Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            Logg.warning("Erro: preço deve ser um número válido.");
            scanner.nextLine();
        } catch (Exception e) {
            Logg.severe("Erro inesperado: " + e.getMessage());
        }
    }

    private static void buscarProduto(Scanner scanner) {
        try {
            Logg.info("=== Buscar Produto ===");
            System.out.println("1. Buscar por ID");
            System.out.println("2. Buscar por Nome");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            model.entity.Produto produto = null;

            if (opcao == 1) {
                List<model.entity.Produto> produtos = control.ProdutoController.listarProdutos();
                if (produtos == null || produtos.isEmpty()) {
                    Logg.warning("Nenhum produto cadastrado.");
                    return;
                }

                Logg.info("=== Lista de Produtos ===");
                System.out.printf("%-5s %-30s %-10s%n", "ID", "Nome", "Preço");
                System.out.println("-".repeat(47));
                produtos.forEach(
                        p -> System.out.printf("%-5d %-30s R$ %.2f%n", p.getIdProduto(), p.getNome(), p.getPreco()));
                System.out.println();

                System.out.print("ID do Produto: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                produto = control.ProdutoController.buscarProdutoPorId(id);
            } else if (opcao == 2) {
                System.out.print("Nome do Produto: ");
                String nome = scanner.nextLine();
                produto = control.ProdutoController.mostraProduto(nome);
            } else {
                Logg.warning("Opção inválida.");
                return;
            }

            if (produto != null) {
                Logg.info("Produto encontrado:");
                System.out.println(produto);
            } else {
                Logg.warning("Produto não encontrado.");
            }

        } catch (IllegalArgumentException e) {
            Logg.warning("Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            Logg.warning("Erro: informe um valor válido.");
            scanner.nextLine();
        } catch (Exception e) {
            Logg.severe("Erro inesperado: " + e.getMessage());
        }
    }

    private static void atualizarProduto(Scanner scanner) {
        try {
            if (scanner.hasNextLine())
                scanner.nextLine();

            Logg.info("=== Atualizar Produto ===");
            System.out.print("ID do Produto: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            model.entity.Produto produtoExistente = control.ProdutoController.buscarProdutoPorId(id);
            if (produtoExistente == null) {
                Logg.warning("Produto não encontrado.");
                return;
            }

            Logg.info("Produto atual:");
            System.out.println(produtoExistente);
            Logg.info("\nInforme os novos dados (ou Enter para manter o atual):");

            System.out.print("Nome [" + produtoExistente.getNome() + "]: ");
            String nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                nome = produtoExistente.getNome();
            }

            System.out.print("Descrição [" + produtoExistente.getDesc() + "]: ");
            String descricao = scanner.nextLine();
            if (descricao.trim().isEmpty()) {
                descricao = produtoExistente.getDesc();
            }

            System.out.print("Preço [" + produtoExistente.getPreco() + "]: ");
            String precoStr = scanner.nextLine();
            double preco = precoStr.trim().isEmpty() ? produtoExistente.getPreco() : Double.parseDouble(precoStr);

            System.out.print("Categoria [" + produtoExistente.getCategoria() + "]: ");
            String categoria = scanner.nextLine();
            if (categoria.trim().isEmpty()) {
                categoria = produtoExistente.getCategoria();
            }

            control.ProdutoController.atualizarProduto(id, nome, descricao, preco, categoria);
            Logg.info("Produto atualizado com sucesso!");

        } catch (NumberFormatException e) {
            Logg.warning("Erro: preço deve ser um número válido.");
        } catch (IllegalArgumentException e) {
            Logg.warning("Erro: " + e.getMessage());
        } catch (Exception e) {
            Logg.severe("Erro inesperado: " + e.getMessage());
        }
    }

    private static void excluirProduto(Scanner scanner) {
        try {
            if (scanner.hasNextLine())
                scanner.nextLine();

            Logg.info("=== Excluir Produto ===");
            System.out.print("ID do Produto: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            model.entity.Produto produto = control.ProdutoController.buscarProdutoPorId(id);
            if (produto == null) {
                Logg.warning("Produto não encontrado.");
                return;
            }

            Logg.info("Produto encontrado:");
            System.out.println(produto);

            Logg.warning("Deseja realmente excluir este produto? (S/n)");
            System.out.print("Resposta: ");
            String resposta = scanner.nextLine().toUpperCase();

            if (resposta.equals("S")) {
                boolean excluido = control.ProdutoController.excluirProduto(id);
                if (excluido) {
                    Logg.info("Produto excluído com sucesso!");
                } else {
                    Logg.warning("Erro ao excluir produto.");
                }
            } else {
                Logg.info("Operação cancelada.");
            }

        } catch (IllegalArgumentException e) {
            Logg.warning("Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            Logg.warning("Erro: informe um valor válido.");
            scanner.nextLine();
        } catch (Exception e) {
            Logg.severe("Erro inesperado: " + e.getMessage());
        }
    }

    private static void listarProdutos(Scanner scanner) {
        try {
            Logg.info("=== Lista de Produtos ===");
            List<model.entity.Produto> produtos = control.ProdutoController.listarProdutos();

            if (produtos.isEmpty()) {
                Logg.warning("Nenhum produto cadastrado.");
            } else {
                System.out.println("\nTotal de produtos: " + produtos.size());
                System.out.println("----------------------------------------");
                produtos.forEach(produto -> {
                    System.out.println(produto);
                    System.out.println("----------------------------------------");
                });
            }

        } catch (Exception e) {
            Logg.severe("Erro ao listar produtos: " + e.getMessage());
        }
    }
}
