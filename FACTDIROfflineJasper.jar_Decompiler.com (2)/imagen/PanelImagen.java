package imagen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelImagen extends JPanel {
   public PanelImagen() {
      this.setSize(400, 280);
   }

   public void paintComponent(Graphics g) {
      Dimension tamanio = this.getSize();
      ImageIcon imagenFondo = new ImageIcon(this.getClass().getResource("monicae.jpg"));
      g.drawImage(imagenFondo.getImage(), 0, 0, tamanio.width, tamanio.height, (ImageObserver)null);
      this.setOpaque(false);
      super.paintComponent(g);
   }
}
