package ui;

import control.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.json.JSONObject;
import ui.event.EventMenuSelected;

public final class MenuCentral extends javax.swing.JFrame {
    private static boolean fullScreen = false;
    private static JFrame form;
    private Controller con;
    
    public MenuCentral(Controller con) {
        initComponents();

        setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.con = con;
        this.setPreferredSize(this.getSize());
        this.setTitle("Simple Bank");
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/mainIcon.png")).getImage());
        
        menuLateral.initMoving(MenuCentral.this);
        menuLateral.setTipoUsuario(new JSONObject(con.getUsuarioLogado()).getInt("tipo"));
        mainPanel.setLayout(new BorderLayout());
        menuLateral.addEventMenuSelected(new EventMenuSelected(){
            @Override
            public void selected(int index){
                switch (index) {
                    case 1:
                        
                        break;
                    case 2:
                        
                        break;
                    case 3:
                        
                    case 4:
                        
                        break;
                    case 5:
                        
                        break;
                    case 6:
                        
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder = new ui.centralmenu.PanelBorder();
        menuLateral = new ui.centralmenu.MenuLateral();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelBorder.setBackground(new java.awt.Color(255, 255, 255));
        panelBorder.setForeground(new java.awt.Color(255, 255, 255));

        mainPanel.setOpaque(false);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 698, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelBorderLayout = new javax.swing.GroupLayout(panelBorder);
        panelBorder.setLayout(panelBorderLayout);
        panelBorderLayout.setHorizontalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderLayout.createSequentialGroup()
                .addComponent(menuLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
