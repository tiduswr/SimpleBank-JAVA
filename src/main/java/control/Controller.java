package control;

import database.*;
import database.dao.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import org.json.JSONArray;
import util.*;

public class Controller {
    private DatabaseConnect con;
    private Usuario logged = null;
    
    public Controller(){
        con = new SQLiteConnection();
        con.connect();
        CreateDataBase.createDataBaseAndTables(con);
    }
    
    public String getUsuarioLogado(){
        return logged.toJson().toString();
    }
    
    public void disconnectDB(){
        if(con != null) con.closeConnection();
    }
    
    public boolean login(String cpf, String senha){
        UsuarioDAO dao = new UsuarioDAO(this.con.getConnection());
        Usuario response = dao.read(cpf);
        
        if(response != null){
            Usuario teste = new Usuario(-1, response.getCpf(), senha, response.getTipo(), response.isActive(), true);
            if(teste.getSenha().equals(response.getSenha())){
                this.logged = response;
                return true;
            }
        }
        
        return false;
        
    }
    
    public String solicitarConta(String cpf){
        ContaDAO dao = new ContaDAO(this.con.getConnection());
        Conta cc = new Conta();
        
        String lastId = String.valueOf(dao.getLastId());
        String agencia = "001";
        String leftCpfKeys = cpf.substring(0, 3);
        String rightCpfKeys = cpf.substring(9,11);
        String numeroConta = leftCpfKeys + "." + lastId + "." + rightCpfKeys;
                
        cc.setActive(false);
        cc.setAgencia(agencia);
        cc.setDateCreation(new Date());
        cc.setIdConta(0);
        cc.setNumeroConta(numeroConta);
        cc.setSaldo(0);
        cc.setTitular(cpf);
        
        if(dao.create(cc)){
            return new Message("Solicitação Realizada!", "A sua conta foi enviada para aprovação!", 
                        "noterror", "none").toJson().toString();
        }else{
            return new Message("ERRO!", "Um erro ocorreu na solicitação de Conta!", 
                        "error", "none").toJson().toString();
        }
    }
    
    private JSONArray filterErrors(JSONArray arr){
        return new JSONArray(util.DataValidator.removeNotErrorEntrys(arr.toString()));
    }
    
