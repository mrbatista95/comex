package br.com.alura.comex.feature.pedido;

import br.com.alura.comex.entity.*;
import br.com.alura.comex.exception.NotFoundException;
import br.com.alura.comex.exception.UnprocessableEntityException;
import br.com.alura.comex.repository.ClienteRepository;
import br.com.alura.comex.repository.ItemDePedidoRepository;
import br.com.alura.comex.repository.PedidoRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemDePedidoRepository itemDePedidoRepository;


    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                         ProdutoRepository produtoRepository, ItemDePedidoRepository itemDePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.itemDePedidoRepository = itemDePedidoRepository;
    }

    public Pedido novoPedido(PedidoRequest pedidoRequest) {
        Cliente cliente = findCliente(pedidoRequest);

        List<ItemDePedido> itensDePedido = calculaItensDoPedidoEAtualizaEstoque(pedidoRequest);

        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setItensDePedido(itensDePedido);
        pedido.calcularPrecoTotal();

        if (pedidoRepository.countByCliente(cliente) >= 5) {
            pedido.setTipoDesconto(TipoDesconto.FIDELIDADE);
        }


        pedido.desconto();
        pedido.calcularPrecoDesconto();

        pedidoRepository.save(pedido);

        itensDePedido.forEach(itemDePedido -> itemDePedido.setPedido(pedido));

        itemDePedidoRepository.saveAll(itensDePedido);

        return pedido;
    }

    public Page<Pedido> listPedidos(Pageable pageable) {
        Page<Pedido> pedidos = pedidoRepository.findAll(pageable);
        return pedidos;
    }

    public Pedido findPedido(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Pedido com id: %s nao encontrado", id)));
    }

    private List<ItemDePedido> calculaItensDoPedidoEAtualizaEstoque(PedidoRequest pedidoRequest) {
        List<ItemDePedido> itensDePedidos = new ArrayList<>();
        for (ItemDePedidoRequest itemDePedidoRequest : pedidoRequest.getItemDePedidoRequest()) {

            Produto produto = findProduto(itemDePedidoRequest);

            Integer quantidade = itemDePedidoRequest.getQuantidade();
            validaQuantidade(quantidade, produto);

            ItemDePedido itemDePedido = new ItemDePedido(quantidade, produto);

            if (itemDePedido.getQuantidade() >= 10) {
                itemDePedido.setTipoDesconto(TipoDescontoItem.QUANTIDADE);
            }

            itemDePedido.aplicarDesconto();
            itemDePedido.calcularPrecoDesconto();

            itensDePedidos.add(itemDePedido);

            produto.retiraDoEstoque(quantidade);
            produtoRepository.save(produto);
        }

        return itensDePedidos;
    }

    public List<Pedido> listAll() {
        return pedidoRepository.findAll();
    }
    private Cliente findCliente(PedidoRequest pedidoRequest) {
        Long idCliente = pedidoRequest.getIdCliente();
        return clienteRepository.findById(idCliente).orElseThrow(() -> new NotFoundException(String.format("Cliente com id: %s nao encontrado", idCliente)));
    }

    private Produto findProduto(ItemDePedidoRequest itemDePedidoRequest) {
        Long idProduto  = itemDePedidoRequest.getIdProduto();
        return produtoRepository.findById(idProduto).orElseThrow(() -> new NotFoundException(String.format("Produto com id: %s nao encontrado", idProduto)));
    }

    private void validaQuantidade(Integer quantidade, Produto produto) {
        if (quantidade > produto.getQuantidadeEstoque()) {
            String message = String.format("Sem quantidade suficiente em estoque para o produto com id: %s", produto.getId());
            throw new UnprocessableEntityException(message);
        }
    }

}