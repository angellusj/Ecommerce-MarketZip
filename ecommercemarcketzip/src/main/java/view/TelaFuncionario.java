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
                            editarCliente(cli.getIdCliente(), cli, scanner);
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
        // Implementar menu de gerenciamento de produtos
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
