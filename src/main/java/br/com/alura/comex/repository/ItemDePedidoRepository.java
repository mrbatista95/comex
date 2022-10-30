package br.com.alura.comex.repository;

import br.com.alura.comex.model.ItemDePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDePedidoRepository extends JpaRepository<ItemDePedido, Long> {
}
