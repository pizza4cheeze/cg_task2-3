package ru.vsu.cs.Grushevskaya.lineDrawers;

import java.awt.*;

public class GraphicsPixelDrawer implements PixelDrawer {
    private final Graphics2D g2d;

    public GraphicsPixelDrawer(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void drawPixel(int x, int y) {
        g2d.fillRect(x, y, 1, 1);
    }
}
