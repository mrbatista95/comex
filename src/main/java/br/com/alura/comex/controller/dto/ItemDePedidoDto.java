package br.com.alura.comex.controller.dto;

import br.com.alura.comex.model.ItemDePedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDePedidoDto {

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

    public ItemDePedidoDto(ItemDePedido itemDePedido){
        this.nomeProduto = itemDePedido.getProduto().getNome();
        this.quantidade = itemDePedido.getQuantidade();
        this.precoUnitario = itemDePedido.getPrecoUnitario();
        this.precoTotal = itemDePedido.getPrecoTotal();
        this.precoDesconto = itemDePedido.getPrecoDesconto();
    }

    public List<ItemDePedidoDto> converter(List<ItemDePedido> itensDePedido) {
        return itensDePedido.stream().map(ItemDePedidoDto::new).collect(Collectors.toList());
    }
}
