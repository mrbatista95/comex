package br.com.alura.comex.controller.dto;

import br.com.alura.comex.model.Pedido;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoDto {

    private Long id;
    private String nomeCliente;
    private List<ItemDePedidoDto> itensDePedidoDto;

    public PedidoDto(Pedido pedido) {
        this.id = pedido.getId();
        this.nomeCliente = pedido.getCliente().getNome();
        this.itensDePedidoDto = pedido.getItensDePedido().stream().map(ItemDePedidoDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public List<ItemDePedidoDto> getItensDePedidoDto() {
        return itensDePedidoDto;
    }

    public static List<PedidoDto> converter(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoDto::new).collect(Collectors.toList());
    }
}
