package br.com.alura.comex.repository;

import br.com.alura.comex.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long>, JpaRepository<Cliente, Long> {

}
