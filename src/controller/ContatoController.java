package controller;

import dao.ContatoDAO;
import java.util.List;
import model.Contato;

public class ContatoController {

    private ContatoDAO dao;

    public ContatoController() {
        dao = new ContatoDAO();
    }
    public boolean adicionar(String nome, String fone){
        Contato contato = new Contato();
        contato.setNome(nome.toUpperCase());
        contato.setFone(fone);
        
        return dao.inset(contato);
    }
    public boolean atualizar(int id, String nome, String fone){
    Contato contato = new Contato();
        contato.setId(id);
        contato.setNome(nome.toUpperCase());
        contato.setFone(fone);        
        return dao.update(contato);
    }
    
    public List<Contato> listar(String filter){
        if(filter == null){
        return dao.selectALL();
        }else{
            return dao.selectFilter(filter);
        }
        
    }
    public boolean remover(int id){
        Contato contato = new Contato();
        contato.setId(id);
        return dao.delete(contato);
    }

   
    
}
