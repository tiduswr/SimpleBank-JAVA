package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

public class Conta implements JSONTransform{
    private long agencia;
    private long numeroConta;
    private String cpfTitular;
    private Date dtCreation;
    private double saldo;
    
    public Conta(){}
    
    public Conta(int agencia, int numeroConta, String cpfTitular){
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.cpfTitular = cpfTitular;
        this.dtCreation = new Date();
        this.saldo = 0;
    }
    
    public Conta(String json) throws ParseException{
        JSONObject j = new JSONObject(json);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        this.agencia = j.getLong("agencia");
        this.numeroConta = j.getLong("numeroConta");
        this.cpfTitular = j.getString("cpfTitular");
        this.saldo = j.getDouble("saldo");
        this.dtCreation = sdf.parse(j.getString("dtCreation"));
    }
    
    //Metodos getters and setters
    public long getAgencia() {
        return agencia;
    }
    public void setAgencia(long agencia) {
        this.agencia = agencia;
    }
    public long getNumeroConta() {
        return numeroConta;
    }
    public void setNumeroConta(long numeroConta) {
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
        return "Conta{" + "agencia=" + agencia + ", numeroConta=" + numeroConta + 
                ", cpfTitular=" + cpfTitular + ", dtCreation=" + dtCreation + ", saldo=" + saldo + '}';
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("agencia", getAgencia());
        json.put("numeroConta", getNumeroConta());
        json.put("cpfTitular", getCpfTitular());
        json.put("saldo", getSaldo());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        json.put("dtCreation", sdf.format(getDateCreation()));
        
        return json;
    }
    
}
