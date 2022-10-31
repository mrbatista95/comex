package br.com.alura.comex.feature.cliente;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.repository.ClienteRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/api/clientes")

@Service
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    @ResponseBody
    public List<ClienteResponse> listClientes(@RequestParam(name = "page", required = true) Integer page, @RequestParam(name = "size", required = true) Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Cliente> clientes = clienteRepository.findAll(pageRequest);
        return ClienteResponse.converter(clientes);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteResponse> postCliente(@RequestBody @Valid ClienteRequest form, UriComponentsBuilder uriBuilder) {
        Cliente cliente = new Cliente();
        cliente.setNome(form.getNome());
        cliente.setCpf(form.getCpf());
        cliente.setTelefone(form.getTelefone());
        cliente.setRua(form.getRua());
        cliente.setNumero(form.getNumero());
        cliente.setComplemento(form.getComplemento());
        cliente.setBairro(form.getBairro());
        cliente.setCidade(form.getCidade());
        cliente.setEstado(form.getEstado());

        clienteRepository.save(cliente);

        URI uri = uriBuilder.path("/api/clientes/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).body(new ClienteResponse(cliente));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClienteResponse> putCliente(@PathVariable Long id, @RequestBody @Valid ClienteRequest form) {
        Optional<Cliente> optional = clienteRepository.findById(id);
        if (optional.isPresent()) {
            Cliente cliente = form.atualizar(id, clienteRepository);
            return ResponseEntity.ok(new ClienteResponse(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ClienteResponse> deleteCliente(@PathVariable Long id) {
        Optional<Cliente> optional = clienteRepository.findById(id);
        if (optional.isPresent()) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
