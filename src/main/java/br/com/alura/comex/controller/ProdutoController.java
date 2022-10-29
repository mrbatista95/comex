package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.ProdutoDto;
import br.com.alura.comex.controller.form.ProdutoForm;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.repository.CategoriaRepository;
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
    public List<ProdutoDto> getProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
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
}
