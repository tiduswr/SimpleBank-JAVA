package model;

import org.json.JSONObject;

public class Usuario implements JSONTransform{
    private String cpf, senha, token;
    private TipoUsuario tipo;

    public Usuario(String cpf, String senha, String token, TipoUsuario tipo) {
        this.cpf = cpf;
        this.senha = senha;
        this.token = token;
        this.tipo = tipo;
    }
    
    public Usuario(){}
    
    public Usuario(String json){
        JSONObject o = new JSONObject(json);
        this.cpf = o.getString("cpf");
        this.senha = o.getString("senha");
        this.token = o.getString("token");
        this.tipo = TipoUsuario.getByInt(o.getInt("tipo"));
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Override
    public JSONObject toJson() {
        JSONObject o = new JSONObject();
        
        o.put("cpf", getCpf());
        o.put("senha", getSenha());
        o.put("token", getToken());
        o.put("tipo", getTipo().getValue());
        
        return o;
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
