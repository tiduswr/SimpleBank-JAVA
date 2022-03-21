package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

public class Conta implements JSONTransform{
    private String agencia;
    private String numeroConta;
    private String cpfTitular;
    private Date dtCreation;
    private double saldo;
    private long idConta;
    private boolean active;
    
    public Conta(){}
    
    public Conta(long id, String agencia, String numeroConta, String cpfTitular, boolean active, Date dtCreation){
        this.idConta = id;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.cpfTitular = cpfTitular;
        this.dtCreation = dtCreation;
        this.saldo = 0;
        this.active = active;
    }
    
    public Conta(String json) throws ParseException{
        JSONObject j = new JSONObject(json);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        this.agencia = j.getString("agencia");
        this.numeroConta = j.getString("numeroConta");
        this.cpfTitular = j.getString("cpfTitular");
        this.saldo = j.getDouble("saldo");
        this.dtCreation = sdf.parse(j.getString("dtCreation"));
        this.idConta = j.getLong("idConta");
        this.active = j.getBoolean("active");
    }
    
    //Metodos getters and setters
    public String getAgencia() {
        return agencia;
    }
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }
    public String getNumeroConta() {
        return numeroConta;
    }
    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public void setTitular(String cpf){
        this.cpfTitular = cpf;
    }
    public String getCpfTitular(){
        return this.cpfTitular;
    }
    public Date getDateCreation() {
        return dtCreation;
    }
    public void setDateCreation(Date dtCreation) {
        this.dtCreation = dtCreation;
    }
    public long getIdConta() {
        return idConta;
    }
    public void setIdConta(long idConta) {
        this.idConta = idConta;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    //Transações
    public boolean depositar(double valor){
        try{
            this.saldo = Double.sum(this.saldo, valor);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean sacar(double valor){
        if(valor <= this.getSaldo()){
            this.saldo = Double.sum(this.saldo, valor*(-1));
            return true;
        }else{
            return false;
        }
    }
    
    public boolean transferir(double valor, Conta cc){
        if(sacar(valor)){
           return cc.depositar(valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return agencia + "-" + numeroConta;
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("agencia", getAgencia());
        json.put("numeroConta", getNumeroConta());
        json.put("cpfTitular", getCpfTitular());
        json.put("saldo", getSaldo());
        json.put("idConta", getIdConta());
        json.put("active", this.active);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        json.put("dtCreation", sdf.format(getDateCreation()));
        
        return json;
    }
    
}
