package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public abstract class Pessoa implements JSONTransform{
    private long idDatabase;
    private String cpf, nome, escolaridade, profissao;
    private RG rg;
    private Date dtNascimento;
    private String email;
    private Telefone fone;
    private Endereco endereco;

    public Pessoa(long idDatabase, String cpf, String nome, String escolaridade, String profissao, RG rg, 
                        Date dtNascimento, String email, Telefone fone, Endereco endereco) {
        this.idDatabase = idDatabase;
        this.cpf = cpf;
        this.nome = nome;
        this.escolaridade = escolaridade;
        this.profissao = profissao;
        this.rg = rg;
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
        this.escolaridade = j.getString("escolaridade");
        this.profissao = j.getString("profissao");
        this.rg = new RG(j.get("rg").toString());
        
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

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public RG getRg() {
        return rg;
    }

    public void setRg(RG rg) {
        this.rg = rg;
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
                ", escolaridade=" + escolaridade + ", profissao=" + profissao + ", rg=" + rg + 
                ", dtNascimento=" + dtNascimento + ", email=" + email + ", fone=" + fone + 
                ", endereco=" + endereco + '}';
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("id", idDatabase);
        json.put("cpf", cpf);
        json.put("nome", nome);
        json.put("escolaridade", escolaridade);
        json.put("profissao", profissao);
        json.put("rg", rg.toJson());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        json.put("dtNascimento", sdf.format(dtNascimento));
        
        json.put("email", email);
        json.put("telefone", fone.toJson());
        json.put("endereco", endereco.toJson());
        
        return json;
    }
    
}