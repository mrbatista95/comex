package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.ProdutoDto;
import br.com.alura.comex.controller.form.ProdutoForm;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.ProdutoRepository;
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
@RequestMapping("/api/produtos")

@Service
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository){
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<ProdutoDto> getProdutos(@RequestParam(name = "page", required = true) Integer page, @RequestParam(name = "size", required = true) Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Produto> produtos = produtoRepository.findAll(pageRequest);
        return ProdutoDto.converter(produtos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDto> postProduto(@RequestBody @Valid ProdutoForm form, UriComponentsBuilder uriBuilder) {
        Produto produto = new Produto();
        produto.setNome(form.getNome());
        produto.setDescricao(form.getDescricao());
        produto.setQuantidadeEstoque(form.getQuantidadeEstoque());
        produto.setPrecoUnitario(form.getPrecoUnitario());
        Optional<Categoria> categoria = categoriaRepository.findById(form.getIdCategoria());
        produto.setCategoria(categoria.get());

        produtoRepository.save(produto);

        URI uri = uriBuilder.path("/api/produtos/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProdutoDto(produto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoDto> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoForm form) {
        Optional<Produto> optional = produtoRepository.findById(id);
        if (optional.isPresent()) {
            Produto produto = form.atualizar(id, produtoRepository);
            return ResponseEntity.ok(new ProdutoDto(produto));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoDto> remover(@PathVariable Long id) {
        Optional<Produto> optional = produtoRepository.findById(id);
        if (optional.isPresent()) {
            produtoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
