package model.entity;

public abstract class Usuario {
    private int idUsuario;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;

    public Usuario(int idUsuario, String nome, String cpf, String email, String telefone, String senha){
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public String toString() {
        return "ID: " + idUsuario + "\nnome: " + nome + "\ncpf:" + cpf + "\nemail:" + email
                + "\ntelefone:" + telefone; // nao faz sentido exibir a senha
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
    
