package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.chaosbuffalo.mkwidgets.client.gui.screens.IMKScreen;
import com.chaosbuffalo.mkwidgets.client.gui.math.Vec2i;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

public interface IMKWidget {

    default boolean addWidget(IMKWidget widget) {
        widget.setParent(this);
        widget.inheritScreen(getScreen());
        getChildren().add(widget);
        return true;
    }

    default void inheritScreen(IMKScreen screen){
        setScreen(screen);
        for (IMKWidget widget : getChildren()){
            widget.inheritScreen(screen);
        }
    }

    default void removeWidget(IMKWidget widget) {
        if (widget.getParent() != null && widget.getParent().getId().equals(this.getId())) {
            getChildren().removeIf((x) -> x.getId().equals(widget.getId()));
            widget.setParent(null);
            widget.inheritScreen(null);
        }
    }

    UUID getId();

    LinkedList<IMKWidget> getChildren();

    IMKWidget setParent(IMKWidget parent);

    IMKWidget setScreen(IMKScreen screen);

    @Nullable
    IMKWidget getParent();

    @Nullable
    IMKScreen getScreen();

    IMKWidget setHeight(int newHeight);

    IMKWidget setWidth(int newWidth);

    IMKWidget setX(int newX);

    IMKWidget setY(int newY);

    int getWidth();

    int getHeight();

    int getX();

    int getY();

    default int getRight() {
        return getX() + getWidth();
    }

    default int getTop() {
        return getY();
    }

    default int getLeft() {
        return getX();
    }

    default int getBottom() {
        return getY() + getHeight();
    }

    default Vec2i getParentCoords(Vec2i pos) {
        if (getParent() == null) {
            return pos;
        } else {
            return getParent().getParentCoords(pos);
        }
    }

    int getLongHoverTicks();

    IMKWidget setLongHoverTicks(int ticks);

    boolean skipBoundsCheck();

    IMKWidget setSkipBoundsCheck(boolean value);

    boolean isHovered();

    IMKWidget setHovered(boolean value);

    boolean isVisible();

    IMKWidget setVisible(boolean value);

    boolean isEnabled();

    IMKWidget setEnabled(boolean value);

    default boolean isInBounds(double x, double y) {
        if (skipBoundsCheck()) {
            return true;
        }
        return x >= getX() && y >= getY() && x < getRight() && y < getBottom();
    }

    default void preDraw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks)
    {

    }

    default void draw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks)
    {

    }

    default void postDraw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks)
    {

    }

    default void longHoverDraw(Minecraft mc, int x, int y, int width, int height,
                               int mouseX, int mouseY, float partialTicks) {

    }

    float getHoveredTicks();

    void setHoveredTicks(float value);

    default boolean checkHovered(int mouseX, int mouseY) {
        return isVisible() && isEnabled() && isInBounds(mouseX, mouseY);
    }

    default void handleLongHoverDraw(Minecraft mc, int x, int y, int width, int height,
                                    int mouseX, int mouseY, float partialTicks) {
        if (isHovered() && getHoveredTicks() > getLongHoverTicks()) {
            longHoverDraw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        }
    }

    default void handleHoverDetection(int mouseX, int mouseY, float partialTicks) {
        boolean hovered = checkHovered(mouseX, mouseY);
        if (hovered) {
            setHoveredTicks(getHoveredTicks() + partialTicks);
        } else {
            setHoveredTicks(0);
        }
        setHovered(hovered);
    }

    boolean doDrawDebugBounds();

    default void drawDebugBounds(Minecraft mc, int x, int y, int width, int height, int mouseX,
                                 int mouseY, float partialTicks){

    }

    void setDrawDebug(boolean value);

    void setDebugColor(int color);

    int getDebugColor();

    default void drawWidget(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        handleHoverDetection(mouseX, mouseY, partialTicks);
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        if (doDrawDebugBounds()){
            drawDebugBounds(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        }
        preDraw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        draw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        for (IMKWidget child : getChildren()) {
            if (child.isVisible()) {
                child.drawWidget(mc, mouseX, mouseY, partialTicks);
            }
        }
        postDraw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        handleLongHoverDraw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
    }

    default void clearWidgets() {
        for (IMKWidget widget : getChildren()) {
            widget.setParent(null);
        }
        getChildren().clear();
    }

    @Nullable
    default IMKWidget getChild(int index){
        return getChildren().get(index);
    }

    default boolean onMouseScrollWheel(Minecraft minecraft, double mouseX, double mouseY, double distance) {
        return false;
    }

    default boolean mouseScrollWheel(Minecraft minecraft, double mouseX, double mouseY, double distance) {
        if (!this.isEnabled() || !this.isVisible() || !this.isInBounds(mouseX, mouseY)) {
            return false;
        }
        Iterator<IMKWidget> it = getChildren().descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            if (child.mouseScrollWheel(minecraft, mouseX, mouseY, distance)) {
                return true;
            }
        }
        return onMouseScrollWheel(minecraft, mouseX, mouseY, distance);
    }

    default boolean mouseDragged(Minecraft minecraft, double mouseX, double mouseY, int mouseButton,
                                 double dX, double dY) {
        Iterator<IMKWidget> it = getChildren().descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            if (child.mouseDragged(minecraft, mouseX, mouseY, mouseButton, dX, dY)) {
                return true;
            }
        }
        return onMouseDragged(minecraft, mouseX, mouseY, mouseButton, dX, dY);
    }

    default boolean onMouseDragged(Minecraft minecraft, double mouseX, double mouseY, int mouseButton,
                                   double dX, double dY) {
        return false;
    }

    default boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        Iterator<IMKWidget> it = getChildren().descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            if (child.mouseReleased(mouseX, mouseY, mouseButton)) {
                return true;
            }
        }
        return onMouseRelease(mouseX, mouseY, mouseButton);
    }

    default boolean onMouseRelease(double mouseX, double mouseY, int mouseButton) {
        return false;
    }

    default IMKWidget mousePressed(Minecraft minecraft, double mouseX, double mouseY, int mouseButton) {
        if (!this.isEnabled() || !this.isVisible() || !this.isInBounds(mouseX, mouseY)) {
            return null;
        }
        Iterator<IMKWidget> it = getChildren().descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            IMKWidget result = child.mousePressed(minecraft, mouseX, mouseY, mouseButton);
            if (result != null) {
                return result;
            }
        }
        if (onMousePressed(minecraft, mouseX, mouseY, mouseButton)) {
            return this;
        }
        return null;
    }

    default boolean onMousePressed(Minecraft minecraft, double mouseX, double mouseY, int mouseButton) {
        return false;
    }

}
