package FACTDIROFFLINE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Principal extends JFrame {
   private FACTDIROffline objPdfToFact;
   Path dir;
   private JLabel jLabel1;
   private JLabel jLabel2;
   private JLabel jLabel3;
   private JLabel jLabel4;
   private JLabel jLabel5;
   private JLabel jLabel6;
   private JLabel jLabelFileNotAutorized;
   private JMenu jMenu1;
   private JMenuBar jMenuBar1;
   private JMenuItem jMenuItem1;
   private JMenuItem jMenuItem2;
   private JScrollPane jScrollPane1;
   private JTextArea jTextArea1;
   private PopupMenu popupMenu1;

   public Principal(Path dir) {
      this.dir = dir;
      this.objPdfToFact = new FACTDIROffline();
      this.initComponents();
   }

   public void ActualizarText(String texto) {
      this.jTextArea1.append(texto + " \n");
   }

   public void ActualizarDocNoAutorizados(String cantidad) {
      this.jLabelFileNotAutorized.setText(cantidad);
   }

   private void initComponents() {
      this.jMenuItem1 = new JMenuItem();
      this.popupMenu1 = new PopupMenu();
      this.jScrollPane1 = new JScrollPane();
      this.jTextArea1 = new JTextArea();
      this.jLabel2 = new JLabel();
      this.jLabel1 = new JLabel();
      this.jLabel3 = new JLabel();
      this.jLabel4 = new JLabel();
      this.jLabel5 = new JLabel();
      this.jLabel6 = new JLabel();
      this.jLabelFileNotAutorized = new JLabel();
      this.jMenuBar1 = new JMenuBar();
      this.jMenu1 = new JMenu();
      this.jMenuItem2 = new JMenuItem();
      this.jMenuItem1.setText("jMenuItem1");
      this.popupMenu1.setLabel("popupMenu1");
      this.setDefaultCloseOperation(3);
      this.setLocation(new Point(0, 0));
      this.setSize(new Dimension(675, 600));
      this.jTextArea1.setEditable(false);
      this.jTextArea1.setColumns(20);
      this.jTextArea1.setRows(5);
      this.jScrollPane1.setViewportView(this.jTextArea1);
      this.jLabel2.setFont(new Font("Times New Roman", 1, 14));
      this.jLabel2.setText("MONICA  Ecuador");
      this.jLabel1.setText("www.monicaecuador.com");
      this.jLabel3.setText("Teléfonos y Whastapp: 0987362687 / 0958703111");
      this.jLabel4.setText("Información, preguntas o problemas favor comunicarse a nuestros números.");
      this.jLabel5.setHorizontalAlignment(0);
      this.jLabel5.setIcon(new ImageIcon(this.getClass().getResource("/imagen/monicae.jpg")));
      this.jLabel5.setText("jLabel5");
      this.jLabel5.setVerticalAlignment(1);
      this.jLabel5.setName("");
      this.jLabel6.setFont(new Font("Tahoma", 1, 12));
      this.jLabel6.setText("Archivos No Autorizados:");
      this.jLabel6.setToolTipText("");
      this.jLabelFileNotAutorized.setFont(new Font("Tahoma", 1, 12));
      this.jLabelFileNotAutorized.setForeground(new Color(255, 0, 0));
      this.jLabelFileNotAutorized.setText("Cargando...");
      this.jLabelFileNotAutorized.setToolTipText("");
      this.jMenu1.setText("Opciones");
      this.jMenuItem2.setText("Reenviar Comprobante");
      this.jMenuItem2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            Principal.this.jMenuItem2ActionPerformed(evt);
         }
      });
      this.jMenu1.add(this.jMenuItem2);
      this.jMenuBar1.add(this.jMenu1);
      this.setJMenuBar(this.jMenuBar1);
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1)).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel3).addComponent(this.jLabel1).addComponent(this.jLabel2, -2, 175, -2).addGroup(layout.createSequentialGroup().addGap(0, 214, 32767).addComponent(this.jLabel6).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabelFileNotAutorized))).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel5, -2, 382, -2))).addContainerGap()));
      layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel1).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel3).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel4).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.jLabelFileNotAutorized))).addComponent(this.jLabel5, -2, 92, -2)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -1, 383, 32767).addContainerGap()));
      this.jLabel2.getAccessibleContext().setAccessibleDescription("");
      this.jLabel6.getAccessibleContext().setAccessibleName("Archivos");
      this.jLabelFileNotAutorized.getAccessibleContext().setAccessibleName("jLabelFileNotAutorized");
      this.pack();
   }

   private void jMenuItem2ActionPerformed(ActionEvent evt) {
      Thread t = new Thread(new Runnable() {
         public void run() {
    Principal.this.ActualizarText("Reenviando comprobantes sin procesar...");
    FACTDIROffline.ProcesarComprobantesPendientesAutorizacion();

    try {
        FACTDIROffline.ProcesarComprobantesPendientes(Principal.this.dir);
        FACTDIROffline.ProcesarComprobantesPendientesSRI();
    } catch (Exception var2) {
        Principal.this.ActualizarText("Error procesando Comprobantes Pendientes");
    }
}
      });
      t.start();
   }
}
