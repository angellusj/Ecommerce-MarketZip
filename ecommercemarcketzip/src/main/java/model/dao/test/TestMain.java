package model.dao.test;

public class TestMain {
    public static void main(String[] args) {
        TesteDaoComposite testes;

        testes = new TesteDaoComposite(
                new TestFuncionarioDAO(),
                new TestProdutoDAO(),
                new TestePedidoDAO(),
                new TesteItemDePedidoDAO(),
                new TestClienteDAO()
        );

        testes.teste();
    }
}
