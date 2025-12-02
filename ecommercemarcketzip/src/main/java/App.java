import java.util.Scanner;

import utils.Logg;
import utils.Utils;
import view.TelaLogin;
import view.TelaFuncionario;
import model.entity.Funcionario;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        menu(scanner);

        scanner.close();
    }

    public static void menu(Scanner scanner) {
        int opcao = 0;

        do {
            Utils.clearScreen();
            Logg.info("=== Menu Principal ===");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = scanner.nextInt();
                if (scanner.hasNextLine())
                    scanner.nextLine(); // Limpar o buffer do scanner
                switch (opcao) {
                    case 1:
                        Funcionario user = TelaLogin.login(scanner);
                        if (user != null) {
                            Logg.info("Login bem sucedido!");
                            TelaFuncionario.menuFuncionario(user, scanner);
                        }
                        break;
                    case 2:
                        Logg.info("Saindo...");
                        break;
                    default:
                        Logg.warning("Opcao invalida, tente novamente.");
                }
            } catch (Exception e) {
                Logg.severe("Erro: " + e.getMessage());
                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
        } while (opcao != 2);
    }
}
