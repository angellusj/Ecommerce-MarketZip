package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import control.ClienteController;
import control.FuncionarioController;
import model.entity.Cliente;
import model.entity.Funcionario;
import utils.Logg;

//finalizarPedido
//visualizarPedido
//excluirPedido
//listarTodosPedidosAtivos
//listarPedidosConcluidosDoCliente
//listarTodosPedidosFeitos
//criarProduto
//mostrarProduto
//atualizarProduto
//excluirProduto
//listarProdutos

public class TelaFuncionario {
    public static void menuFuncionario(Funcionario func, Scanner scanner) {
        int opcao = 0;

        do {
            Logg.info("=== Menu do Funcionário ===");
            System.out.println("1. Gerenciar pedidos de clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Sair");
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
                        /*if (scanner.hasNextLine())
                            scanner.nextLine();
                        Logg.info("Informe o CPF do cliente ou digite 0 para volar ao menu principal");
                        System.out.print("CPF: ");
                        String cpf = scanner.nextLine();
                        if (cpf.equals("0")) {
                            Logg.info("Voltando ao menu principal...");
                            break;
                        }
                        Cliente cli = ClienteController.buscarPorCpf(cpf);
                        if (cli != null) {
                            Logg.info("Cliente encontrado!");
                            System.out.println(cli);
                            gerarPedido(func, cli, scanner);
                        } else {
                            Logg.warning("Cliente não encontrado.");
                        }
                        break;*/
                    case 3:
                        Logg.info("Saindo do menu do funcionário...");
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

    //finalizarPedido
//visualizarPedido
//excluirPedido
//listarTodosPedidosAtivos
//listarPedidosConcluidosDoCliente
//listarTodosPedidosFeitos

    private static void menuPedido(Scanner scanner) {
        // Implementar menu de gerenciamento de clientes
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
                        break;
                    case 2:
                        if (scanner.hasNextLine())
                            scanner.nextLine();
                        List<Cliente> c = ClienteController.listarClientes();
                        if (c.isEmpty()) {
                            Logg.warning("Nenhum cliente cadastrado.");
                            break;
                        }
                        Logg.info("TODOS OS CLIENTES");
                        c.forEach(System.out::println);

                        Logg.info("Informe o CPF do cliente");
                        System.out.print("CPF: ");
                        String cpf = scanner.nextLine();
                        Cliente cli = ClienteController.buscarPorCpf(cpf);

                        if (cli != null) {
                            Logg.info("Cliente encontrado!");
                            System.out.println(cli);
                           // editarCliente(cli.getIdCliente(), cli, scanner);
                        } else {
                            Logg.warning("Cliente não encontrado.");
                        }
                        break;
                    case 3:
                        if (scanner.hasNextLine())
                            scanner.nextLine();
                        List<Cliente> cl = ClienteController.listarClientes();
                        Logg.info("TODOS OS CLIENTES");
                        cl.forEach(System.out::println);

                        Logg.info("Informe o CPF do cliente");
                        System.out.print("CPF: ");
                        String cp = scanner.nextLine();
                        Cliente clie = ClienteController.buscarPorCpf(cp);
                        Logg.info("Cliente encontrado!");
                        System.out.println(clie);

                        if (clie != null) {
                            Logg.warning("Deseja realmente excluir o cliente? (S/n)");
                            System.out.print("Resposta: ");
                            String resposta = scanner.nextLine().toUpperCase();
                            if (!resposta.equals("S")) {
                                Logg.info("Operação cancelada.");
                                break;
                            }
                            ClienteController clienteCtrl = new ClienteController();
                            clienteCtrl.deletarCliente(clie);
                            Logg.info("Cliente excluído com sucesso!");
                        } else {
                            Logg.warning("Cliente não encontrado.");
                        }
                        break;
                    case 4:
                        if (scanner.hasNextLine())
                            scanner.nextLine();
                        List<Cliente> clien = ClienteController.listarClientes();
                        Logg.info("TODOS OS CLIENTES");
                        clien.forEach(System.out::println);

                        Logg.info("Informe o CPF do cliente");
                        System.out.print("CPF: ");
                        String cpfBuscado = scanner.nextLine();
                        Cliente clienteBusca = ClienteController.buscarPorCpf(cpfBuscado);
                        if (clienteBusca == null) {
                            Logg.warning("Cliente não encontrado.");
                            break;
                        }
                        Logg.info("Cliente encontrado!");
                        System.out.println(clienteBusca);
                        break;
                    case 5:
                        Logg.info("Saindo...");
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

    /*private static void cadastrarCliente(Scanner scanner) {
        String nome, cpf, telefone, endereco, email, senha;
        int idUsuario;
        try {
            if (scanner.hasNextLine())
                scanner.nextLine();

            Logg.info("===Cadastro de Cliente===");
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            System.out.print("CPF: ");
            cpf = scanner.nextLine();
            System.out.print("Email: ");
            email = scanner.nextLine();
            System.out.print("Telefone: ");
            telefone = scanner.nextLine();
            System.out.print("Senha: ");
            senha = scanner.nextLine();
            System.out.print("Endereço: ");
            endereco = scanner.nextLine();
            System.out.print("ID do Usuário: ");
            idUsuario = scanner.nextInt();

            ClienteController.cadastrarCliente(idUsuario, nome, cpf, email, telefone, senha, endereco);
            Logg.info("Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            Logg.warning(e.getMessage());
        }
    }

    private static void gerarPedido(Funcionario func, Cliente cli, Scanner scanner) {
        // Implementar geração de pedido
    }

    private static void consultarPedido(Scanner scanner) {
        // Implementar consulta de pedido 
    }

    private static void editarCliente(int id, Cliente cli, Scanner scanner) {
        // Implementar edição de cliente
    }

    public static void main(String[] args) {
    }*/
}
