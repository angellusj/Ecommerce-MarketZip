package model.entity;

public class Cliente extends Usuario{
    private String endereco;

    public Cliente(int idUsuario, String nome, String cpf, String email, String telefone, String senha, String endereco){
        super(idUsuario, nome, cpf, email, telefone, senha);
        this.endereco = endereco;
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
