package br.com.alura.comex.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class ItemDePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Pedido pedido;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto produto;

    @Column(nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private TipoDescontoItem tipoDesconto = TipoDescontoItem.NENHUM;

    public ItemDePedido() {
        super();
    }

    public ItemDePedido(Integer quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.precoUnitario = produto.getPrecoUnitario();
    }

    public BigDecimal getValorTotalItem() {
        return this.precoUnitario.multiply(new BigDecimal(this.quantidade));
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public TipoDescontoItem getTipoDesconto() {
        return tipoDesconto;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public void setTipoDesconto(TipoDescontoItem tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public void desconto() {
        BigDecimal total = BigDecimal.ZERO;
        if (this.tipoDesconto == TipoDescontoItem.NENHUM) {
            BigDecimal valor = BigDecimal.ZERO;
            total = this.precoUnitario.multiply(valor);
        }

        if (this.tipoDesconto == TipoDescontoItem.QUANTIDADE) {
            BigDecimal valor = new BigDecimal("0.10");
            total = this.precoUnitario.multiply(valor);
        }
        this.desconto = total;
    }
}