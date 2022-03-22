package ui.models;

import control.Controller;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class TrasacaoTableModel extends AbstractTableModel{
    
    private ArrayList<JSONObject> data;
    private final String[] header = {"Conta Origem", "Conta Destino", "Tipo", "Data", "Valor Movimentado"};
    
    public TrasacaoTableModel(Controller con, String cpf){
        data = new ArrayList<>();
        JSONArray arr = new JSONArray(con.listConta(cpf));
        ArrayList<Long> ids = new ArrayList<>();
        
        arr.forEach(e -> {
            JSONObject o = new JSONObject(e.toString());
            if(o.getBoolean("active")){
                ids.add(o.getLong("idConta"));
            }
        });
        
        long[] idsArr = new long[ids.size()];
        
        for(int i = 0; i < ids.size(); i++){
            idsArr[i] = ids.get(i);
        }
        
        JSONArray transacoes = new JSONArray(con.listTransacao(idsArr));
        transacoes.forEach(t -> {
            JSONObject aux = new JSONObject(t.toString());
            JSONObject oNew = new JSONObject();

            JSONObject cc = aux.getJSONObject("from");
            oNew.put("from", cc.getString("agencia") + "-" + cc.getString("numeroConta"));

            cc = aux.getJSONObject("to");
            oNew.put("to", cc.getString("agencia") + "-" + cc.getString("numeroConta"));

            oNew.put("tipo", aux.getString("tipoDesc"));
            oNew.put("valMovimentado", aux.getDouble("valMovimentado"));
            oNew.put("dtMovimento", aux.getString("dtMovimento"));

            data.add(oNew);
        });
        
    }
    
    public TrasacaoTableModel(Controller con){
        data = new ArrayList<>();
        
        JSONArray transacoes = new JSONArray(con.listTransacao(-1));
        transacoes.forEach(t -> {
            JSONObject aux = new JSONObject(t.toString());
            JSONObject oNew = new JSONObject();
            
            JSONObject cc = aux.getJSONObject("from");
            oNew.put("from", cc.getString("agencia") + "-" + cc.getString("numeroConta"));
            
            cc = aux.getJSONObject("to");
            oNew.put("to", cc.getString("agencia") + "-" + cc.getString("numeroConta"));
            
            oNew.put("tipo", aux.getString("tipoDesc"));
            oNew.put("valMovimentado", aux.getDouble("valMovimentado"));
            oNew.put("dtMovimento", aux.getString("dtMovimento"));

            data.add(oNew);

        });
        
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
    
    public void removeRow(int r){
        if(r != -1){
            this.data.remove(r);
            this.fireTableRowsDeleted(r, r);
        }
    }
    
    public JSONObject getJsonAt(int r){
        return data.get(r);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        JSONObject o = data.get(rowIndex);
        
        if(o != null){
            switch(columnIndex){
                case 0:
                    return o.getString("from");
                case 1:
                    return o.getString("to");
                case 2:
                    return o.getString("tipo");
                case 3:
                    return o.getString("dtMovimento");
                case 4:
                    return o.getDouble("valMovimentado");
            }
        }
        return null;
    }
    
}
