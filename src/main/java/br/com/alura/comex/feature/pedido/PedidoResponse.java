package br.com.alura.comex.feature.pedido;

import br.com.alura.comex.entity.Pedido;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoResponse {

    private Long id;
    private String nomeCliente;
    private List<ItemDePedidoResponse> itensDePedidoResponse;

    private BigDecimal precoTotal;

    private BigDecimal precoDesconto;

    private LocalDate data;

    public PedidoResponse(Pedido pedido) {
        this.id = pedido.getId();
        this.nomeCliente = pedido.getCliente().getNome();
        this.data = pedido.getData();
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

    public LocalDate getData() {
        return data;
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

    public static Page<PedidoResponse> converter(Page<Pedido> pedidos) {
        return pedidos.map(PedidoResponse::new);
    }

}
