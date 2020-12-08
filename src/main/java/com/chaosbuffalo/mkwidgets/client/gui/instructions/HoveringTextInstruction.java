package com.chaosbuffalo.mkwidgets.client.gui.instructions;

import com.chaosbuffalo.mkwidgets.client.gui.math.Vec2i;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HoveringTextInstruction implements IInstruction {

    public List<String> texts;
    public Vec2i mousePos;

    public HoveringTextInstruction(List<String> texts, Vec2i mousePos) {
        this.texts = texts;
        this.mousePos = mousePos;
    }

    public HoveringTextInstruction(List<String> texts, int x, int y) {
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
    public void draw(MatrixStack matrixStack, FontRenderer renderer, int screenWidth, int screenHeight, float partialTicks) {
        GuiUtils.drawHoveringText(
                matrixStack,
                texts.stream().map(StringTextComponent::new).collect(Collectors.toList()),
                mousePos.x, mousePos.y, screenWidth, screenHeight, -1, renderer);
    }
}
