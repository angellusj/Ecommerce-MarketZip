package model.entity;

public class Cliente extends Usuario{
    private int idCliente;
    private String endereco;

    public Cliente(int idUsuario, String nome, String cpf, String email, String telefone, String senha, String endereco){
        super(idUsuario, nome, cpf, email, telefone, senha);
        this.endereco = endereco;
    }

    public Cliente(int idCliente, int idUsuario, String nome, String cpf, String email, String telefone, String senha, String endereco){
        super(idUsuario, nome, cpf, email, telefone, senha);
        this.idCliente = idCliente;
        this.endereco = endereco;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString(){
        return super.toString() + "\nEndereço: " + endereco;
    }
}
