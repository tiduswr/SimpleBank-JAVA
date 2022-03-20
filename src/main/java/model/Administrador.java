package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

public class Administrador extends Pessoa{
    private String cargo;
    private Date dtAdmissao;

    public Administrador(long idDatabase, String cpf, String nome, Date dtNascimento, 
            String email, Telefone fone, Endereco endereco, Date dtAdmissao, String cargo) {
        super(idDatabase, cpf, nome, dtNascimento, email, fone, endereco);
        this.dtAdmissao = dtAdmissao;
        this.cargo = cargo;
    }

    public Administrador() {
    }

    public Administrador(String json) throws ParseException {
        super(json);
        JSONObject o = new JSONObject(json);
        
        this.dtAdmissao = new SimpleDateFormat("dd/MM/yyyy").parse(o.getString("dtAdmissao"));
        this.cargo = o.getString("cargo");
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(Date dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    @Override
    public String toString() {
        return super.toString() + "Administrador{" + "cargo=" + cargo + ", dtAdmissao=" + dtAdmissao + '}';
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject pai = super.toJson();
        
        pai.put("cargo", cargo);
        pai.put("dtAdmissao", new SimpleDateFormat("dd/MM/yyyy").format(dtAdmissao));
        
        return pai;
    }
    
}
