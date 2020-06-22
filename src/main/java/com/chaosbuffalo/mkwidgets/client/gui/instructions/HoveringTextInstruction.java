package com.chaosbuffalo.mkwidgets.client.gui.instructions;

import com.chaosbuffalo.mkwidgets.client.gui.math.Vec2i;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;

public class HoveringTextInstruction implements IInstruction {

    public ArrayList<String> texts;
    public Vec2i mousePos;

    public HoveringTextInstruction(ArrayList<String> texts, Vec2i mousePos) {
        this.texts = texts;
        this.mousePos = mousePos;
    }

    public HoveringTextInstruction(ArrayList<String> texts, int x, int y) {
        this(texts, new Vec2i(x, y));
    }

    public HoveringTextInstruction(String text, int x, int y) {
        this(new ArrayList<>(), x, y);
        texts.add(text);
    }

    public HoveringTextInstruction(String text, Vec2i mousePos) {
        this(new ArrayList<>(), mousePos);
        texts.add(text);
    }

    @Override
    public void draw(FontRenderer renderer, int screenWidth, int screenHeight, float partialTicks) {
        GuiUtils.drawHoveringText(texts, mousePos.x, mousePos.y, screenWidth, screenHeight, -1, renderer);
    }
}
