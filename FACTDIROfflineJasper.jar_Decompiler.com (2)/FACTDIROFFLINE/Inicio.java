package FACTDIROFFLINE;

import imagen.PanelImagen;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

public class Inicio extends JFrame {
   public Inicio() {
      this.setUndecorated(true);
      this.initComponents();
      this.setDefaultCloseOperation(3);
      this.setBounds(100, 100, 450, 300);
      PanelImagen p = new PanelImagen();
      p.setBorder(new EmptyBorder(5, 5, 5, 5));
      p.setLayout(new BorderLayout(0, 0));
      this.setContentPane(p);
   }

   private void initComponents() {
      this.setDefaultCloseOperation(3);
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 567, 32767));
      layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 251, 32767));
      this.pack();
   }
}
