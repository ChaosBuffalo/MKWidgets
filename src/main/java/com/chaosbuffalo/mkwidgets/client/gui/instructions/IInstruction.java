package com.chaosbuffalo.mkwidgets.client.gui.instructions;

import net.minecraft.client.gui.FontRenderer;

public interface IInstruction {

    void draw(FontRenderer renderer, int screenWidth, int screenHeight, float partialTicks);
}
