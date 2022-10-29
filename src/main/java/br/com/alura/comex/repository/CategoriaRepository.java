package br.com.alura.comex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.alura.comex.model.Categoria;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
