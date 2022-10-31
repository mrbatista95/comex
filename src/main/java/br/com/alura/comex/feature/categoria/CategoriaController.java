package br.com.alura.comex.feature.categoria;

import br.com.alura.comex.entity.Categoria;
import br.com.alura.comex.entity.CategoriaProdutoProjection;
import br.com.alura.comex.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/categorias")

@Service
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }


    @GetMapping
    public List<CategoriaResponse> listCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return CategoriaResponse.converter(categorias);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaResponse> postCategoria(@RequestBody @Valid CategoriaRequest categoriaRequest, UriComponentsBuilder uriBuilder) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequest.getNome());
        categoriaRepository.save(categoria);

        URI uri = uriBuilder.path("/api/categorias/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).body(new CategoriaResponse(categoria));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoriaResponse> putCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaRequest categoriaRequest) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isPresent()) {
            Categoria categoria = categoriaRequest.atualizar(id, categoriaRepository);
            return ResponseEntity.ok(new CategoriaResponse(categoria));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoriaResponse> deleteCategoria(@PathVariable Long id) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isPresent()) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pedidos")
    public ResponseEntity<List<CategoriaProdutoProjection>> listPedidos() {
        return new ResponseEntity<>(categoriaRepository.listCategoriaProduto(), HttpStatus.OK);
    }
}
