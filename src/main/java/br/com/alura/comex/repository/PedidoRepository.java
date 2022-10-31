package br.com.alura.comex.repository;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Long countByCliente(Cliente cliente);

}
