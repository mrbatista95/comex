package br.com.alura.comex.feature.pedido;

import br.com.alura.comex.entity.Pedido;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoResponse {

    private Long id;
    private String nomeCliente;
    private List<ItemDePedidoResponse> itensDePedidoDto;

    public PedidoResponse(Pedido pedido) {
        this.id = pedido.getId();
        this.nomeCliente = pedido.getCliente().getNome();
        this.itensDePedidoDto = pedido.getItensDePedido().stream().map(ItemDePedidoResponse::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public List<ItemDePedidoResponse> getItensDePedidoDto() {
        return itensDePedidoDto;
    }

    public static List<PedidoResponse> converter(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoResponse::new).collect(Collectors.toList());
    }
}
