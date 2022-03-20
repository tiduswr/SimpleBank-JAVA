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
import model.Transacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransacaoDAOTest {
    
    private TransacaoDAO dao;
    private DatabaseConnect c;
    private Class cl = TransacaoDAO.class;
    
    public TransacaoDAOTest() {
        System.out.println("# Initializing tests...");
        System.out.println("    - Connecting on database...");
        
        c = new SQLiteConnection("db/dbTeste.db");
        c.connect();
        CreateDataBase.createDataBaseAndTables(c);
        
        dao = new TransacaoDAO(c.getConnection());
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
        
        ContaDAO ccDao = new ContaDAO(c.getConnection());
        Conta cc = new Conta();
        cc.setAgencia("555");
        cc.setNumeroConta("334422");
        cc.setIdConta(-1);
        cc.setDateCreation(new Date());
        cc.setSaldo(1000f);
        cc.setTitular("999.999.999-99");
        
        Assertions.assertTrue(ccDao.create(cc));
        
        //Simulando um depósito
        Transacao tt = new Transacao();
        
        tt.setFrom(cc);
        tt.setTo(cc);
        tt.setIdTransacao(-1);
        tt.setDtMovimento(new Date());
        tt.setTipo(Transacao.TipoTransacao.DEPOSITO);
        tt.setValMovimentado(1000f);
        
        Assertions.assertTrue(dao.create(tt));
        
        //Simulando um saque
        
        tt.setTipo(Transacao.TipoTransacao.SAQUE);
        
        Assertions.assertTrue(dao.create(tt));
        
        System.out.println("    - Data saved in database!");
    }

    @Test
    public void testRead() {
        System.out.println("    - Trying to read " + cl.getName() + " on database...");
        
        Transacao tra = dao.read(dao.list().get(1).getIdTransacao());
        Assertions.assertNotNull(tra);
        System.out.println("Transacao tipo -> " + tra.getTipo().toString() + "; "
                + "Conta origem numero: " + tra.getFrom().toString() + "; Conta destino numero: " + tra.getTo().toString());
        
        System.out.println("    - Data read in database!");
    }

    @Test
    public void testUpdate() {
        System.out.println("    - The class " + cl.getName() + " dont have a update method!");
    }

    @Test
    public void testList() {
        System.out.println("    - Trying to list " + cl.getName() + " entrys on database...");
        
        ArrayList<Transacao> l = dao.list();
        
        l.forEach(e -> {
            System.out.println("id:" + String.valueOf(e.getIdTransacao())
                    + "; Conta origem numero: " + e.getFrom().toString() 
                    + "; Conta destino numero: " + e.getTo().toString());
        });
        
        System.out.println("    - Entrys listed in database!");
    }
    
    @Test
    public void testDelete() {
        System.out.println("    - Trying to delete " + cl.getName() + " on database...");
        
        ClienteDAO daoCli = new ClienteDAO(c.getConnection());
        ContaDAO ccDao = new ContaDAO(c.getConnection());
        
        ArrayList<Transacao> l = dao.list();
        
        l.forEach(e -> {
            Assertions.assertTrue(dao.delete(e.getIdTransacao()));
        });
        
        Assertions.assertTrue(ccDao.delete("555-334422"));
        Assertions.assertTrue(daoCli.delete("999.999.999-99"));
        
        System.out.println("    - Data deleted in database!");
        this.c.closeConnection();
        System.out.println("# Database Disconected!!");
    }
    
}
