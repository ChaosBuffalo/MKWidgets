package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class MKText extends MKWidget {

    public Component text;
    private final Font fontRenderer;
    public int color;
    public boolean isMultiline;
    public boolean isCentered;

    public MKText(Font renderer, Component text, int x, int y, int width, int height){
        super(x, y, width, height);
        this.color = 0;
        this.fontRenderer = renderer;
        this.text = text;
        this.isMultiline = false;
    }

    public MKText(Font renderer, Component text){
        this(renderer, text, 200, renderer.lineHeight);
    }

    public MKText(Font renderer, Component text, int x, int y){
        this(renderer, text, x, y, 200, renderer.lineHeight);
    }

    public MKText(Font renderer, String text) {
        this(renderer, text, 200);
    }

    public MKText(Font renderer, String text, int x, int y){
        this(renderer, text, x, y, 200, renderer.lineHeight);
    }

    public MKText(Font renderer, String text, int x, int y, int width, int height){
        this(renderer, new TextComponent(text), x, y, width, height);
    }

    public MKText(Font renderer, String text, int width){
        this(renderer, text, 0, 0, width, renderer.lineHeight);
    }

    public int getFontHeight() {
        return fontRenderer.lineHeight;
    }

    public int getColor() {
        return color;
    }

    public MKText setColor(int i) {
        this.color = i;
        return this;
    }

    public void draw(PoseStack matrixStack, Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        Component formattedText = getText();
        if (isCentered()) {
            this.drawCenteredStringNoDropShadow(matrixStack, this.fontRenderer,
                    formattedText,
                    this.getX() + this.getWidth() / 2, this.getY() + (this.getHeight() - this.fontRenderer.lineHeight) / 2, color);
        } else if (isMultiline()) {
            drawStringMultiline(fontRenderer, formattedText, getX(), getY(), getWidth(), color);
        } else {
            drawString(fontRenderer, matrixStack, formattedText, getX(), getY(), color);
        }
    }

    protected void drawString(Font font, PoseStack matrixStack, Component text, float x, float y, int color) {
        font.draw(matrixStack, text, x, y, color);
    }

    protected void drawStringShadow(Font font, PoseStack matrixStack, Component text, float x, float y, int color) {
        font.draw(matrixStack, text, x, y, color);
    }

    protected void drawStringMultiline(Font font, Component text, int x, int y, int width, int color) {
        font.drawWordWrap(text, x, y, width, color);
    }

    public void drawCenteredStringNoDropShadow(PoseStack matrixStack, Font fontRenderer, String string, int x, int y, int color) {
        fontRenderer.draw(matrixStack, string, (float)(x - fontRenderer.width(string) / 2), (float)y, color);
    }

    public void drawCenteredStringNoDropShadow(PoseStack matrixStack, Font fontRenderer, Component string, int x, int y, int color) {
        drawString(fontRenderer, matrixStack, string, (float)(x - fontRenderer.width(string) / 2), (float)y, color);
    }

    public MKText setIsCentered(boolean isCentered) {
        this.isCentered = isCentered;
        return this;
    }

    public boolean isCentered() {
        return isCentered;
    }

    public MKText setText(String text) {
        return setText(new TextComponent(text));
    }

    public MKText setText(Component text){
        this.text = text;
        updateLabel();
        return this;
    }

    public Component getText() {
        return text;
    }

    public MKText setMultiline(boolean multiline) {
        this.isMultiline = multiline;
        updateLabel();
        return this;
    }

    public boolean isMultiline() {
        return isMultiline;
    }

    @Override
    public IMKWidget setWidth(int newWidth) {
        super.setWidth(newWidth);
        updateLabel();
        return this;
    }


    private void updateLabel() {
        if (isMultiline()) {
            setHeight(fontRenderer.wordWrapHeight(getText().getString(), getWidth()));
        } else {
            setHeight(fontRenderer.lineHeight);
        }
    }
}
