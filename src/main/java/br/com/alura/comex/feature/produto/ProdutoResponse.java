package br.com.alura.comex.feature.produto;

import br.com.alura.comex.entity.Produto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoResponse {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal precoUnitario;
    private Integer quantidadeEstoque;

    private Long idCategoria;

    private String nomeCategoria;

    public ProdutoResponse(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.precoUnitario = produto.getPrecoUnitario();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.idCategoria = produto.getCategoria().getId();
        this.nomeCategoria = produto.getCategoria().getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public static List<ProdutoResponse> converter(Page<Produto> produtos) {
        return produtos.stream().map(ProdutoResponse::new).collect(Collectors.toList());
    }
}
