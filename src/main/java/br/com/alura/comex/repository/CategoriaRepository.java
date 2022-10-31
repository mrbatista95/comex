package br.com.alura.comex.repository;

import br.com.alura.comex.entity.CategoriaProdutoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.alura.comex.entity.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(nativeQuery = true, value = """
            SELECT
             	c.nome AS nomeCategoria,
                sum(ip.quantidade) AS quantidadeProdutosVendidos,
                sum(ip.preco_unitario * ip.quantidade) AS montanteVendido
            FROM api.categorias c
                JOIN api.produtos p ON p.categoria_id = c.id
                JOIN api.itens_pedido ip ON ip.produto_id = p.id
                JOIN api.pedidos ON pedidos.id = ip.pedido_id
            GROUP BY categoria_id
            """)
    public List<CategoriaProdutoProjection> listCategoriaProduto();

}
