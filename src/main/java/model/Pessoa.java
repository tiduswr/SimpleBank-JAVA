package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public abstract class Pessoa implements JSONTransform{
    private long idDatabase;
    private String cpf, nome;
    private Date dtNascimento;
    private String email;
    private Telefone fone;
    private Endereco endereco;

    public Pessoa(long idDatabase, String cpf, String nome, Date dtNascimento, String email, 
            Telefone fone, Endereco endereco) {
        this.idDatabase = idDatabase;
        this.cpf = cpf;
        this.nome = nome;
        this.dtNascimento = dtNascimento;
        this.email = email;
        this.fone = fone;
        this.endereco = endereco;
    }
    
    public Pessoa(){}
    
    public Pessoa(String json){
        JSONObject j = new JSONObject(json);
        
        this.idDatabase = j.getLong("id");
        this.cpf = j.getString("cpf");
        this.nome = j.getString("nome");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.dtNascimento = sdf.parse(j.getString("dtNascimento"));
        } catch (ParseException ex) {
            Logger.getLogger(Pessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.email = j.getString("email");
        this.fone = new Telefone(j.get("telefone").toString());
        this.endereco = new Endereco(j.get("endereco").toString());
    
    }    
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Telefone getFone() {
        return fone;
    }

    public void setFone(Telefone fone) {
        this.fone = fone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public long getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(long idDatabase) {
        this.idDatabase = idDatabase;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "idDatabase=" + idDatabase + ", cpf=" + cpf + ", nome=" + nome + 
                ", dtNascimento=" + dtNascimento + ", email=" + email + ", fone=" + fone + 
                ", endereco=" + endereco + '}';
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("id", idDatabase);
        json.put("cpf", cpf);
        json.put("nome", nome);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        json.put("dtNascimento", sdf.format(dtNascimento));
        
        json.put("email", email);
        json.put("telefone", fone.toJson());
        json.put("endereco", endereco.toJson());
        
        return json;
    }
    
}