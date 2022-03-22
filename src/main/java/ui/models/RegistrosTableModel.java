package ui.models;

import control.Controller;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RegistrosTableModel extends SolicitacaoTableModel{

    public RegistrosTableModel(Controller con) {
        super();
        JSONArray arr = new JSONArray(con.listUsuario());
        data = new ArrayList<>();
        
        arr.forEach(e -> {
            JSONObject o = new JSONObject(e.toString());
            if(o.getBoolean("active")){
                data.add(o);
            }
        });
        
        arr = new JSONArray(con.listAdministrador());
        arr.forEach(e -> {
            JSONObject o = new JSONObject(e.toString());
            data.forEach(el -> {
                if(el.getString("cpf").equals(o.getString("cpf"))){
                    el.put("nome", o.getString("nome"));
                    el.put("dtSol", o.getString("dtAdmissao"));
                }
            });
        });
        
        arr = new JSONArray(con.listCliente());
        arr.forEach(e -> {
            JSONObject o = new JSONObject(e.toString());
            data.forEach(el -> {
                if(el.getString("cpf").equals(o.getString("cpf"))){
                    el.put("nome", o.getString("nome"));
                    el.put("dtSol", o.getString("dtCadastro"));
                }
            });
        });
        
        arr = new JSONArray(con.listConta(null));
        arr.forEach(e -> {
            JSONObject o = new JSONObject(e.toString());
            if(o.getBoolean("active")){
                String aux = con.readAdministrador(o.getString("cpfTitular"));
                if(aux == null){
                    aux = con.readCliente(o.getString("cpfTitular"));
                }
                
                JSONObject pessoa = new JSONObject(aux);
                JSONObject oNew = new JSONObject();
                
                oNew.put("cpf", o.getString("cpfTitular"));
                oNew.put("dtSol", o.getString("dtCreation"));
                oNew.put("tipoDesc", "Conta Bancaria");
                oNew.put("agencia", o.getString("agencia"));
                oNew.put("numeroConta", o.getString("numeroConta"));
                oNew.put("nome", pessoa.getString("nome"));
                
                data.add(oNew);
            }
        });
    }

    
}
