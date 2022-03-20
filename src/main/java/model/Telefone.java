package model;

import org.json.JSONObject;

public class Telefone implements JSONTransform{
    private int ddd;
    private String numero;

    public Telefone(int ddd, String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }
    
    public Telefone(){}
    
    public Telefone(String json){
        JSONObject j = new JSONObject(json);
        
        this.ddd = j.getInt("ddd");
        this.numero = j.getString("numero");
    }
    
    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getNumeroFormatado(){
        return "(" + String.valueOf(ddd) + ")" + numero.substring(0, 0) + 
                numero.substring(1, 5) + "-" + numero.substring(5,numero.length());
    }

    @Override
    public String toString() {
        return "Telefone{" + "ddd=" + ddd + ", numero=" + numero + '}';
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("ddd", ddd);
        json.put("numero", numero);
        
        return json;
    }
    
}
