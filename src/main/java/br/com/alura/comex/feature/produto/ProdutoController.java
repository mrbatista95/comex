package br.com.alura.comex.feature.produto;

import br.com.alura.comex.entity.Categoria;
import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.entity.Produto;
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
    public List<ProdutoResponse> getProdutos(@RequestParam(name = "page", required = true) Integer page, @RequestParam(name = "size", required = true) Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Produto> produtos = produtoRepository.findAll(pageRequest);
        return ProdutoResponse.converter(produtos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoResponse> postProduto(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder) {
        Produto produto = new Produto();
        produto.setNome(produtoRequest.getNome());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setQuantidadeEstoque(produtoRequest.getQuantidadeEstoque());
        produto.setPrecoUnitario(produtoRequest.getPrecoUnitario());

        Optional<Categoria> categoria = categoriaRepository.findById(produtoRequest.getIdCategoria());
        if (categoria.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        produto.setCategoria(categoria.get());

        produtoRepository.save(produto);

        URI uri = uriBuilder.path("/api/produtos/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProdutoResponse(produto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoResponse> putProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequest produtoRequest) {
        Optional<Produto> optional = produtoRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Produto produto = produtoRequest.atualizar(id, produtoRepository);
        return ResponseEntity.ok(new ProdutoResponse(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoResponse> deleteProduto(@PathVariable Long id) {
        Optional<Produto> optional = produtoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
