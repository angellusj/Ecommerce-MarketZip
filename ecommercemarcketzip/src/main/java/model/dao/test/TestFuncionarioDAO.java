package model.dao.test;

import model.entity.Funcionario;

import java.util.List;

import model.dao.FuncionarioDAO;

public class TestFuncionarioDAO implements TesteDaoComponent{
    @Override
    public boolean teste() {

        try{
            System.out.println("TESTANDO FUNCIONARIO DAO");

            System.out.println("Pegando os funcionarios no banco e inserindo numa lista");
            List<Funcionario> lista = FuncionarioDAO.listarFuncionarios();
            Funcionario funcionario;
            if (lista.isEmpty()){
                funcionario = new Funcionario(0, "josé", "0000000011", "jose@email.com", "01010101000", "joseteste", "gerente");
                System.out.println("Inserindo funcionario no banco:");
                FuncionarioDAO.inserirFuncionario(funcionario);
                funcionario = FuncionarioDAO.buscarPorCpf(funcionario.getCpf());
                System.out.println(funcionario);
            } else {
                funcionario = lista.getFirst();
            }

            System.out.println("Editando funcionario:");
            funcionario.setNome("João");
            FuncionarioDAO.atualizarFuncionario(funcionario);
            System.out.println(funcionario);

            System.out.println("Buscando funcionario por cpf:");
            funcionario = FuncionarioDAO.buscarPorCpf(funcionario.getCpf());
            System.out.println(funcionario);
            System.out.println("Funcionario encontrado!");

            System.out.println("Deletar funcionario:");
            FuncionarioDAO.excluirFuncionario(funcionario);;
            System.out.println("Funcionario deletado com sucesso!");

            System.out.println("Listando todos os funcionarios:");
            List<Funcionario> funcionarios = FuncionarioDAO.listarFuncionarios();
            for (Funcionario f : funcionarios) {
                System.out.println(f);
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Falhou em FuncionarioDAO");
        }
        return false;
    }
}