package database.dao;

import database.CreateDataBase;
import database.DatabaseConnect;
import database.SQLiteConnection;
import java.util.ArrayList;
import java.util.Date;
import model.Administrador;
import model.Endereco;
import model.Telefone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdministradorDAOTest {
    
    private AdministradorDAO dao;
    private DatabaseConnect c;
    private Class cl = AdministradorDAO.class;
    
    public AdministradorDAOTest() {
        
        System.out.println("\n\n# Initializing tests of " + cl.getName() + "...");
        System.out.println("    - Connecting on database...");
        
        c = new SQLiteConnection("db/dbTeste.db");
        c.connect();
        CreateDataBase.createDataBaseAndTables(c);
        
        dao = new AdministradorDAO(c.getConnection());
        System.out.println("    - Connected!!");
        
    }
    
    @Test
    public void testCreate() {
        System.out.println("    - Trying to persist " + cl.getName() + " on database...");
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
                
        Assertions.assertTrue(dao.create(o));
        
        o.setCpf("777.777.777-77");
        
        Assertions.assertTrue(dao.create(o));
        
        System.out.println("    - Data saved in database!");
    }

    @Test
    public void testRead() {
        System.out.println("    - Trying to read " + cl.getName() + " on database...");
        
        Administrador adm = dao.read("999.999.999-99");
        Assertions.assertNotNull(adm);
        System.out.println("Administrador name -> " + adm.getNome());
        
        System.out.println("    - Data read in database!");
    }


    @Test
    public void testUpdate() {
        System.out.println("    - Trying to update " + cl.getName() + " on database...");
        
        Administrador adm = dao.read("999.999.999-99");
        System.out.println("Administrador old cpf -> " + adm.getCpf());
        adm.setCpf("888.888.888-88");
        Assertions.assertTrue(dao.update(adm));
        adm = dao.read("888.888.888-88");
        System.out.println("Administrador new cpf -> " + adm.getCpf());
        
        System.out.println("    - Data updated in database!");
    }
    

    @Test
    public void testList() {
        
        System.out.println("    - Trying to list " + cl.getName() + " entrys on database...");
        
        ArrayList<Administrador> l = dao.list();
        
        l.forEach(e -> {
            System.out.println("id:" + String.valueOf(e.getIdDatabase()) + ", nome:" + e.getNome() + ", cpf:" + e.getCpf());
        });
        
        System.out.println("    - Entrys listed in database!");
        
    }
    
    @Test
    public void testDelete() {
        System.out.println("    - Trying to delete " + cl.getName() + " on database...");
        
        Assertions.assertTrue(dao.delete("888.888.888-88"));
        Assertions.assertTrue(dao.delete("777.777.777-77"));
        
        System.out.println("    - Data deleted in database!");
        this.c.closeConnection();
        System.out.println("\n\n# End of tests of " + cl.getName() + "!");
    }
    
}
