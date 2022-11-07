package br.com.alura.comex.feature.pedido;

import br.com.alura.comex.entity.Pedido;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PedidosResponse {

    private LocalDate data;
    private BigDecimal precoTotal;
    private BigDecimal descontoItens;
    private BigDecimal descontoPedido;
    private Integer quantidadeVendido;
    private Long idCliente;
    private String nomeCliente;

    public PedidosResponse(Pedido pedido) {
        this.data = pedido.getData();
        this.precoTotal = pedido.getPrecoDesconto();
        this.descontoItens = pedido.getItensDePedido().stream().map(o -> o.getDesconto()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.descontoPedido = pedido.getDesconto();
        this.idCliente = pedido.getCliente().getId();
        this.nomeCliente = pedido.getCliente().getNome();
        this.quantidadeVendido = pedido.getItensDePedido().stream().map(o -> o.getQuantidade()).reduce(0, Integer::sum);
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public BigDecimal getDescontoItens() {
        return descontoItens;
    }

    public BigDecimal getDescontoPedido() {
        return descontoPedido;
    }

    public Integer getQuantidadeVendido() {
        return quantidadeVendido;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public static Page<PedidosResponse> converter(Page<Pedido> pedidos) {
        return pedidos.map(PedidosResponse::new);
    }

}
