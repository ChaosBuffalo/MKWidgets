package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;

public abstract class MKAbstractGui extends AbstractGui {

    public static void mkBlitUVSizeSame(MatrixStack matrixStack, int x, int y, float u0, float v0, int width, int height,
                                        int texWidth, int texHeight) {
        blit(matrixStack, x, y, u0, v0, width, height, texWidth, texHeight);
    }

    public static void mkBlitUVSizeDifferent(MatrixStack matrixStack, int x, int y, int width, int height, float u0, float v0,
                                             int uSize, int vSize, int texWidth, int texHeight) {
        blit(matrixStack, x, y, width, height, u0, v0, uSize, vSize, texWidth, texHeight);
    }

    public static void mkFill(MatrixStack matrixStack, int x0, int y0, int x1, int y1, int color){
        fill(matrixStack, x0, y0, x1, y1, color);
    }
}
