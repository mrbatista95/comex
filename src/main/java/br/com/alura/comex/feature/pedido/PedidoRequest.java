package br.com.alura.comex.feature.pedido;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PedidoRequest {

    @NotNull
    private Long idCliente;

    @NotNull
    private List<ItemDePedidoRequest> itemDePedidoRequest;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<ItemDePedidoRequest> getItemDePedidoForm() {
        return itemDePedidoRequest;
    }

    public void setItemDePedidoForms(List<ItemDePedidoRequest> itemDePedidoRequest) {
        this.itemDePedidoRequest = itemDePedidoRequest;
    }
}