    public String insertAdministrador(String json){
        
        JSONArray errors = new JSONArray(DataValidator.adminIsValid(json));
        if(DataValidator.containsMessageErrors(errors.toString())){
            return filterErrors(errors).toString();
        }
        
        try {
            Administrador adm = new Administrador(json);
            AdministradorDAO dao = new AdministradorDAO(this.con.getConnection());
            
            if(dao.create(adm)){
                return new Message("Concluido!", "Administrador Inserido!", "done", "none").toJson().toString();
            }else{
                return new Message("Ja cadastrado!", "Esse registro ja esta cadastrado no banco de dados!", 
                        "error", "none").toJson().toString();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Message("Erro de Conversão de Data!", "Erro ao converter as datas do banco de dados!", 
                    "error", "date").toJson().toString();
        }
        
    }
    
    public String insertCliente(String json){
        
        JSONArray errors = new JSONArray(DataValidator.clienteIsValid(json));
        if(DataValidator.containsMessageErrors(errors.toString())){
            return filterErrors(errors).toString();
        }
        
        try {
            Cliente cliente = new Cliente(json);
            ClienteDAO dao = new ClienteDAO(this.con.getConnection());
            
            if(dao.create(cliente)){
                return new Message("Concluido!", "Cliente Inserido!", "done", "none").toJson().toString();
            }else{
                return new Message("Ja cadastrado!", "Esse registro ja esta cadastrado no banco de dados!", 
                        "error", "none").toJson().toString();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Message("Erro de Conversão de Data!", "Erro ao converter as datas do banco de dados!", 
                    "error", "date").toJson().toString();
        }
        
    }
    
    public String insertUsuario(String json){
        
        JSONArray errors = new JSONArray(DataValidator.usuarioIsValid(json));
        if(DataValidator.containsMessageErrors(errors.toString())){
            return filterErrors(errors).toString();
        }
        
        Usuario user = new Usuario(json, true);
        UsuarioDAO dao = new UsuarioDAO(this.con.getConnection());

        if(dao.create(user)){
            return new Message("Concluido!", "Usuario Inserido!", "done", "none").toJson().toString();
        }else{
            return new Message("Ja cadastrado!", "Esse registro ja esta cadastrado no banco de dados!", 
                        "error", "none").toJson().toString();
        }
        
    }
    
    public String insertConta(String json){
        
        JSONArray errors = new JSONArray(DataValidator.contaIsValid(json));
        if(DataValidator.containsMessageErrors(errors.toString())){
            return filterErrors(errors).toString();
        }
        
        try {
            Conta conta = new Conta(json);
            ContaDAO dao = new ContaDAO(this.con.getConnection());

            if(dao.create(conta)){
                return new Message("Concluido!", "Conta Inserido!", "done", "none").toJson().toString();
            }else{
                return new Message("Ja cadastrado!", "Esse registro ja esta cadastrado no banco de dados!", 
                        "error", "none").toJson().toString();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Message("Erro de Conversão de Data!", "Erro ao converter as datas do banco de dados!", 
                    "error", "date").toJson().toString();
        }
        
    }
    
    public String deleteAdministrador(String cpf){
        AdministradorDAO dao = new AdministradorDAO(this.con.getConnection());
        
        if(dao.delete(cpf)){
            return new Message("Concluido!", "Administrador excluido!", "done", "none").toJson().toString();
        }else{
            return new Message("Não Excluido!", "Erro desconhecido ocorreu!", "error", "none").toJson().toString();
        }
    }
    
    public String deleteCliente(String cpf){
        ClienteDAO dao = new ClienteDAO(this.con.getConnection());
        
        if(dao.delete(cpf)){
            return new Message("Concluido!", "Cliente excluido!", "done", "none").toJson().toString();
        }else{
            return new Message("Não Excluido!", "Erro desconhecido ocorreu!", "error", "none").toJson().toString();
        }
    }
    
    public String deleteUsuario(String cpf){
        UsuarioDAO dao = new UsuarioDAO(this.con.getConnection());
        
        if(dao.delete(cpf)){
            return new Message("Concluido!", "Usuario excluido!", "done", "none").toJson().toString();
        }else{
            return new Message("Não Excluido!", "Erro desconhecido ocorreu!", "error", "none").toJson().toString();
        }
    }
    
    public String deleteConta(String contacompleta){
        ContaDAO dao = new ContaDAO(this.con.getConnection());
        
        if(dao.delete(contacompleta)){
            return new Message("Concluido!", "Conta excluido!", "done", "none").toJson().toString();
        }else{
            return new Message("Não Excluido!", "Erro desconhecido ocorreu!", "error", "none").toJson().toString();
        }
    }
    
    public String readAdministrador(String cpf){
        AdministradorDAO dao = new AdministradorDAO(this.con.getConnection());
        Administrador response = dao.read(cpf);
        if(response == null){
            return null;
        }else{
            return response.toJson().toString();
        }
    }
    
    public String readCliente(String cpf){
        ClienteDAO dao = new ClienteDAO(this.con.getConnection());
        Cliente response = dao.read(cpf);
        if(response == null){
            return null;
        }else{
            return response.toJson().toString();
        }
    }
    
    public String readUsuario(String cpf){
        UsuarioDAO dao = new UsuarioDAO(this.con.getConnection());
        Usuario response = dao.read(cpf);
        if(response == null){
            return null;
        }else{
            return response.toJson().toString();
        }
    }
    
    public String readConta(String contacompleta){
        ContaDAO dao = new ContaDAO(this.con.getConnection());
        Conta response = dao.read(contacompleta);
        if(response == null){
            return null;
        }else{
            return response.toJson().toString();
        }
    }
    
    public String listAdministrador(){
        AdministradorDAO dao = new AdministradorDAO(this.con.getConnection());
        ArrayList<Administrador> l = dao.list();
        
        if(l != null){
            JSONArray jList = new JSONArray();
            l.forEach(e -> {
                jList.put(e.toJson());
            });
            return jList.toString();
        }else{
            return null;
        }
    }
    
    public String listCliente(){
        ClienteDAO dao = new ClienteDAO(this.con.getConnection());
        ArrayList<Cliente> l = dao.list();
        
        if(l != null){
            JSONArray jList = new JSONArray();
            l.forEach(e -> {
                jList.put(e.toJson());
            });
            return jList.toString();
        }else{
            return null;
        }
    }
    
    public String listUsuario(){
        UsuarioDAO dao = new UsuarioDAO(this.con.getConnection());
        ArrayList<Usuario> l = dao.list();
        
        if(l != null){
            JSONArray jList = new JSONArray();
            l.forEach(e -> {
                jList.put(e.toJson());
            });
            return jList.toString();
        }else{
            return null;
        }
    }
    
    public String listConta(String cpf){
        ContaDAO dao = new ContaDAO(this.con.getConnection());
        ArrayList<Conta> l;
        if(cpf == null){
            l = dao.list();
        }else{
            l = dao.list(cpf);
        }
        
        if(l != null){
            JSONArray jList = new JSONArray();
            l.forEach(e -> {
                jList.put(e.toJson());
            });
            return jList.toString();
        }else{
            return null;
        }
    }
    
    public String listTransacao(long... idsOrigem){
        TransacaoDAO dao = new TransacaoDAO(this.con.getConnection());
        ArrayList<Transacao> l;
        if(idsOrigem[0] == -1){
            l = dao.list();
        }else{
            l = dao.list(idsOrigem);
        }
        
        if(l != null){
            JSONArray jList = new JSONArray();
            l.forEach(e -> {
                jList.put(e.toJson());
            });
            return jList.toString();
        }else{
            return null;
        }
    }
    
    public String updateAdministrador(String json){
        
        JSONArray errors = new JSONArray(DataValidator.adminIsValid(json));
        if(DataValidator.containsMessageErrors(errors.toString())){
            return filterErrors(errors).toString();
        }
        
        try {
            Administrador adm = new Administrador(json);
            AdministradorDAO dao = new AdministradorDAO(this.con.getConnection());
            
            if(dao.update(adm)){
                return new Message("Concluido!", "Administrador Atualizado!", "done", "none").toJson().toString();
            }else{
                return new Message("Erro ao atualizar!", "Não foi possivel atualizar!", 
                        "error", "none").toJson().toString();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Message("Erro de Conversão de Data!", "Erro ao converter as datas do banco de dados!", 
                    "error", "date").toJson().toString();
        }
        
    }
    
    public String updateCliente(String json){
        
        JSONArray errors = new JSONArray(DataValidator.clienteIsValid(json));
        if(DataValidator.containsMessageErrors(errors.toString())){
            return filterErrors(errors).toString();
        }
        
        try {
            Cliente cli = new Cliente(json);
            ClienteDAO dao = new ClienteDAO(this.con.getConnection());
            
            if(dao.update(cli)){
                return new Message("Concluido!", "Cliente Atualizado!", "done", "none").toJson().toString();
            }else{
                return new Message("Erro ao atualizar!", "Não foi possivel atualizar!", 
                        "error", "none").toJson().toString();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Message("Erro de Conversão de Data!", "Erro ao converter as datas do banco de dados!", 
                    "error", "date").toJson().toString();
        }
        
    }
    
    public String updateUsuario(String json, boolean criptSenha){
        
        JSONArray errors = new JSONArray(DataValidator.usuarioIsValid(json));
        if(DataValidator.containsMessageErrors(errors.toString())){
            return filterErrors(errors).toString();
        }
        
        Usuario user = new Usuario(json, criptSenha);
        UsuarioDAO dao = new UsuarioDAO(this.con.getConnection());

        if(dao.update(user)){
            return new Message("Concluido!", "Usuario Atualizado!", "done", "none").toJson().toString();
        }else{
            return new Message("Erro ao atualizar!", "Não foi possivel atualizar!", 
                    "error", "none").toJson().toString();
        }
        
    }
    
    public String updateConta(String json){
        
        JSONArray errors = new JSONArray(DataValidator.contaIsValid(json));
        if(DataValidator.containsMessageErrors(errors.toString())){
            return filterErrors(errors).toString();
        }
        
        try {
            Conta conta = new Conta(json);
            ContaDAO dao = new ContaDAO(this.con.getConnection());

            if(dao.update(conta)){
                return new Message("Concluido!", "Conta Atualizada!", "done", "none").toJson().toString();
            }else{
                return new Message("Ja cadastrado!", "Esse registro ja esta cadastrado no banco de dados!", 
                        "error", "none").toJson().toString();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Message("Erro de Conversão de Data!", "Erro ao converter as datas do banco de dados!", 
                    "error", "date").toJson().toString();
        }
        
    }
    
    public String depositarConta(String contacompleta, Double value){
        ContaDAO dao = new ContaDAO(this.con.getConnection());
        Conta cc = dao.read(contacompleta);
        
        if(cc != null){
            if(cc.depositar(value) && dao.update(cc)){
                
                TransacaoDAO daot = new TransacaoDAO(this.con.getConnection());
                Transacao t = new Transacao();
                
                t.setDtMovimento(new Date());
                t.setFrom(cc);
                t.setTo(cc);
                t.setIdTransacao(0);
                t.setValMovimentado(value);
                t.setTipo(Transacao.TipoTransacao.DEPOSITO);
                
                daot.create(t);
                
                return new Message("Deposito concluido!", "O depósito foi feito sem erros!", 
                        "noterror", "none").toJson().toString(); 
            }else{
                return new Message("Deposito falhou!", "Um erro desconhecido ocorreu!", 
                        "error", "none").toJson().toString();
            }
        }else{
            return new Message("Deposito falhou!", "A conta para depósito não foi encontrado!", 
                        "error", "none").toJson().toString(); 
        }
        
    }
    
    public String sacarConta(String contacompleta, Double value){
        ContaDAO dao = new ContaDAO(this.con.getConnection());
        Conta cc = dao.read(contacompleta);
        
        if(cc != null){
            if(cc.sacar(value) && dao.update(cc)){
                TransacaoDAO daot = new TransacaoDAO(this.con.getConnection());
                Transacao t = new Transacao();
                
                t.setDtMovimento(new Date());
                t.setFrom(cc);
                t.setTo(cc);
                t.setIdTransacao(0);
                t.setValMovimentado(value);
                t.setTipo(Transacao.TipoTransacao.SAQUE);
                
                daot.create(t);
                return new Message("Saque concluido!", "O depósito foi feito sem erros!", 
                        "noterror", "none").toJson().toString(); 
            }else{
                return new Message("Saque falhou!", "Você não tem saldo suficiente!", 
                        "error", "none").toJson().toString();
            }
        }else{
            return new Message("Saque falhou!", "A conta para depósito não foi encontrado!", 
                        "error", "none").toJson().toString(); 
        }
        
    }
    
    public String transferirDinheiroConta(String contacompletaOrigem, String contacompletaDestino, Double value){
        ContaDAO dao = new ContaDAO(this.con.getConnection());
        Conta co = dao.read(contacompletaOrigem);
        Conta cd = dao.read(contacompletaDestino);
        
        if(co != null && cd != null){
            if(co.transferir(value, cd)){
                if(dao.update(co) && dao.update(cd)){
                    TransacaoDAO daot = new TransacaoDAO(this.con.getConnection());
                    Transacao t = new Transacao();

                    t.setDtMovimento(new Date());
                    t.setFrom(co);
                    t.setTo(cd);
                    t.setIdTransacao(0);
                    t.setValMovimentado(value);
                    t.setTipo(Transacao.TipoTransacao.TRANSFERENCIA);

                    daot.create(t);
                    return new Message("Transferencia concluida!", "Os valores foram transferidos entre as contas!", 
                        "noterror", "none").toJson().toString();
                }else{
                    return new Message("Transferencia falhou!", "Erro ao salvar no banco de dados!", 
                        "error", "none").toJson().toString();
                }
            }else{
                return new Message("Transferencia falhou!", "Você não tem saldo suficiente!", 
                        "error", "none").toJson().toString();
            }
        }else{
            return new Message("Transferência falhou!", "As contas para transferencia não foram encontradas!", 
                        "error", "none").toJson().toString(); 
        }
        
    }
    
}
