package br.com.alura.comex.feature.pedido;

import br.com.alura.comex.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public Page<PedidosResponse> getPedidos(@PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 5) Pageable pageable) {

        Page<Pedido> pedidos = pedidoService.listPedidos(pageable);
        return PedidosResponse.converter(pedidos);
    }

    @GetMapping("/{id}")
    public PedidoResponse getPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.findPedido(id);
        return new PedidoResponse(pedido);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PedidoResponse> postPedido(@RequestBody @Valid PedidoRequest pedidoRequest, UriComponentsBuilder uriBuilder) {

        Pedido pedido = pedidoService.novoPedido(pedidoRequest);

        URI uri = uriBuilder.path("/api/pedidos/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).body(new PedidoResponse(pedido));
    }




}
