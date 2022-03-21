package ui.models;

import control.Controller;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class SolicitacaoTableModel extends AbstractTableModel{
    
    private ArrayList<JSONObject> data;
    private final String[] header = {"CPF", "Nome", "Data Sol.", "Tipo Sol."};
    
    public SolicitacaoTableModel(Controller con){
        JSONArray arr = new JSONArray(con.listUsuario());
        data = new ArrayList<>();
        
        arr.forEach(e -> {
            JSONObject o = new JSONObject(e.toString());
            if(!o.getBoolean("active")){
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
            if(!o.getBoolean("active")){
                String aux = con.readAdministrador(o.getString("cpf"));
                if(aux == null){
                    aux = con.readCliente(o.getString("cpf"));
                }
                
                JSONObject pessoa = new JSONObject(aux);
                JSONObject oNew = new JSONObject();
                
                oNew.put("cpf", o.getString("cpfTitular"));
                oNew.put("dtSol", o.getString("dtCreation"));
                oNew.put("tipoDesc", o.getString("Conta Bancaria"));
                oNew.put("nome", pessoa.getString("nome"));
                
                data.add(oNew);
            }
        });
        
    }
    
    @Override
    public String getColumnName(int c) {
        return header[c];
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        JSONObject o = data.get(rowIndex);
        
        if(o != null){
            switch(columnIndex){
                case 0:
                    return o.getString("cpf");
                case 1:
                    return o.getString("nome");
                case 2:
                    return o.getString("dtSol");
                case 3:
                    return o.getString("tipoDesc");
            }
        }
        return null;
    }
    
    public void removeRow(int r){
        if(r != -1){
            this.data.remove(r);
            this.fireTableRowsDeleted(r, r);
        }
    }
    
    public JSONObject getJsonAt(int r){
        return data.get(r);
    }
    
}
