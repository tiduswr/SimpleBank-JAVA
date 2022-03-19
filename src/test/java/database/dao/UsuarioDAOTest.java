package database.dao;

import database.CreateDataBase;
import database.DatabaseConnect;
import database.SQLiteConnection;
import java.util.ArrayList;
import java.util.Date;
import model.Administrador;
import model.Endereco;
import model.Telefone;
import model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioDAOTest {
    
    private UsuarioDAO dao;
    private DatabaseConnect c;
    private Class cl = UsuarioDAO.class;
    
    public UsuarioDAOTest() {
        
        System.out.println("# Initializing tests...");
        System.out.println("    - Connecting on database...");
        
        c = new SQLiteConnection("db/dbTeste.db");
        c.connect();
        CreateDataBase.createDataBaseAndTables(c);
        
        dao = new UsuarioDAO(c.getConnection());
        System.out.println("    - Connected!!");
        
    }
    
    @Test
    public void testCreate() {
        System.out.println("    - Trying to persist " + cl.getName() + " on database...");
        
        AdministradorDAO daoAdm = new AdministradorDAO(c.getConnection());        
        Administrador o = new Administrador();
        
        o.setCpf("999.999.999-99");
        o.setDtAdmissao(new Date());
        o.setCargo("Gerente");
        o.setDtNascimento(new Date());
        o.setEmail("harllem@gmail.com");
        o.setIdDatabase(0);
        o.setNome("Harllem");

        Endereco e = new Endereco();
        e.setBairro("Novo Horizonte");
        e.setCidade("Patos");
        e.setEstado("PB");
        e.setNumCasa(182);
        e.setRua("Manoel Mauricio de Oliveira");

        Telefone tel = new Telefone();
        tel.setDdd(83);
        tel.setNumero("3421-3421");

        o.setEndereco(e);
        o.setFone(tel);
                
        Assertions.assertTrue(daoAdm.create(o));
        
        o.setCpf("777.777.777-77");
        
        Assertions.assertTrue(daoAdm.create(o));
        
        o = daoAdm.read(o.getCpf());
        Usuario user = new Usuario(o.getIdDatabase(), o.getCpf(), "admLife", Usuario.TipoUsuario.ADM);
        Assertions.assertTrue(dao.create(user));
        
        o = daoAdm.read("999.999.999-99");
        user = new Usuario(o.getIdDatabase(), o.getCpf(), "admLife", Usuario.TipoUsuario.CLIENTE);
        
        Assertions.assertTrue(dao.create(user));
        System.out.println("    - Data saved in database!");
    }

    @Test
    public void testRead() {
        System.out.println("    - Trying to read " + cl.getName() + " on database...");
        
        Usuario user = dao.read("999.999.999-99");
        Assertions.assertNotNull(cl);
        System.out.println("Usuario cpf -> " + user.getCpf() + ": " + user.getTipo().toString());
        
        System.out.println("    - Data read in database!");
    }

    
    public void testUpdate() {
        System.out.println("    - Trying to update " + cl.getName() + " on database...");
        
        Usuario user = dao.read("999.999.999-99");
        System.out.println("Usuario old Tipo -> " + user.getTipo().toString());
        user.setTipo(Usuario.TipoUsuario.ADM);
        Assertions.assertTrue(dao.update(user));
        user = dao.read(user.getCpf());
        System.out.println("Usuario new Tipo -> " + user.getTipo().toString());
        
        System.out.println("    - Data updated in database!");
    }

    @Test
    public void testList() {
        System.out.println("    - Trying to list " + cl.getName() + " entrys on database...");
        
        ArrayList<Usuario> l = dao.list();
        
        l.forEach(e -> {
            System.out.println("id:" + String.valueOf(e.getId()) + ", cpf:" + e.getCpf() + ", tipo:" + e.getTipo().toString());
        });
        
        System.out.println("    - Entrys listed in database!");
    }
    
    @Test
    public void testDelete() {
        System.out.println("    - Trying to delete " + cl.getName() + " on database...");
        
        AdministradorDAO daoAdm = new AdministradorDAO(c.getConnection());
        Assertions.assertTrue(dao.delete("999.999.999-99"));
        Assertions.assertTrue(dao.delete("777.777.777-77"));
        Assertions.assertTrue(daoAdm.delete("999.999.999-99"));
        Assertions.assertTrue(daoAdm.delete("777.777.777-77"));
        
        System.out.println("    - Data deleted in database!");
        this.c.closeConnection();
        System.out.println("# Database Disconected!!");
    }
    
}
