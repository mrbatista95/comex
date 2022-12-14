package br.com.alura.comex.feature.categoria;

import br.com.alura.comex.entity.Categoria;
import br.com.alura.comex.repository.CategoriaRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoriaRequest {

    @NotEmpty @NotNull @Length(min = 2)
     private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria atualizar(Long id, CategoriaRepository categoriaRepository) {
        Categoria categoria = categoriaRepository.getReferenceById(id);

        categoria.setNome(this.nome);

        return categoria;
    }
}
