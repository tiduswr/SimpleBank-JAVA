package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

public class Cliente extends Pessoa{
    private Date dtCadastro;

    public Cliente(long idDatabase, String cpf, String nome, Date dtNascimento, 
            String email, Telefone fone, Endereco endereco, Date dtCadastro) {
        super(idDatabase, cpf, nome, dtNascimento, email, fone, endereco);
        this.dtCadastro = dtCadastro;
    }

    public Cliente() {
    }

    public Cliente(String json) throws ParseException {
        super(json);
        JSONObject o = new JSONObject(json);
        this.dtCadastro = new SimpleDateFormat("dd/MM/yyyy").parse(o.getString("dtCadastro"));
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject pai = super.toJson();
        
        pai.put("dtCadastro", new SimpleDateFormat("dd/MM/yyyy").format(dtCadastro));
        
        return pai;
    }
    
}
