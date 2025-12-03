import java.util.Scanner;

import control.ClienteController;
import control.FuncionarioController;
import utils.Logg;
import utils.Utils;
import view.TelaCliente;
import view.TelaFuncionario;
import model.entity.Cliente;
import model.entity.Funcionario;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        menu(scanner);
        scanner.close();
    }

    public static void menu(Scanner scanner) {

        int opcao = 0;
        String nome, cpf, email, telefone, senha, cargo, endereco, login;

        do {
            Logg.info("=== Menu Principal ===");
            System.out.println("1. Login");
            System.out.println("2. Cadastrar um usuario");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        Utils.clearScreen();
                        System.out.println("Voce é...");
                        System.out.println("1 - Um funcionario");
                        System.out.println("2 - Um cliente");
                        System.out.print("Escolha: ");

                        int tipo = scanner.nextInt();
                        scanner.nextLine();

                        if (tipo == 1) {
                            System.out.print("Login: ");
                            login = scanner.nextLine();

                            System.out.print("Senha: ");
                            senha = scanner.nextLine();

                            Funcionario funcionarioLogado = FuncionarioController.realizarLogin(login, senha);

                            if (funcionarioLogado == null) {
                                Logg.warning("Credenciais incorretas!");
                                System.out.println("Pressione ENTER para continuar...");
                                scanner.nextLine();
                            } else {
                                Utils.clearScreen();
                                TelaFuncionario.menuFuncionario(funcionarioLogado, scanner);
                            }

                        } else if (tipo == 2) {
                            System.out.print("CPF: ");
                            login = scanner.nextLine();

                            System.out.print("Senha: ");
                            senha = scanner.nextLine();

                            Cliente clienteLogado = ClienteController.realizarLoginCliente(login, senha);

                            if (clienteLogado == null) {
                                Logg.warning("Credenciais incorretas!");
                                System.out.println("Pressione ENTER para continuar...");
                                scanner.nextLine();
                            } else {
                                Utils.clearScreen();
                                TelaCliente.menuCliente(clienteLogado, scanner);
                            }
                        }
                        break;

                    case 2:
                        Utils.clearScreen();
                        System.out.println("1 - Cadastrar um funcionario");
                        System.out.println("2 - Cadastrar um cliente");
                        System.out.print("Escolha: ");

                        int tipoCadastro = scanner.nextInt();
                        scanner.nextLine();

                        if (tipoCadastro == 1) {
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

                            System.out.print("Cargo: ");
                            cargo = scanner.nextLine();

                            Funcionario funcionarioNovo = new Funcionario(
                                    0, nome, cpf, email, telefone, senha, cargo);

                            if (FuncionarioController.cadastrarFuncionario(funcionarioNovo)) {
                                Logg.info("Criado!");
                            } else {
                                Logg.warning("Erro ao criar funcionario");
                            }

                        } else if (tipoCadastro == 2) {

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

                            System.out.print("Endereco: ");
                            endereco = scanner.nextLine();

                            if (ClienteController.cadastrarCliente(
                                    0, nome, cpf, email, telefone, senha, endereco)) {
                                Logg.info("Criado!");
                            } else {
                                Logg.warning("Erro ao criar cliente");
                            }
                        } else {
                            Logg.warning("Opcao invalida!");
                        }

                        System.out.println("Pressione ENTER para continuar...");
                        scanner.nextLine();
                        Utils.clearScreen();
                        break;

                    case 3:
                        Logg.info("Saindo...");
                        break;

                    default:
                        Logg.warning("Opcao invalida, tente novamente.");
                        System.out.println("Pressione ENTER para continuar...");
                        scanner.nextLine();
                        Utils.clearScreen();
                }

            } catch (Exception e) {
                Logg.severe("Erro: " + e.getMessage());
                scanner.nextLine();
            }

        } while (opcao != 3);
    }
}
