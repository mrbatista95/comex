package br.com.alura.comex.feature.pedido;

import br.com.alura.comex.entity.ItemDePedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDePedidoResponse {

    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;

    private BigDecimal precoDesconto;

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public BigDecimal getPrecoDesconto() {
        return precoDesconto;
    }

    public ItemDePedidoResponse(ItemDePedido itemDePedido){
        this.nomeProduto = itemDePedido.getProduto().getNome();
        this.quantidade = itemDePedido.getQuantidade();
        this.precoUnitario = itemDePedido.getPrecoUnitario();
        this.precoTotal = itemDePedido.getPrecoTotal();
        this.precoDesconto = itemDePedido.getPrecoDesconto();
    }

    public List<ItemDePedidoResponse> converter(List<ItemDePedido> itensDePedido) {
        return itensDePedido.stream().map(ItemDePedidoResponse::new).collect(Collectors.toList());
    }
}
