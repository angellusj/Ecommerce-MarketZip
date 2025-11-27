package model.dao.test;

import model.entity.Funcionario;
import model.dao.FuncionarioDAO;

public class TestaFuncionarioDAO {
    public static void main(String[] args) {

        Funcionario novoFuncionario = new Funcionario(
                0,
                "Carlos Alberto",
                "2323232",
                "carloesmatdor@email.com",
                "9999999",
                "senha123",
                "Gerente"
        );

        System.out.println("Inserindo funcionario...");
        int idFuncGerado = FuncionarioDAO.inserirFuncionario(novoFuncionario);
        if (idFuncGerado == -1) {
            System.out.println("CPF ja cadastrado tnte dnv.");
            return;
        }

        if (idFuncGerado == -2) {
            System.out.println("Email já cadastrado tente de nv.");
            return;
        }
        System.out.println("Funcionario inserido");

        System.out.println("\nBuscando funcionario pelo ID...");

        Funcionario f = FuncionarioDAO.buscarPorId(idFuncGerado);

        if (f != null) {
            System.out.println("Funcionario encontrado:");
            System.out.println(f);
        } else {
            System.out.println("Nenhum funcionario encontrado de ID " + idFuncGerado);
        }
    }
}
