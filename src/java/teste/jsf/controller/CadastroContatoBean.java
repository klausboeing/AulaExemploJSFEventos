/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.jsf.controller;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import teste.model.Contato;
import teste.model.ContatoDao;

/**
 *
 * @author Klaus Boeing
 */
@ManagedBean
public class CadastroContatoBean {
    
    private Contato contato = new Contato();

    @ManagedProperty("#{param.id}")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @PostConstruct
    public void init(){
        if(id != null){
            try {
                this.contato = new ContatoDao().buscarPorId(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
    
    public String grava(){
        try {
            if(contato.getId() == null){
                new ContatoDao().adiciona(contato);
            }else{
                new ContatoDao().edita(contato);
            }            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return "lista-contatos?faces-redirect=true";
    }
    
    public void emailChanged(ValueChangeEvent event) {
        String oldEmailValue = (String)event.getOldValue();
        String newEmailValue = (String)event.getNewValue();
        
        if (newEmailValue != null && !newEmailValue.equals(oldEmailValue)) {
            try {
                Contato contato = new ContatoDao().buscarPorEmail(newEmailValue);
                Long id = (Long) ((UIInput) event.getComponent().findComponent("id")).getValue();

                if (contato != null && !contato.getId().equals(id) && newEmailValue.equals(contato.getEmail())) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, String.format("Email já cadastrado para o contato %s.", contato.getNome()), null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}