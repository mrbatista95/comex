package br.com.alura.comex.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data = LocalDate.now();

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cliente cliente;

    private BigDecimal desconto = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private TipoDesconto tipoDesconto = TipoDesconto.NENHUM;

	@OneToMany(mappedBy = "pedido")
	private List<ItemDePedido> itensDePedido = new ArrayList<ItemDePedido>();

    public Pedido() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public TipoDesconto getTipoDesconto() {
		return tipoDesconto;
	}

	public void setTipoDesconto(TipoDesconto tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public List<ItemDePedido> getItensDePedido() {
		return itensDePedido;
	}

	public void setItensDePedido(List<ItemDePedido> itensDePedido) {
		this.itensDePedido = itensDePedido;
	}

	public void desconto() {
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal valor = BigDecimal.ONE;
		if (this.tipoDesconto == TipoDesconto.NENHUM) {
			valor = BigDecimal.ZERO;
		}

		if (this.tipoDesconto == TipoDesconto.FIDELIDADE) {
			valor = new BigDecimal("0.05");
		}
		total = itensDePedido.stream().map(itensDePedido -> itensDePedido.getPrecoUnitario()).reduce(BigDecimal::add).get().multiply(valor);
		this.desconto = total;
	}
}