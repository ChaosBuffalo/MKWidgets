package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.chaosbuffalo.mkwidgets.client.gui.math.Vec2i;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;

public class HoveringTextInstruction {

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

    public void draw(FontRenderer renderer, int width, int height) {
        GuiUtils.drawHoveringText(texts, mousePos.x, mousePos.y, width, height, -1, renderer);
    }
}
