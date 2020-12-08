package com.chaosbuffalo.mkwidgets.client.gui.instructions;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;

public interface IInstruction {

    void draw(MatrixStack matrixStack, FontRenderer renderer, int screenWidth, int screenHeight, float partialTicks);
}
