/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.model;

import java.util.Calendar;

/**
 *
 * @author Klaus Boeing
 */
public class Contato {

    private Long id;
    private String nome;
    private String email;
    private String endereco;
    private Calendar dataNascimento;

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String novo) {
        this.nome = novo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String novo) {
        this.email = novo;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String novo) {
        this.endereco = novo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long novo) {
        this.id = novo;
    }

}
