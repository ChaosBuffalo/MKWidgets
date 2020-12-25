package com.chaosbuffalo.mkwidgets.client.gui.instructions;

import com.chaosbuffalo.mkwidgets.client.gui.math.Vec2i;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HoveringTextInstruction implements IInstruction {

    private final List<ITextComponent> texts;
    private final Vec2i mousePos;

    public HoveringTextInstruction(String text, Vec2i mousePos) {
        this(new StringTextComponent(text), mousePos);
    }

    public HoveringTextInstruction(String text, int x, int y) {
        this(text, new Vec2i(x, y));
    }

    public HoveringTextInstruction(ITextComponent text, int x, int y) {
        this(text, new Vec2i(x, y));
    }

    public HoveringTextInstruction(ITextComponent text, Vec2i mousePos) {
        this(new ArrayList<>(), mousePos);
        texts.add(text);
    }

    public HoveringTextInstruction(List<ITextComponent> texts, int x, int y) {
        this(texts, new Vec2i(x, y));
    }

    public HoveringTextInstruction(List<ITextComponent> texts, Vec2i mousePos) {
        this.texts = new ArrayList<>(texts);
        this.mousePos = mousePos;
    }

    public static HoveringTextInstruction fromStrings(List<String> texts, Vec2i mousePos) {
        return new HoveringTextInstruction(texts.stream().map(StringTextComponent::new).collect(Collectors.toList()), mousePos);
    }

    public static HoveringTextInstruction fromStrings(List<String> texts, int x, int y) {
        return fromStrings(texts, new Vec2i(x, y));
    }

    @Override
    public void draw(MatrixStack matrixStack, FontRenderer renderer, int screenWidth, int screenHeight, float partialTicks) {
        GuiUtils.drawHoveringText(
                matrixStack,
                texts,
                mousePos.x, mousePos.y, screenWidth, screenHeight, -1, renderer);
    }
}
