package br.com.alura.comex.feature.cliente;

import br.com.alura.comex.entity.Cliente;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteResponse {

    private String nome;
    private String cpf;
    private String telefone;
    private String local;

    public ClienteResponse(Cliente cliente) {
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.local = cliente.getCidade() + "/" + cliente.getEstado();

    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getLocal() {
        return local;
    }

    public static List<ClienteResponse> converter(Page<Cliente> clientes) {
        return clientes.stream().map(ClienteResponse::new).collect(Collectors.toList());
    }
}
