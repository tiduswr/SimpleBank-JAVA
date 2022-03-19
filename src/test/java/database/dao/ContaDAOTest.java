package database.dao;

import database.CreateDataBase;
import database.DatabaseConnect;
import database.SQLiteConnection;
import java.util.ArrayList;
import java.util.Date;
import model.Cliente;
import model.Conta;
import model.Endereco;
import model.Telefone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContaDAOTest {
    
    private ContaDAO dao;
    private DatabaseConnect c;
    private Class cl = ContaDAO.class;
    
    public ContaDAOTest() {
        System.out.println("# Initializing tests...");
        System.out.println("    - Connecting on database...");
        
        c = new SQLiteConnection("db/dbTeste.db");
        c.connect();
        CreateDataBase.createDataBaseAndTables(c);
        
        dao = new ContaDAO(c.getConnection());
        System.out.println("    - Connected!!");
    }

    @Test
    public void testCreate() {
        System.out.println("    - Trying to persist " + cl.getName() + " on database...");
        
        ClienteDAO daoCliente = new ClienteDAO(c.getConnection());        
        Cliente o = new Cliente();
        
        o.setCpf("999.999.999-99");
        o.setDtCadastro(new Date());
        o.setActive(true);
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
                
        Assertions.assertTrue(daoCliente.create(o));
        
        Conta cc = new Conta();
        cc.setAgencia("555");
        cc.setNumeroConta("334422");
        cc.setIdConta(-1);
        cc.setDateCreation(new Date());
        cc.setSaldo(1000f);
        cc.setTitular("999.999.999-99");
        
        Assertions.assertTrue(dao.create(cc));
        
        cc.setNumeroConta("112233");
        
        Assertions.assertTrue(dao.create(cc));
        
        System.out.println("    - Data saved in database!");
    }

    
    @Test
    public void testRead() {
        System.out.println("    - Trying to read " + cl.getName() + " on database...");
        
        Conta cc = dao.read("555-334422");
        Assertions.assertNotNull(cl);
        System.out.println("Conta cpf -> " + cc.getCpfTitular() + "; Conta numero: " + cc.getAgencia() + "-" + cc.getNumeroConta());
        
        System.out.println("    - Data read in database!");
    }

    @Test
    public void testUpdate() {
        System.out.println("    - Trying to update " + cl.getName() + " on database...");
        
        Conta cc = dao.read("555-334422");
        System.out.println("Conta old numero -> " + cc.getAgencia() + "-" + cc.getNumeroConta());
        cc.setNumeroConta("224433");
        Assertions.assertTrue(dao.update(cc));
        cc = dao.read(cc.getAgencia() + "-" + cc.getNumeroConta());
        System.out.println("Conta new numero -> " + cc.getAgencia() + "-" + cc.getNumeroConta());
        
        System.out.println("    - Data updated in database!");
    }

    @Test
    public void testList() {
        System.out.println("    - Trying to list " + cl.getName() + " entrys on database...");
        
        ArrayList<Conta> l = dao.list();
        
        l.forEach(e -> {
            System.out.println("id:" + String.valueOf(e.getIdConta()) + ", cpf:" + e.getCpfTitular() 
                    + ", conta:" + e.getAgencia() + "-" + e.getNumeroConta());
        });
        
        System.out.println("    - Entrys listed in database!");
    }
    
    @Test
    public void testDelete() {
        System.out.println("    - Trying to delete " + cl.getName() + " on database...");
        
        ClienteDAO daoCli = new ClienteDAO(c.getConnection());
        Assertions.assertTrue(dao.delete("555-224433"));
        Assertions.assertTrue(dao.delete("555-112233"));
        Assertions.assertTrue(daoCli.delete("999.999.999-99"));
        
        System.out.println("    - Data deleted in database!");
        this.c.closeConnection();
        System.out.println("# Database Disconected!!");
    }
    
}
