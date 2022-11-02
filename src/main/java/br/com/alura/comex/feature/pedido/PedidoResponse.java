package br.com.alura.comex.feature.pedido;

import br.com.alura.comex.entity.Pedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoResponse {

    private Long id;
    private String nomeCliente;
    private List<ItemDePedidoResponse> itensDePedidoResponse;

    private BigDecimal precoTotal;

    private BigDecimal precoDesconto;

    public PedidoResponse(Pedido pedido) {
        this.id = pedido.getId();
        this.nomeCliente = pedido.getCliente().getNome();
        this.itensDePedidoResponse = pedido.getItensDePedido().stream().map(ItemDePedidoResponse::new).collect(Collectors.toList());
        this.precoTotal = pedido.getPrecoTotal();
        this.precoDesconto = pedido.getPrecoDesconto();
    }

    public Long getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public List<ItemDePedidoResponse> getItensDePedidoResponse() {
        return itensDePedidoResponse;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public BigDecimal getPrecoDesconto() {
        return precoDesconto;
    }

    public static List<PedidoResponse> converter(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoResponse::new).collect(Collectors.toList());
    }

}
