package br.com.alura.comex.controller.dto;

import br.com.alura.comex.model.Pedido;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoDto {

    private Long id;
    private String nomeCliente;
    private List<Long> idProdutos;
    private List<Integer> quantidades;

    public PedidoDto(Pedido pedido) {
        this.id = pedido.getId();
        this.nomeCliente = pedido.getCliente().getNome();
        this.idProdutos = pedido.getItensDePedido().stream().map(itemDePedido -> itemDePedido.getProduto().getId()).collect(Collectors.toList());
        this.quantidades = pedido.getItensDePedido().stream().map(itemDePedido -> itemDePedido.getQuantidade()).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public List<Long> getIdProdutos() {
        return idProdutos;
    }

    public List<Integer> getQuantidades() {
        return quantidades;
    }

    public static List<PedidoDto> converter(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoDto::new).collect(Collectors.toList());
    }
}
