package br.com.alura.comex.controller.dto;

import br.com.alura.comex.model.Produto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal precoUnitario;
    private Integer quantidadeEstoque;

    private Long idCategoria;

    private String nomeCategoria;

    public ProdutoDto(Produto produto) {
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

    public static List<ProdutoDto> converter(Page<Produto> produtos) {
        return produtos.stream().map(ProdutoDto::new).collect(Collectors.toList());
    }
}
