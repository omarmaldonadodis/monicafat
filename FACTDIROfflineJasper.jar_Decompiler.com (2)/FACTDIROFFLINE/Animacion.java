package FACTDIROFFLINE;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Animacion {
   private static final int TIME = 3000;
   private static final int MILLIS_PER_FRAME = 25;
   private static final float DELTA = 0.008333334F;

   public void fade(final JFrame frame, final boolean in) {
      frame.setOpacity(in ? 0.0F : 1.0F);
      if (in) {
         frame.setState(0);
      }

      final Timer timer = new Timer();
      TimerTask timerTask = new TimerTask() {
         float opacity = in ? 0.0F : 1.0F;
         float delta = in ? 0.008333334F : -0.008333334F;

         public void run() {
            this.opacity += this.delta;
            if (this.opacity < 0.0F) {
               frame.setState(1);
               frame.setOpacity(1.0F);
               timer.cancel();
            } else if (this.opacity > 1.0F) {
               frame.setOpacity(1.0F);
               timer.cancel();
            } else {
               frame.setOpacity(this.opacity);
            }

         }
      };
      timer.scheduleAtFixedRate(timerTask, 25L, 25L);
   }
}
