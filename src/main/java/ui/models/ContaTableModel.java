package ui.models;

import control.Controller;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class ContaTableModel extends AbstractTableModel{
    
    private ArrayList<JSONObject> data;
    private final String[] header = {"Agencia", "Numero", "Data Criação", "Saldo"};
    private String cpf;
    private Controller con;
    
    public ContaTableModel(Controller con, String cpfUser){
        data = new ArrayList<>();
        JSONArray arr = new JSONArray(con.listConta(cpfUser));
        
        this.con = con;
        this.cpf = cpfUser;
        arr.forEach(e -> {
            JSONObject o = new JSONObject(e.toString());
            if(o.getBoolean("active")){
                JSONObject oNew = new JSONObject();

                oNew.put("agencia", o.getString("agencia"));
                oNew.put("numero", o.getString("numeroConta"));
                oNew.put("dtCriacao", o.getString("dtCreation"));
                oNew.put("saldo", o.getDouble("saldo"));

                data.add(oNew);
            }
        });
        
    }
    
    public void reload(){
        data = new ArrayList<>();
        JSONArray arr = new JSONArray(con.listConta(cpf));
        
        arr.forEach(e -> {
            JSONObject o = new JSONObject(e.toString());
            if(o.getBoolean("active")){
                JSONObject oNew = new JSONObject();

                oNew.put("agencia", o.getString("agencia"));
                oNew.put("numero", o.getString("numeroConta"));
                oNew.put("dtCriacao", o.getString("dtCreation"));
                oNew.put("saldo", o.getDouble("saldo"));

                data.add(oNew);
            }
        });
        this.fireTableDataChanged();
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
    public String getColumnName(int c) {
        return header[c];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        JSONObject o = data.get(rowIndex);
        
        if(o != null){
            switch(columnIndex){
                case 0:
                    return o.getString("agencia");
                case 1:
                    return o.getString("numero");
                case 2:
                    return o.getString("dtCriacao");
                case 3:
                    return o.getDouble("saldo");
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
