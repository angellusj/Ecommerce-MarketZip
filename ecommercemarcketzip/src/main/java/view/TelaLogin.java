package view;

import java.util.Scanner;

import control.FuncionarioController;
import model.entity.Funcionario;
import model.entity.Cliente;
import control.ClienteController;
import utils.Logg;

public class TelaLogin {
    public static Funcionario login(Scanner scanner) {
        String nome, senha;
        Funcionario func = null;
        try {
            Logg.info("==LOGIN==");
            boolean loginRealizado = false;
            do {
                try {
                    Logg.info("Insira seu nome de usuário: ");
                    System.out.print("nome de usuario: ");
                    nome = scanner.nextLine();

                    if (nome.equals("0")) {
                        Logg.info("Retornando ao menu principal...");
                        return null;
                    }

                    Logg.info("Insira a senha: ");
                    System.out.print("senha: ");
                    senha = scanner.nextLine();

                    if (nome.equals("0")) {
                        Logg.info("Retornando ao menu principal...");
                        return null;
                    }

                    func = FuncionarioController.realizarLogin(nome, senha);
                    if (func == null) {
                        Logg.warning("Nome de usuário ou senha incorretos. Por favor, tente novamente.");
                    } else {
                        loginRealizado = true;
                    }
                } catch (IllegalArgumentException e) {
                    Logg.warning("Erro: " + e.getMessage());
                    Logg.info("Por favor, tente novamente!");
                }
            } while (!loginRealizado);
            return func;

        } catch (Exception e) {
            Logg.severe("Erro inesperado: " + e.getMessage());
            return null;
        }
    }

    public static Cliente loginCliente(Scanner scanner) {
        String nome, senha;
        Cliente cli = null;
        try {
            Logg.info("==LOGIN CLIENTE==");
            boolean loginRealizado = false;
            do {
                try {
                    Logg.info("Insira seu nome de usuário: ");
                    System.out.print("nome de usuario: ");
                    nome = scanner.nextLine();

                    if (nome.equals("0")) {
                        Logg.info("Retornando ao menu principal...");
                        return null;
                    }

                    Logg.info("Insira a senha: ");
                    System.out.print("senha: ");
                    senha = scanner.nextLine();

                    if (nome.equals("0")) {
                        Logg.info("Retornando ao menu principal...");
                        return null;
                    }

                    cli = ClienteController.realizarLoginCliente(nome, senha);
                    if (cli == null) {
                        Logg.warning("Nome de usuário ou senha incorretos. Por favor, tente novamente.");
                    } else {
                        loginRealizado = true;
                    }
                } catch (IllegalArgumentException e) {
                    Logg.warning("Erro: " + e.getMessage());
                    Logg.info("Por favor, tente novamente!");
                }
            } while (!loginRealizado);
            return cli;

        } catch (Exception e) {
            Logg.severe("Erro inesperado: " + e.getMessage());
            return null;
        }
    }
}
