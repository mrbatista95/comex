package br.com.alura.comex.feature.pedido;

import br.com.alura.comex.entity.*;
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
    public List<PedidoResponse> getPedidos() {
        List<Pedido> pedidos = pedidoService.listAll();
        return PedidoResponse.converter(pedidos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PedidoResponse> postPedido(@RequestBody @Valid PedidoRequest pedidoRequest, UriComponentsBuilder uriBuilder) {

        Pedido pedido = pedidoService.novoPedido(pedidoRequest);

        URI uri = uriBuilder.path("/api/pedidos/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).body(new PedidoResponse(pedido));
    }




}
