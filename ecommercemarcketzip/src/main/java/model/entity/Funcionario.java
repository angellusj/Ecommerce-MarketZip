package model.entity;

public class Funcionario extends Usuario {
    private String cargo;

    public Funcionario(int idUsuario, String nome, String cpf, String email, String telefone, String senha, String cargo){
        super(idUsuario, nome, cpf, email, telefone, senha);
        this.cargo = cargo;
        
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return super.toString() + "\ncargo: " + cargo;
    }

    
}
