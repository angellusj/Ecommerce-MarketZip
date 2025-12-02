package model.entity;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class Pedido {
    private int idPedido;
    private Date data;
    private Boolean finalizar;
    private double valorTotal;
    private Cliente cliente;
    private List<ItemDePedido> itens;
    
public Pedido(int idPedido, Date data, Boolean finalizar, double valorTotal, Cliente cliente) {
        this.idPedido = idPedido;
        this.data = data;
        this.finalizar = finalizar;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
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

public List<ItemDePedido> getItens() {
    return itens;
}

public void setItens(List<ItemDePedido> itens) {
    this.itens = itens;
}

public void adicionarItem(ItemDePedido item) {
    this.itens.add(item);
}

@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("╔═══════════════════════════════════╗\n");
    sb.append("║ Pedido #").append(idPedido).append("\n");
    sb.append("║ Data: ").append(data).append("\n");
    sb.append("║ Status: ").append(finalizar ? "Finalizado" : "Ativo").append("\n");
    sb.append("║ Valor Total: R$ ").append(String.format("%.2f", valorTotal)).append("\n");
    if (cliente != null) {
        sb.append("║ Cliente: ").append(cliente.getNome()).append("\n");
    }
    sb.append("╠═══════════════════════════════════╣\n");
    
    if (itens != null && !itens.isEmpty()) {
        sb.append("║ PRODUTOS:\n");
        for (ItemDePedido item : itens) {
            sb.append("║  • ID Produto: ").append(item.getIdProduto());
            sb.append(" | Quantidade: ").append(item.getQuantidade()).append("\n");
        }
    } else {
        sb.append("║ Nenhum produto no pedido\n");
    }
    
    sb.append("╚═══════════════════════════════════╝");
    return sb.toString();
}
}
