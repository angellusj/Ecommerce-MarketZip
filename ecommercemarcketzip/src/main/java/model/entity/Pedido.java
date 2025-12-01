package model.entity;
import java.sql.Date;

public class Pedido {
    private int idPedido;
    private Date data;
    private Boolean finalizar;
    private double valorTotal;
    private Cliente cliente;
    
public Pedido(int idPedido, Date data, Boolean finalizar, double valorTotal, Cliente cliente) {
        this.idPedido = idPedido;
        this.data = data;
        this.finalizar = finalizar;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
 }

public int getIdPedido() {
    return idPedido;
}

public void setIdPedido(int idPedido) {
    this.idPedido = idPedido;
}

public Date getData() {
    return data;
}

public void setData(Date data) {
    this.data = data;
}

public Boolean getFinalizar() {
    return finalizar;
}

public void setFinalizar(Boolean finalizar) {
    this.finalizar = finalizar;
}

public double getValorTotal() {
    return valorTotal;
}

public void setValorTotal(double valorTotal) {
    this.valorTotal = valorTotal;
} 

public Cliente getCliente() {
    return cliente;
}

public void setCliente(Cliente cliente) {
    this.cliente = cliente;
}

@Override
public String toString() {
    return "Pedido idPedido=" + idPedido + ", data=" + data + ", finalizar=" + finalizar + ", valorTotal=" + valorTotal;
}
}
