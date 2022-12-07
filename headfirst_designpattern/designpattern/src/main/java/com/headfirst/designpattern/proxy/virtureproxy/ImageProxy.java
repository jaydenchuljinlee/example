package com.headfirst.designpattern.proxy.virtureproxy;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageProxy implements Icon {
    private volatile ImageIcon imageIcon;
    private final URL imageURL;
    private Thread retrievalThread;
    private boolean retrieving = false;

    public ImageProxy(URL url) {
        this.imageURL = url;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (imageIcon != null) {
            imageIcon.paintIcon(c, g, x, y);
            return;
        }

        g.drawString("Loading album cover, please wait...", x+300, y+190);

        if (retrieving) return;

        retrieving = true;

        retrievalThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setImageIcon(new ImageIcon(imageURL, "Album Cover"));
                    c.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        retrievalThread = new Thread(() -> {
            try {
                setImageIcon(new ImageIcon(imageURL, "Album Cover"));
                c.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        retrievalThread.start();
    }

    @Override
    public int getIconWidth() {
        if (imageIcon != null) {
            return imageIcon.getIconWidth();
        }

        return 800;
    }

    @Override
    public int getIconHeight() {
        if (imageIcon != null) {
            return imageIcon.getIconHeight();
        }

        return 600;
    }

    synchronized void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
}
