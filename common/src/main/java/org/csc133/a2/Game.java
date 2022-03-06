// ----------------------------------------------------------------------------
// Initializes game world. Source of graphics context each object uses to draw.
// ----------------------------------------------------------------------------

package org.csc133.a2;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a2.views.GlassCockpit;
import org.csc133.a2.views.MapView;

import javax.swing.border.Border;

public class Game extends Form implements Runnable {
    final private GameWorld gw;
    MapView mapView;
    GlassCockpit glassCockpit;

    public final static int DISP_W = Display.getInstance().getDisplayWidth();
    public final static int DISP_H = Display.getInstance().getDisplayHeight();

    public static int getSmallDim() {
        return Math.min(DISP_W, DISP_H);
    }

    public static int getLargeDim() {
        return Math.max(DISP_W, DISP_H);
    }

    public Game() {
        gw = new GameWorld();
        mapView = new MapView(gw);
        this.getToolbar().hideToolbar();
        this.getAllStyles().setBgColor(ColorUtil.BLACK);
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, mapView);

        // key listeners to control user input
        //
        addKeyListener(-93, (evt) -> gw.processKeyPress(-93));
        addKeyListener(-94, (evt) -> gw.processKeyPress(-94));
        addKeyListener(-91, (evt) -> gw.processKeyPress(-91));
        addKeyListener(-92, (evt) -> gw.processKeyPress(-92));
        addKeyListener('f', (evt) -> gw.processKeyPress('f'));
        addKeyListener('d', (evt) -> gw.processKeyPress('d'));
        addKeyListener('Q', (evt) -> gw.quit());

        UITimer timer = new UITimer(this);
        timer.schedule(20, true, this);

//        this.show();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //gw.draw(g);
    }

    @Override
    public void run() {
//        System.out.println("run() is running...");
        gw.tick();
        repaint();
    }
}
