package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.chaosbuffalo.mkwidgets.client.gui.screens.IMKScreen;
import com.chaosbuffalo.mkwidgets.client.gui.UIConstants;
import net.minecraft.client.Minecraft;


import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.UUID;

public abstract class MKWidgetBase<T extends MKWidgetBase<T>> extends MKAbstractGui implements IMKWidget<T> {
    private UUID id;
    private final LinkedList<IMKWidget<?>> children;
    private IMKWidget<?> parent;
    private IMKScreen<?> screen;
    private int width;
    private int height;
    private int x;
    private int y;
    private int longHoverTicks;
    private boolean skipBoundsCheck;
    private boolean hovered;
    private float hoveredTicks;
    private boolean enabled;
    private boolean visible;
    private int debugColor;

    public MKWidgetBase(int x, int y, int width, int height){
        id = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        children = new LinkedList<>();
        parent = null;
        hovered = false;
        hoveredTicks = 0;
        longHoverTicks = UIConstants.DEFAULT_LONG_HOVER_TICKS;
        enabled = true;
        skipBoundsCheck = false;
        visible = true;
        screen = null;
        debugColor = 0x3fffffff;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public LinkedList<IMKWidget<?>> getChildren() {
        return children;
    }

    @Override
    public IMKWidget<T> setParent(IMKWidget<?> parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public IMKWidget<T> setScreen(IMKScreen<?> screen) {
        this.screen = screen;
        return this;
    }

    @Nullable
    @Override
    public IMKWidget<?> getParent() {
        return parent;
    }

    @Nullable
    @Override
    public IMKScreen<?> getScreen() {
        return screen;
    }

    @Override
    public IMKWidget<T> setHeight(int newHeight) {
        this.height = newHeight;
        return this;
    }

    @Override
    public void setDebugColor(int color) {
        debugColor = color;
    }

    @Override
    public int getDebugColor() {
        return debugColor;
    }

    @Override
    public void drawWidgetBbox(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        mkFill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), getDebugColor());
    }



    @Override
    public IMKWidget<T> setWidth(int newWidth) {
        this.width = newWidth;
        return this;
    }

    @Override
    public IMKWidget<T> setX(int newX) {
        this.x = newX;
        return this;
    }

    @Override
    public IMKWidget<T> setY(int newY) {
        this.y = newY;
        return this;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getLongHoverTicks() {
        return longHoverTicks;
    }

    @Override
    public IMKWidget<T> setLongHoverTicks(int ticks) {
        this.longHoverTicks = ticks;
        return this;
    }

    @Override
    public boolean skipBoundsCheck() {
        return skipBoundsCheck;
    }

    @Override
    public IMKWidget<T> setSkipBoundsCheck(boolean skipBoundsCheck) {
        this.skipBoundsCheck = skipBoundsCheck;
        return this;
    }

    @Override
    public boolean isHovered() {
        return hovered;
    }

    @Override
    public IMKWidget<T> setHovered(boolean value) {
        this.hovered = value;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public IMKWidget<T> setVisible(boolean value) {
        this.visible = value;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public IMKWidget<T> setEnabled(boolean value) {
        this.enabled = value;
        return this;
    }

    @Override
    public float getHoveredTicks() {
        return hoveredTicks;
    }

    @Override
    public void setHoveredTicks(float value) {
        hoveredTicks = value;
    }


}
