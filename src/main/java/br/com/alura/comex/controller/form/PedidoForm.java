package br.com.alura.comex.controller.form;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PedidoForm {

    @NotNull
    private Long idCliente;

    @NotNull
    private List<ItemDePedidoForm> itemDePedidoForm;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<ItemDePedidoForm> getItemDePedidoForm() {
        return itemDePedidoForm;
    }

    public void setItemDePedidoForms(List<ItemDePedidoForm> itemDePedidoForm) {
        this.itemDePedidoForm = itemDePedidoForm;
    }
}
