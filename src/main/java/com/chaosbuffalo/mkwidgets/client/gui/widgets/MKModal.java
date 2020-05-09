package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.chaosbuffalo.mkwidgets.client.gui.screens.IMKScreen;
import net.minecraft.client.Minecraft;

public class MKModal extends MKWidgetBase<MKModal> implements IMKModal<MKModal> {

    private boolean doBackground;
    private int backgroundColor;
    private boolean closeOnClickOutsideContent;
    private Runnable onCloseCallback;

    public MKModal(int width, int height) {
        super(0, 0, width, height);
        doBackground = true;
        backgroundColor = 0x7D000000;
        closeOnClickOutsideContent = true;
    }

    public MKModal setBackgroundColor(int color) {
        backgroundColor = color;
        return this;
    }

    @Override
    public MKModal setCloseOnClickOutside(boolean value) {
        closeOnClickOutsideContent = value;
        return this;
    }

    @Override
    public boolean shouldCloseOnClickOutside() {
        return closeOnClickOutsideContent;
    }

    @Override
    public MKModal setOnCloseCallback(Runnable callback) {
        onCloseCallback = callback;
        return this;
    }

    @Override
    public Runnable getOnCloseCallback() {
        return onCloseCallback;
    }

    @Override
    public boolean onMousePressed(Minecraft minecraft, double mouseX, double mouseY, int mouseButton) {
        if (getScreen() != null && shouldCloseOnClickOutside()) {
            IMKScreen<?> screen = getScreen();
            screen.closeModal(this);
            return true;
        }
        return super.onMousePressed(minecraft, mouseX, mouseY, mouseButton);
    }


    public int getBackgroundColor() {
        return backgroundColor;
    }

    public MKModal setDoBackground(boolean value) {
        doBackground = value;
        return this;
    }

    public boolean shouldDoBackground() {
        return doBackground;
    }

    @Override
    public void preDraw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        super.preDraw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        if (shouldDoBackground()) {
            fill(getX(), getY(), getWidth(), getHeight(), getBackgroundColor());
        }
    }
}
