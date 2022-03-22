package ui;

import control.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.json.JSONObject;
import ui.components.Notification;
import ui.event.EventMenuSelected;
import ui.forms.AprovarSolicitacao;

public final class MenuCentral extends javax.swing.JFrame {
    private static boolean fullScreen = false;
    private static JFrame form;
    private Controller con;
    private JSONObject o;
    
    public MenuCentral(Controller con) {
        this.con = con;
        o = new JSONObject(con.getUsuarioLogado());
        initComponents();
        
        setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.setPreferredSize(this.getSize());
        this.setTitle("Simple Bank");
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/mainIcon.png")).getImage());
        
        form = MenuCentral.this;
        menuLateral.initMoving(MenuCentral.this);
        menuLateral.setTipoUsuario(new JSONObject(con.getUsuarioLogado()).getInt("tipo"));
        mainPanel.setLayout(new BorderLayout());
        menuLateral.addEventMenuSelected(new EventMenuSelected(){
            boolean isCliente = o.getInt("tipo") == 0;
            @Override
            public void selected(int index){
                switch (index) {
                    case 1:
                        if(isCliente){
                            
                        }else{
                            setForm(new AprovarSolicitacao(con));
                        }
                        break;
                    case 2:
                        if(isCliente){
                            
                        }else{
                            
                        }
                        break;
                    case 3:
                        if(isCliente){
                            
                        }else{
                            con.disconnectDB();
                            dispose();
                        }
                        break;
                    case 4:
                        
                        break;
                    case 5:
                        //Solicitação de Nova Conta
                        JSONObject response = new JSONObject(con.solicitarConta(o.getString("cpf")));
                        Notification.Type tp;
                                
                        if(response.getString("type").equals("error")){
                            tp = Notification.Type.WARNING;
                        }else{
                            tp = Notification.Type.SUCESS;
                        }
                        
                        Notification n = new Notification(MenuCentral.getFrame(), tp, 
                                            Notification.Location.BOTTOM_RIGHT, response.getString("message"));
                        n.showNotification();
                        break;
                    case 6:
                        con.disconnectDB();
                        dispose();
                        break;
                    default:
                        break;
                }
            }
        });
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                con.disconnectDB();
                super.windowClosing(e);
            }
        });
        Notification n = new Notification(this, Notification.Type.SUCESS, 
                                            Notification.Location.BOTTOM_RIGHT, "Seja bem vindo!");
        n.showNotification();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder = new ui.centralmenu.PanelBorder();
        menuLateral = new ui.centralmenu.MenuLateral(o.getInt("tipo"));
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelBorder.setBackground(new java.awt.Color(255, 255, 255));
        panelBorder.setForeground(new java.awt.Color(255, 255, 255));

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout panelBorderLayout = new javax.swing.GroupLayout(panelBorder);
        panelBorder.setLayout(panelBorderLayout);
        panelBorderLayout.setHorizontalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderLayout.createSequentialGroup()
                .addComponent(menuLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorderLayout.setVerticalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuLateral, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
            .addGroup(panelBorderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void setForm(JComponent c){
        mainPanel.removeAll();
        mainPanel.add(c);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    public static boolean isFullScreen(){
        return fullScreen;
    }
    
    public static void setFullScreen(boolean v){
        fullScreen = v;
    }
    
    public static JFrame getFrame(){
        return form;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private ui.centralmenu.MenuLateral menuLateral;
    private ui.centralmenu.PanelBorder panelBorder;
    // End of variables declaration//GEN-END:variables
}
