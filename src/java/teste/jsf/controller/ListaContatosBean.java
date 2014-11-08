/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.jsf.controller;

import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import teste.model.Contato;
import teste.model.ContatoDao;

/**
 *
 * @author Klaus Boeing
 */
@ManagedBean
public class ListaContatosBean {
    
    public List<Contato> getContatos() throws SQLException{
        return new ContatoDao().getLista();
    }
    
    public void remove(Contato contato){
        try {
            new ContatoDao().deleta(contato);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
