package model.dao.test;

import model.entity.Cliente;
import model.entity.Funcionario;

import java.util.List;

import model.dao.FuncionarioDAO;

public class TestFuncionarioDAO implements TesteDaoComponent{

    @Override
    public boolean teste() {

        try{
            System.out.println("TESTANDO FUNCIONARIO DAO");
            Funcionario funcionario;

            System.out.println("Pegando os funcionarios no banco e inserindo numa lista");
            List<Funcionario> lista = FuncionarioDAO.listarFuncionarios();
            if (lista.isEmpty())
                funcionario = new Funcionario(4, "jose", "123648", "naoseioq@gmail.com", "12345", "senhateste", "gerente");
            else {
                funcionario = lista.getFirst();
            }

            System.out.println("Inserindo funcionario no banco:");
            FuncionarioDAO.inserirFuncionario(funcionario);

            System.out.println("Editando funcionario:");
            funcionario.setNome("João");
            FuncionarioDAO.atualizarFuncionario(funcionario);

            System.out.println("Buscando funcionario por cpf:");
            funcionario = FuncionarioDAO.buscarPorCpf(funcionario.getCpf());
            System.out.println(funcionario);
            System.out.println("Funcionario encontrado!");

            System.out.println("Deletar funcionario:");
            FuncionarioDAO.excluirFuncionario(funcionario);;
            System.out.println("Cliente deletado com sucesso!");

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Falhou em ClienteDAO");
        }
        return false;
    }
        }

        /*Funcionario novoFuncionario = new Funcionario(
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

        System.out.println("\nBuscando funcionario pelo CPF...");

        Funcionario f = FuncionarioDAO.buscarPorCpf(novoFuncionario.getCpf());

        if (f != null) {
            System.out.println("Funcionario encontrado:");
            System.out.println(f);
        } else {
            System.out.println("Nenhum funcionario encontrado de CPF " );
        }
    }
}*/
