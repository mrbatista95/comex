package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.CategoriaDto;
import br.com.alura.comex.controller.form.CategoriaForm;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.CategoriaProdutoProjection;
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
    public List<CategoriaDto> listar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return CategoriaDto.converter(categorias);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaDto> postCategoria(@RequestBody @Valid CategoriaForm form, UriComponentsBuilder uriBuilder) {
        Categoria categoria = new Categoria();
        categoria.setNome(form.getNome());
        categoriaRepository.save(categoria);

        URI uri = uriBuilder.path("/api/categorias/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoriaDto> atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaForm form) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isPresent()) {
            Categoria categoria = form.atualizar(id, categoriaRepository);
            return ResponseEntity.ok(new CategoriaDto(categoria));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoriaDto> remover(@PathVariable Long id) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isPresent()) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pedidos")
    public ResponseEntity<List<CategoriaProdutoProjection>> listarPedidos() {
        return new ResponseEntity<>(categoriaRepository.listCategoriaProduto(), HttpStatus.OK);
    }
}
