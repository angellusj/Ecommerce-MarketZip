package model.entity;

public class ItemDePedido {
    private int idItem;
    private int idProduto;
    private int idPedido;
    private int quantidade;

    public ItemDePedido(int idItem, int idProduto, int idPedido, int quantidade) {
        this.idItem = idItem;
        this.idProduto = idProduto;
        this.idPedido = idPedido;
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ID: " + idItem + "\nID Produto: " + idProduto + "\nID Pedido: " + idPedido 
                + "\nQuantidade: " + quantidade;
    }

    public int getIdItem() {
        return idItem;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
