package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MKText extends MKWidget {

    public ITextComponent text;
    private FontRenderer fontRenderer;
    public int color;
    public boolean isMultiline;
    public boolean isCentered;

    public MKText(FontRenderer renderer, ITextComponent text, int x, int y, int width, int height){
        super(x, y, width, height);
        this.color = 0;
        this.fontRenderer = renderer;
        this.text = text;
        this.isMultiline = false;
    }

    public MKText(FontRenderer renderer, ITextComponent text){
        this(renderer, text, 200, renderer.FONT_HEIGHT);
    }

    public MKText(FontRenderer renderer, ITextComponent text, int x, int y){
        this(renderer, text, x, y, 200, renderer.FONT_HEIGHT);
    }

    public MKText(FontRenderer renderer, String text) {
        this(renderer, text, 200);
    }

    public MKText(FontRenderer renderer, String text, int x, int y){
        this(renderer, text, x, y, 200, renderer.FONT_HEIGHT);
    }

    public MKText(FontRenderer renderer, String text, int x, int y, int width, int height){
        this(renderer, new StringTextComponent(text), x, y, width, height);
    }

    public MKText(FontRenderer renderer, String text, int width){
        this(renderer, text, 0, 0, width, renderer.FONT_HEIGHT);
    }

    public int getFontHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    public int getColor() {
        return color;
    }

    public MKText setColor(int i) {
        this.color = i;
        return this;
    }

    public void draw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        String formattedText = getText().getFormattedText();
        if (isCentered()) {
            this.drawCenteredStringNoDropShadow(this.fontRenderer, formattedText,
                    this.getX() + this.getWidth() / 2,
                    this.getY(), color);
        } else if (isMultiline()) {
            fontRenderer.drawSplitString(formattedText, this.getX(), this.getY(), this.getWidth(), this.color);
        } else {
            fontRenderer.drawString(formattedText, this.getX(), this.getY(), this.color);
        }

    }

    public void drawCenteredStringNoDropShadow(FontRenderer fontRenderer, String string, int x, int y, int color) {
        fontRenderer.drawString(string, (float)(x - fontRenderer.getStringWidth(string) / 2), (float)y, color);
    }

    public MKText setIsCentered(boolean isCentered) {
        this.isCentered = isCentered;
        return this;
    }

    public boolean isCentered() {
        return isCentered;
    }

    public MKText setText(String text) {
        return setText(new StringTextComponent(text));
    }

    public MKText setText(ITextComponent text){
        this.text = text;
        updateLabel();
        return this;
    }

    public ITextComponent getText() {
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
            setHeight(fontRenderer.getWordWrappedHeight(getText().getFormattedText(), getWidth()));
        } else {
            setHeight(fontRenderer.FONT_HEIGHT);
        }
    }
}
