package ui.forms;

import control.Controller;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.json.JSONObject;
import ui.MenuCentral;
import ui.components.CustomButton;
import ui.components.CustomTable;
import ui.components.Notification;
import ui.models.ContaTableModel;
import util.RegexValidation;

public class GerirConta extends javax.swing.JPanel {
    private Controller con;
    private ContaTableModel modelBusca;
    private String cpf;
    
    public GerirConta(Controller con) {
        initComponents();
        
        this.con = con;
        this.cpf = new JSONObject(con.getUsuarioLogado()).getString("cpf");
        this.btBusca.setStyle(CustomButton.ButtonStyle.SECONDARY);
        modelBusca = new ContaTableModel(con, this.cpf);
        tblContas.setModel(modelBusca);
        panelTransferencia.setVisible(false);
        CustomTable.setBasicScrollConfigurations(scrollTblContas);
        this.cbTipo.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                
                String item = (String) e.getItem();
                
                if(item != null && !item.equals("Transferencia")){
                    panelTransferencia.setVisible(false);
                    revalidate();
                    repaint();
                 }else{
                    panelTransferencia.setVisible(true);
                    revalidate();
                    repaint();
                }
            }
        
        });
        this.txtConta.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(!txtTitular.getText().equals("")){
                    txtTitular.setText("");
                }
            }
        });
    }
    
    public String toJson(){
        String selectedTableObject = getSelectedTableItemJson();
        
        if(selectedTableObject != null){
            JSONObject tblItem = new JSONObject(selectedTableObject);
            JSONObject o = new JSONObject();
        
            o.put("contaOrigem", tblItem.getString("agencia") + "-" + tblItem.getString("numero"));
            o.put("valor", Double.parseDouble(txtValor.getText().replace(",", ".")));
            o.put("operacao", (String) cbTipo.getSelectedItem());
            if(((String) cbTipo.getSelectedItem()).equals("Transferencia")){
                o.put("contaDestino", txtConta.getText());
            }else{
                o.put("contaDestino", tblItem.getString("agencia") + "-" + tblItem.getString("numero"));
            }
            return o.toString();
        }
        return null;
    }
    
    public boolean validateFields(){
        
       
        if(cbTipo.getSelectedItem() != null){
            if(!RegexValidation.TestString(RegexValidation.StandardRegexString.DECIMAL_VALUE, txtValor.getText())){
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                    Notification.Location.BOTTOM_RIGHT, "Digite um valor no formato Decimal!");
                n.showNotification();
                return false;
            }
            String json = toJson();
            if(json != null){
                JSONObject o = new JSONObject(toJson());

                if(o.getString("operacao").equals("Transferencia") && 
                        (txtTitular.getText() == null || txtTitular.getText().equals(""))){
                    Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                        Notification.Location.BOTTOM_RIGHT, "Digite uma conta para transferencia Válida!");
                    n.showNotification();
                    return false;
                }
                return true;
            }
            else{
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                    Notification.Location.BOTTOM_RIGHT, "Selecione um item da tabela!");
                n.showNotification();
                return false;
            }
        }
        else{
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.BOTTOM_RIGHT, "Selecione uma operação!");
                n.showNotification();
                return false;
            }
        
    }
    
    public String getSelectedTableItemJson(){
        if(tblContas.getSelectedRow() != -1){
            return modelBusca.getJsonAt(tblContas.getSelectedRow()).toString();
        }else{
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        scrollTblContas = new javax.swing.JScrollPane();
        tblContas = new ui.components.CustomTable();
        lblTitle = new javax.swing.JLabel();
        txtValor = new ui.components.CustomTextField();
        panelTransferencia = new javax.swing.JPanel();
        txtConta = new ui.components.CustomTextField();
        txtTitular = new ui.components.CustomTextField();
        btBusca = new ui.components.CustomButton();
        btExecuta = new ui.components.CustomButton();
        cbTipo = new ui.components.CustomComboBox();

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        tblContas.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollTblContas.setViewportView(tblContas);

        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(76, 76, 76));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("GERIR CONTA BANCARIA");

        txtValor.setLabelText("Valor");

        panelTransferencia.setBackground(new java.awt.Color(255, 255, 255));
        panelTransferencia.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txtConta.setLabelText("Numero Conta");

        txtTitular.setEditable(false);
        txtTitular.setLabelText("Titular da Conta");

        btBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buscar.png"))); // NOI18N
        btBusca.setRound(50);
        btBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTransferenciaLayout = new javax.swing.GroupLayout(panelTransferencia);
        panelTransferencia.setLayout(panelTransferenciaLayout);
        panelTransferenciaLayout.setHorizontalGroup(
            panelTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransferenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTitular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTransferenciaLayout.setVerticalGroup(
            panelTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransferenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTransferenciaLayout.createSequentialGroup()
                        .addGroup(panelTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btExecuta.setText("Movimentar");
        btExecuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExecutaActionPerformed(evt);
            }
        });

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Saque", "Deposito", "Transferencia" }));
        cbTipo.setSelectedIndex(-1);
        cbTipo.setLblText("Tipo de Operação");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTransferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollTblContas, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btExecuta, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTblContas, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btExecuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btExecutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExecutaActionPerformed
        if(validateFields()){
            JSONObject obj = new JSONObject(toJson());
            JSONObject response = null;
            switch(obj.getString("operacao")){
                case "Saque":
                    response = new JSONObject(con.sacarConta(obj.getString("contaOrigem"), obj.getDouble("valor")));
                    break;
                case "Deposito":
                    response = new JSONObject(con.depositarConta(obj.getString("contaOrigem"), obj.getDouble("valor")));
                    break;
                case "Transferencia":
                    response = new JSONObject(con.transferirDinheiroConta(obj.getString("contaOrigem"), 
                            obj.getString("contaDestino"), obj.getDouble("valor")));
                    break;
                default:
                    break;
            }
            if(response != null){
                Notification.Type tp;
                if(response.getString("type").equals("noterror")){
                    tp = Notification.Type.SUCESS;
                    modelBusca.reload();
                }else{
                    tp = Notification.Type.WARNING;
                }
                Notification n = new Notification(MenuCentral.getFrame(), tp, 
                                                Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                n.showNotification();
            }else{
                
            }
            
        }
    }//GEN-LAST:event_btExecutaActionPerformed

    private void btBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscaActionPerformed
        String result = con.readConta(txtConta.getText());
        
        if(result != null){
            JSONObject obj = new JSONObject(result);
            result = con.readCliente(obj.getString("cpfTitular"));
            if(result == null){
                result = con.readAdministrador(obj.getString("cpf"));
            }
            if(result != null){
                obj = new JSONObject(result);
                txtTitular.setText(obj.getString("nome"));
            }
        }
        
    }//GEN-LAST:event_btBuscaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ui.components.CustomButton btBusca;
    private ui.components.CustomButton btExecuta;
    private ui.components.CustomComboBox cbTipo;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelTransferencia;
    private javax.swing.JScrollPane scrollTblContas;
    private ui.components.CustomTable tblContas;
    private ui.components.CustomTextField txtConta;
    private ui.components.CustomTextField txtTitular;
    private ui.components.CustomTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
