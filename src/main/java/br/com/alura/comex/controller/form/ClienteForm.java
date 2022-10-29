package br.com.alura.comex.controller.form;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.repository.ClienteRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ClienteForm {

    @NotEmpty @NotNull @Length(min = 2)
    private String nome;

    @NotEmpty @NotNull
    private String cpf;

    @NotEmpty @NotNull
    private String telefone;

    @NotEmpty @NotNull
    private String rua;

    @NotEmpty @NotNull
    private String numero;

    private String complemento;

    @NotEmpty @NotNull
    private String bairro;

    @NotEmpty @NotNull
    private String cidade;

    @NotEmpty @NotNull
    private String estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente atualizar(Long id, ClienteRepository clienteRepository) {
        Cliente cliente = clienteRepository.getReferenceById(id);

        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        cliente.setTelefone(this.telefone);
        cliente.setRua(this.rua);
        cliente.setNumero(this.numero);
        cliente.setComplemento(this.complemento);
        cliente.setBairro(this.bairro);
        cliente.setCidade(this.cidade);
        cliente.setEstado(this.estado);

        return cliente;
    }
}
