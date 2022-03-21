package ui;

import control.Controller;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.components.Notification;

public final class Registrar extends javax.swing.JFrame {
    private Controller con;
    
    public Registrar(Controller con) {
        initComponents();
        
        this.txtCargo.setVisible(false);
        this.con = con;
        setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.setPreferredSize(this.getSize());
        this.setTitle("Simple Bank - Registrar");
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/mainIcon.png")).getImage());
        
        this.cbTipo.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                
                String item = (String) e.getItem();
                
                if(item != null && item.equals("Cliente")){
                    txtCargo.setVisible(false);
                    revalidate();
                    repaint();
                 }else{
                    txtCargo.setVisible(true);
                    revalidate();
                    repaint();
                }
            }
        
        });
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                new Login(con).setVisible(true);
                super.windowClosing(e);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dtNascimentoChooser = new ui.datechooser.DateChooser();
        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tableTitle = new javax.swing.JLabel();
        txtCpf = new ui.components.CustomTextField();
        txtNome = new ui.components.CustomTextField();
        txtDtNascimento = new ui.components.CustomTextField();
        txtEmail = new ui.components.CustomTextField();
        txtDDD = new ui.components.CustomTextField();
        txtTelefone = new ui.components.CustomTextField();
        txtRua = new ui.components.CustomTextField();
        txtNumero = new ui.components.CustomTextField();
        txtBairro = new ui.components.CustomTextField();
        txtCidade = new ui.components.CustomTextField();
        txtEstado = new ui.components.CustomTextField();
        cbTipo = new ui.components.CustomComboBox();
        txtCargo = new ui.components.CustomTextField();
        btSolicita = new ui.components.CustomButton();
        txtSenha = new ui.components.CustomTextField();

        dtNascimentoChooser.setTextRefernce(txtDtNascimento);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(76, 76, 76));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/mainIcon.png"))); // NOI18N
        jLabel1.setText("  SimpleBANK");

        tableTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        tableTitle.setForeground(new java.awt.Color(76, 76, 76));
        tableTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableTitle.setText("Dados Pessoais");

        txtCpf.setLabelText("CPF");

        txtNome.setLabelText("Nome");

        txtDtNascimento.setEditable(false);
        txtDtNascimento.setLabelText("Data de Nascimento");

        txtEmail.setLabelText("Email");

        txtDDD.setLabelText("DDD");

        txtTelefone.setLabelText("Telefone");

        txtRua.setLabelText("Rua");

        txtNumero.setLabelText("Numero");

        txtBairro.setLabelText("Bairro");

        txtCidade.setLabelText("Cidade");

        txtEstado.setLabelText("Estado");

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cliente", "Administrador" }));
        cbTipo.setLblText("Tipo de Usuario");

        txtCargo.setLabelText("Cargo");

        btSolicita.setText("Solicitar");
        btSolicita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSolicitaActionPerformed(evt);
            }
        });

        txtSenha.setLabelText("Senha");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBairro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDtNascimento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(txtDDD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(btSolicita, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableTitle)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDtNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(btSolicita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSolicitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSolicitaActionPerformed
        System.out.println("oi");
        if(txtSenha.getText() != null && !txtSenha.getText().equals("")){
            JSONObject j = toJson();
            JSONArray arr = null;
            JSONObject response = null;
            String teste;
            System.out.println("ola denovo");
            if(this.cbTipo.getSelectedItem() != null && ((String) this.cbTipo.getSelectedItem()).equals("Cliente")){
                teste = con.insertCliente(j.toString());
            }else{
                teste = con.insertAdministrador(j.toString());
            }
            
            if(teste != null && !teste.equals("")){
                if(teste.charAt(0) == '['){
                    arr = new JSONArray(teste);
                }else{
                    response = new JSONObject(teste);
                    System.out.println(response);
                }
            }

            if(arr != null){
                for(Object o : arr){
                    JSONObject obj = new JSONObject(o.toString());
                    if(obj.getString("type").equals("error") || obj.getString("type").equals("finderror") || 
                            obj.getString("type").equals("nullerror")){
                        Notification n = new Notification(this, Notification.Type.WARNING, 
                                                        Notification.Location.BOTTOM_RIGHT, obj.getString("message"));
                        n.showNotification();
                        break;
                    }
                }
            }    
            if(response != null){
                if(response.getString("type").equals("error")){
                    Notification n = new Notification(this, Notification.Type.INFO, 
                                                    Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                    n.showNotification();
                }else{
                    Notification n = new Notification(this, Notification.Type.SUCESS, 
                                                    Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                    n.showNotification();
                    con.insertUsuario(toJsonUsuario().toString());
                }
            }
        }else{
            Notification n = new Notification(this, Notification.Type.INFO, 
                                            Notification.Location.BOTTOM_RIGHT, "Preencha o campo de Senha!");
            n.showNotification();
        }
        
    }//GEN-LAST:event_btSolicitaActionPerformed

    private JSONObject toJson(){
        JSONObject j = new JSONObject();
        
        j.put("id", 0);
        j.put("cpf", txtCpf.getText());
        j.put("nome", txtNome.getText());
        j.put("dtNascimento", txtDtNascimento.getText());
        j.put("email", txtEmail.getText());
        
        JSONObject tel = new JSONObject();
        tel.put("ddd", txtDDD.getText());
        tel.put("numero", txtTelefone.getText());
        
        j.put("telefone", tel);
        
        JSONObject end = new JSONObject();
        end.put("rua", txtRua.getText());
        end.put("bairro", txtBairro.getText());
        end.put("cidade", txtCidade.getText());
        end.put("estado", txtEstado.getText());
        end.put("numCasa", txtNumero.getText());
        
        j.put("endereco", end);
        
        if(this.cbTipo.getSelectedItem() != null && ((String) this.cbTipo.getSelectedItem()).equals("Cliente")){
            j.put("dtCadastro", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        }else{
            j.put("dtAdmissao", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            j.put("cargo", txtCargo.getText());
        }
        
        return j;
    }

    private JSONObject toJsonUsuario(){
        JSONObject o = new JSONObject();
        o.put("cpf", txtCpf.getText());
        o.put("senha", txtSenha.getText());
        if(this.cbTipo.getSelectedItem() != null && ((String) this.cbTipo.getSelectedItem()).equals("Cliente")){
             o.put("tipo", 0);
        }else{
            o.put("tipo", 1);
        }
        o.put("idUser", 0);
        o.put("active", false);
        return o;
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ui.components.CustomButton btSolicita;
    private ui.components.CustomComboBox cbTipo;
    private ui.datechooser.DateChooser dtNascimentoChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel tableTitle;
    private ui.components.CustomTextField txtBairro;
    private ui.components.CustomTextField txtCargo;
    private ui.components.CustomTextField txtCidade;
    private ui.components.CustomTextField txtCpf;
    private ui.components.CustomTextField txtDDD;
    private ui.components.CustomTextField txtDtNascimento;
    private ui.components.CustomTextField txtEmail;
    private ui.components.CustomTextField txtEstado;
    private ui.components.CustomTextField txtNome;
    private ui.components.CustomTextField txtNumero;
    private ui.components.CustomTextField txtRua;
    private ui.components.CustomTextField txtSenha;
    private ui.components.CustomTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
