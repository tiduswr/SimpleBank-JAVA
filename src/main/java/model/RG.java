package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class RG implements JSONTransform{
    private String numero, og, uf;
    private Date dtEmissao;

    public RG(String numero, String og, String uf, Date dtEmissao) {
        this.numero = numero;
        this.og = og;
        this.uf = uf;
        this.dtEmissao = dtEmissao;
    }
    
    public RG(){}
    
    public RG(String json){
        JSONObject j = new JSONObject(json);
        
        this.numero = j.getString("numero");
        this.og = j.getString("og");
        this.uf = j.getString("uf");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.dtEmissao = sdf.parse(j.getString("dtEmissao"));
        } catch (ParseException ex) {
            Logger.getLogger(RG.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOg() {
        return og;
    }

    public void setOg(String og) {
        this.og = og;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Date getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(Date dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    @Override
    public String toString() {
        return "RG{" + "numero=" + numero + ", og=" + og + ", uf=" + uf + ", dtEmissao=" + dtEmissao + '}';
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("numero", numero);
        json.put("og", og);
        json.put("uf", uf);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        json.put("dtEmissao", sdf.format(dtEmissao));
        
        return json;
    }
    
}
