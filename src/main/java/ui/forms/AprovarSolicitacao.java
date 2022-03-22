package ui.forms;

import control.Controller;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.MenuCentral;
import ui.components.CustomButton;
import ui.components.CustomTable;
import ui.components.Notification;
import ui.models.SolicitacaoTableModel;

public class AprovarSolicitacao extends javax.swing.JPanel {
    private Controller con;
    private SolicitacaoTableModel modelBusca;
    
    public AprovarSolicitacao(Controller con) {
        initComponents();
        
        this.con = con;
        modelBusca = new SolicitacaoTableModel(con);
        this.tblSolicitacoes.setModel(modelBusca);
        CustomTable.setBasicScrollConfigurations(tblScroll);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tblScroll = new javax.swing.JScrollPane();
        tblSolicitacoes = new ui.components.CustomTable();
        formTitle = new javax.swing.JLabel();
        btAprova = new ui.components.CustomButton();
        btRecusa = new ui.components.CustomButton();

        tableTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        tableTitle.setForeground(new java.awt.Color(76, 76, 76));
        tableTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableTitle.setText("LOGIN");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblSolicitacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblScroll.setViewportView(tblSolicitacoes);

        formTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        formTitle.setForeground(new java.awt.Color(76, 76, 76));
        formTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        formTitle.setText("APROVAR SOLICITAÇÕES");

        btAprova.setText("APROVAR");
        btAprova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAprovaActionPerformed(evt);
            }
        });

        btRecusa.setText("RECUSAR");
        btRecusa.setStyle(CustomButton.ButtonStyle.DESTRUCTIVE);
        btRecusa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRecusaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(formTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addComponent(tblScroll, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btAprova, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btRecusa, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(formTitle)
                .addGap(18, 18, 18)
                .addComponent(tblScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAprova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRecusa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btAprovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAprovaActionPerformed
        int row = tblSolicitacoes.getSelectedRow();
        if(row != -1){
            JSONObject obj = modelBusca.getJsonAt(row);
            String teste;
            JSONArray arr = null;
            JSONObject response = null;
            
            switch(obj.getString("tipoDesc")){
                case "Conta Bancaria":
                    JSONObject conta = new JSONObject(con.readConta(obj.getString("agencia") + 
                                                                    "-" + obj.getString("numeroConta")));
                    conta.remove("active");
                    conta.put("active", true);
                    
                    teste = con.updateConta(conta.toString());
                    
                    if(teste != null && !teste.equals("")){
                        if(teste.charAt(0) == '['){
                            arr = new JSONArray(teste);
                        }else{
                            response = new JSONObject(teste);
                        }
                    }
                    
                    if(arr != null){
                        for(Object o : arr){
                            JSONObject aux = new JSONObject(o.toString());
                            if(aux.getString("type").equals("error") || aux.getString("type").equals("finderror") || 
                                aux.getString("type").equals("nullerror")){
                                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                                Notification.Location.BOTTOM_RIGHT, aux.getString("message"));
                                n.showNotification();
                                break;
                            }
                        }
                    }    
                    if(response != null){
                        if(response.getString("type").equals("error")){
                            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.INFO, 
                                                            Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                            n.showNotification();
                        }else{
                            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                            Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                            n.showNotification();
                            modelBusca.removeRow(row);
                        }
                    }
                    
                    break;
                default:
                    JSONObject user = new JSONObject(con.readUsuario(obj.getString("cpf")));
                    user.remove("active");
                    user.put("active", true);
                    
                    teste = con.updateUsuario(user.toString(), false);
                    
                    if(teste != null && !teste.equals("")){
                        if(teste.charAt(0) == '['){
                            arr = new JSONArray(teste);
                        }else{
                            response = new JSONObject(teste);
                        }
                    }
                    
                    if(arr != null){
                        for(Object o : arr){
                            JSONObject aux = new JSONObject(o.toString());
                            if(aux.getString("type").equals("error") || aux.getString("type").equals("finderror") || 
                                aux.getString("type").equals("nullerror")){
                                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                                Notification.Location.BOTTOM_RIGHT, aux.getString("message"));
                                n.showNotification();
                                break;
                            }
                        }
                    }    
                    if(response != null){
                        if(response.getString("type").equals("error")){
                            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.INFO, 
                                                            Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                            n.showNotification();
                        }else{
                            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                            Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                            n.showNotification();
                            modelBusca.removeRow(row);
                        }
                    }
                    
                    break;
            }
            
        }else{
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.INFO, 
                                        Notification.Location.BOTTOM_RIGHT, "Selecione um item da tabela!");
            n.showNotification();
        }
    }//GEN-LAST:event_btAprovaActionPerformed

    private void btRecusaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRecusaActionPerformed
        int row = tblSolicitacoes.getSelectedRow();
        if(row != -1){
            JSONObject obj = modelBusca.getJsonAt(row);
            JSONObject response;
            Notification n;
            
            switch(obj.getString("tipoDesc")){
                case "Conta Bancaria":
                    con.deleteConta(obj.getString("agencia") + "-" + obj.getString("numeroConta"));
                    n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                    Notification.Location.BOTTOM_RIGHT, "Conta Deletada!");
                    modelBusca.removeRow(row);
                    n.showNotification();
                    break;
                default:
                    response = new JSONObject(con.deleteUsuario(obj.getString("cpf")));
                    
                    if(response.getString("type").equals("error")){
                        n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                        Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                    }else{
                        if(obj.getString("tipoDesc").equals("Cliente")){
                            con.deleteCliente(obj.getString("cpf"));
                        }else{
                            con.deleteAdministrador(obj.getString("cpf"));
                        }
                        n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                        Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                        modelBusca.removeRow(row);
                    }
                    n.showNotification();
                    break;
            }
            
        }else{
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.INFO, 
                                        Notification.Location.BOTTOM_RIGHT, "Selecione um item da tabela!");
            n.showNotification();
        }
    }//GEN-LAST:event_btRecusaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ui.components.CustomButton btAprova;
    private ui.components.CustomButton btRecusa;
    private javax.swing.JLabel formTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel tableTitle;
    private javax.swing.JScrollPane tblScroll;
    private ui.components.CustomTable tblSolicitacoes;
    // End of variables declaration//GEN-END:variables
}
