package com.nasser.juego.marco;

import com.nasser.juego.marco.Graficos.PixmapFormat;

public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
