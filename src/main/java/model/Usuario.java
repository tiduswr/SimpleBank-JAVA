package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class Usuario implements JSONTransform{
    private String cpf, senha;
    private TipoUsuario tipo;
    private boolean active;
    private long id;
    
    public Usuario(long id, String cpf, String senha, TipoUsuario tipo, boolean active, boolean cript) {
        this.id = id;
        this.cpf = cpf;
        if(cript) 
            this.senha = criptografar(senha);
        else
            this.senha = senha;
        this.tipo = tipo;
        this.active = active;
    }
    
    public Usuario(){}
    
    public Usuario(String json, boolean cript){
        JSONObject o = new JSONObject(json);
        this.id = o.getLong("idUser");
        this.cpf = o.getString("cpf");
        if(cript)
            this.senha = criptografar(o.getString("senha"));
        else
            this.senha = o.getString("senha");
        this.tipo = TipoUsuario.getByInt(o.getInt("tipo"));
        this.active = o.getBoolean("active");
    }
    
    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = criptografar(senha);
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public JSONObject toJson() {
        JSONObject o = new JSONObject();
        
        o.put("cpf", getCpf());
        o.put("senha", getSenha());
        o.put("tipo", getTipo().getValue());
        o.put("idUser", getId());
        o.put("active", isActive());
                
        return o;
    }
    
    private String criptografar(String s){
       MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public enum TipoUsuario implements JSONTransform{
        CLIENTE(0){

            @Override
            public String toString(){
              return "Cliente";
            }

            @Override
            public JSONObject toJson() {
                JSONObject json = new JSONObject();

                json.put("code", getValue());
                json.put("nome", toString());

                return json;
            }

        },
        ADM(1){

            @Override
            public String toString(){
              return "Administrador";
            }

            @Override
            public JSONObject toJson() {
                JSONObject json = new JSONObject();

                json.put("code", getValue());
                json.put("nome", toString());

                return json;
            }

        };

        private final int value;

        private TipoUsuario(int i){
            this.value = i;
        }

        public int getValue(){
            return this.value;
        }

        public static TipoUsuario getByInt(int i){
            switch(i){
                case 0:
                    return TipoUsuario.CLIENTE;
                case 1:
                    return TipoUsuario.ADM;
                default:
                    return null;
            }
        }

        public static int getQtdTipos(){
            return 2;
        }
    }
    
}
