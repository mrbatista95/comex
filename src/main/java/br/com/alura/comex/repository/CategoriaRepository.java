package br.com.alura.comex.repository;

import br.com.alura.comex.model.CategoriaProdutoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.alura.comex.model.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(nativeQuery = true, value = "SELECT c.nome, count(p.id), sum(ip.quantidade) FROM api.categorias c" +
            "JOIN api.produtos p ON p.categoria_id = c.id JOIN api.itens_pedido ip ON ip.produto_id = p.id" +
            "JOIN api.pedidos ON pedidos.id = ip.pedido_id GROUP BY c.nome")
    public List<CategoriaProdutoProjection> listCategoriaProduto();

}
