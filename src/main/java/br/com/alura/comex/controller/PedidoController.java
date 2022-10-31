package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.PedidoDto;
import br.com.alura.comex.controller.form.ItemDePedidoForm;
import br.com.alura.comex.controller.form.PedidoForm;
import br.com.alura.comex.model.*;
import br.com.alura.comex.repository.ClienteRepository;
import br.com.alura.comex.repository.ItemDePedidoRepository;
import br.com.alura.comex.repository.PedidoRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")

@Service
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemDePedidoRepository itemDePedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository, ItemDePedidoRepository itemDePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.itemDePedidoRepository = itemDePedidoRepository;
    }

    @GetMapping
    public List<PedidoDto> getPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return PedidoDto.converter(pedidos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PedidoDto> postPedido(@RequestBody @Valid PedidoForm form, UriComponentsBuilder uriBuilder) {
        Pedido pedido = new Pedido();

        //Optional<Cliente> cliente = clienteRepository.findById(form.getIdCliente());
        Cliente cliente = clienteRepository.findById(form.getIdCliente()).orElseThrow(RuntimeException::new);
        pedido.setCliente(cliente);

        for (ItemDePedidoForm itemDePedidoForm : form.getItemDePedidoForm()) {
            Produto produto = produtoRepository.findById(itemDePedidoForm.getIdProduto()).orElseThrow(RuntimeException::new);

            ItemDePedido itemDePedido = new ItemDePedido(itemDePedidoForm.getQuantidade(), produto);

            if (itemDePedido.getQuantidade() >= 10) {
                itemDePedido.setTipoDesconto(TipoDescontoItem.QUANTIDADE);
            }

            itemDePedido.desconto();
            itemDePedido.calcularPrecoDesconto();

            pedido.getItensDePedido().add(itemDePedido);
            itemDePedido.setPedido(pedido);

            itemDePedidoRepository.save(itemDePedido);
        }

        if (pedidoRepository.countByCliente(cliente) >= 5) {
            pedido.setTipoDesconto(TipoDesconto.FIDELIDADE);
        }

        pedido.desconto();
        pedido.calcularPrecoTotal();
        pedido.calcularPrecoDesconto();

        pedidoRepository.save(pedido);

        URI uri = uriBuilder.path("/api/pedidos/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).body(new PedidoDto(pedido));
    }




}
