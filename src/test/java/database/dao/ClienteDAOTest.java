package database.dao;

import database.CreateDataBase;
import database.DatabaseConnect;
import database.SQLiteConnection;
import java.util.ArrayList;
import java.util.Date;
import model.Cliente;
import model.Endereco;
import model.Telefone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClienteDAOTest {
    
    private ClienteDAO cdao;
    private DatabaseConnect c;
    private Class cl = ClienteDAO.class;
    
    public ClienteDAOTest() {
        
        System.out.println("# Initializing tests...");
        System.out.println("    - Connecting on database...");
        
        c = new SQLiteConnection("db/dbTeste.db");
        c.connect();
        CreateDataBase.createDataBaseAndTables(c);
        
        cdao = new ClienteDAO(c.getConnection());
        System.out.println("    - Connected!!");
        
    }
    
    @Test
    public void testCreate() {
        System.out.println("    - Trying to persist " + cl.getName() + " on database...");
        Cliente o = new Cliente();
        
        o.setCpf("999.999.999-99");
        o.setDtCadastro(new Date());
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
                
        Assertions.assertTrue(cdao.create(o));
        
        o.setCpf("777.777.777-77");
        
        Assertions.assertTrue(cdao.create(o));
        
        System.out.println("    - Data saved in database!");
    }

    @Test
    public void testRead() {
        System.out.println("    - Trying to read " + cl.getName() + " on database...");
        
        Cliente cli = cdao.read("999.999.999-99");
        Assertions.assertNotNull(cli);
        System.out.println("Cliente name -> " + cli.getNome());
        
        System.out.println("    - Data read in database!");
    }


    @Test
    public void testUpdate() {
        System.out.println("    - Trying to update " + cl.getName() + " on database...");
        
        Cliente cl = cdao.read("999.999.999-99");
        System.out.println("Cliente old cpf -> " + cl.getCpf());
        cl.setCpf("888.888.888-88");
        Assertions.assertTrue(cdao.update(cl));
        cl = cdao.read("888.888.888-88");
        System.out.println("Cliente new cpf -> " + cl.getCpf());
        
        System.out.println("    - Data updated in database!");
    }
    

    @Test
    public void testList() {
        
        System.out.println("    - Trying to list " + cl.getName() + " entrys on database...");
        
        ArrayList<Cliente> l = cdao.list();
        
        l.forEach(e -> {
            System.out.println("id:" + String.valueOf(e.getIdDatabase()) + ", nome:" + e.getNome() + ", cpf:" + e.getCpf());
        });
        
        System.out.println("    - Entrys listed in database!");
        
    }
    
    @Test
    public void testDelete() {
        System.out.println("    - Trying to delete " + cl.getName() + " on database...");
        
        Assertions.assertTrue(cdao.delete("888.888.888-88"));
        Assertions.assertTrue(cdao.delete("777.777.777-77"));
        
        System.out.println("    - Data deleted in database!");
        this.c.closeConnection();
        System.out.println("# Database Disconected!!");
    }
    
}
