package br.com.alura.comex.feature.categoria;

import br.com.alura.comex.entity.Categoria;
import br.com.alura.comex.entity.StatusCategoria;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriaResponse {

    private Long id;
    private String nome;
    private StatusCategoria status;

    public CategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.status = categoria.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public StatusCategoria getStatus() {
        return status;
    }

    public static List<CategoriaResponse> converter(List<Categoria> categorias) {
        return categorias.stream().map(CategoriaResponse::new).collect(Collectors.toList());
    }
}
