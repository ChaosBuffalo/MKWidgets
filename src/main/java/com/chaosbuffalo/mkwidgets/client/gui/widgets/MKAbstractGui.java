package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import net.minecraft.client.gui.AbstractGui;

public abstract class MKAbstractGui extends AbstractGui {

    public static void mkBlitUVSizeSame(int x, int y, float u0, float v0, int width, int height,
                                        int texWidth, int texHeight) {
        blit(x, y, u0, v0, width, height, texWidth, texHeight);
    }

    public static void mkBlitUVSizeDifferent(int x, int y, int width, int height, float u0, float v0,
                                             int uSize, int vSize, int texWidth, int texHeight) {
        blit(x, y, width, height, u0, v0, uSize, vSize, texWidth, texHeight);
    }

    public static void mkFill(int x0, int y0, int x1, int y1, int color){
        fill(x0, y0, x1, y1, color);
    }

    public static void mkInnerBlit(int x0, int x1, int y0, int y1,
                                   int z, int uSize, int vSize, float u0, float v0,
                                   int texWidth, int texHeight) {
        mkInnerBlit(x0, x1, y0, y1, z,
                (u0 + 0.0F) / (float)texWidth,
                (u0 + (float)uSize) / (float)texWidth,
                (v0 + 0.0F) / (float)texHeight,
                (v0 + (float)vSize) / (float)texHeight);
    }

    public static void mkInnerBlit(int x0, int x1, int y0, int y1, int z,
                                   float u0, float u1, float v0, float v1) {
        innerBlit(x0, x1, y0, y1, z, u0, u1, v0, v1);
    }
}
