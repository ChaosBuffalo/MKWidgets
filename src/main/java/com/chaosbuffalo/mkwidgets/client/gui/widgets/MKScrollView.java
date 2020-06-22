package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.chaosbuffalo.mkwidgets.client.gui.math.Vec2i;
import com.chaosbuffalo.mkwidgets.client.gui.screens.IMKScreen;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.Iterator;

public class MKScrollView extends MKWidget {

    private double offsetX;
    private double offsetY;
    private int screenWidth;
    private int screenHeight;
    private boolean isDragging;
    private boolean clipBounds;
    private double scaleFactor;
    private boolean doScrollLock;
    private int scrollMarginX;
    private int scrollMarginY;
    private boolean doScrollX;
    private boolean doScrollY;
    private double scrollVelocity;
    private boolean drawScrollBars;
    private static final int SCROLL_BAR_WIDTH = 1;

    public MKScrollView(int x, int y, int width, int height, double scaleFactor,
                        boolean clipBounds) {
        super(x, y, width, height);
        offsetX = 0;
        offsetY = 0;
        this.clipBounds = clipBounds;
        isDragging = false;
        this.scaleFactor = scaleFactor;
        this.doScrollLock = true;
        scrollMarginX = 0;
        scrollMarginY = 0;
        doScrollX = true;
        doScrollY = true;
        scrollVelocity = 1.0;
        drawScrollBars = true;
    }

    public MKScrollView(int x, int y, int width, int height, boolean clipBounds) {
        this(x, y, width, height, Minecraft.getInstance().getMainWindow().getGuiScaleFactor(), clipBounds);
    }


    public MKScrollView setScrollVelocity(double vel){
        scrollVelocity = vel;
        return this;
    }

    @Override
    public IMKWidget setScreen(IMKScreen screen) {
        super.setScreen(screen);
        if (screen != null) {
            screenWidth = screen.getWidth();
            screenHeight = screen.getHeight();
        }
        return this;
    }

    public MKScrollView setScrollLock(boolean state) {
        this.doScrollLock = state;
        return this;
    }

    public MKScrollView setDrawScrollBars(boolean value) {
        drawScrollBars = value;
        return this;
    }

    @Override
    public boolean onMouseScrollWheel(Minecraft minecraft, double mouseX, double mouseY, double amount) {
        double dY = amount * scrollVelocity;
        if (isScrollLockOn()) {
            IMKWidget child = getChild();
            if (child != null) {
                dY = lockScrollY(child, dY);
            }
        }
        offsetY += dY;
        return true;
    }

    public boolean shouldDrawScrollbars() {
        return drawScrollBars;
    }

    public boolean isClipBoundsEnabled() {
        return clipBounds;
    }

    public MKScrollView setOffsetX(double value) {
        offsetX = value;
        return this;
    }

    public int getIntOffsetX(){
        return (int)Math.round(offsetX);
    }

    public int getIntOffsetY(){
        return (int)Math.round(offsetY);
    }

    public MKScrollView setDoScrollX(boolean value) {
        doScrollX = value;
        return this;
    }

    public MKScrollView setDoScrollY(boolean value) {
        doScrollY = value;
        return this;
    }

    public boolean shouldScrollY() {
        return doScrollY;
    }

    public boolean shouldScrollX() {
        return doScrollX;
    }

    public MKScrollView setOffsetY(double value) {
        offsetY = value;
        return this;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public MKScrollView setClipBoundsEnabled(boolean value) {
        clipBounds = value;
        return this;
    }

    public boolean isScrollLockOn() {
        return doScrollLock;
    }

    public int getScrollMarginX() {
        return scrollMarginX;
    }

    public int getScrollMarginY() {
        return scrollMarginY;
    }

    public MKScrollView setScrollMarginX(int value) {
        scrollMarginX = value;
        return this;
    }

    public MKScrollView setScrollMarginY(int value) {
        scrollMarginY = value;
        return this;
    }

    public void centerContentX() {
        if (getChildren().size() > 0) {
            IMKWidget child = this.getChildren().getFirst();
            setOffsetX(getWidth() / 2.0 - child.getWidth() / 2.0 + getX());
        }
    }

    public void centerContentY() {
        if (getChildren().size() > 0) {
            IMKWidget child = this.getChildren().getFirst();
            setOffsetX(getHeight() / 2.0 - child.getHeight() / 2.0 + getY());
        }
    }

    public void setToTop() {
        setOffsetY(getY());
    }

    public void setToRight() {
        setOffsetX(getX());
    }

    @Override
    public void draw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef(getIntOffsetX(), getIntOffsetY(), 0);

        if (isClipBoundsEnabled()) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            int y1 = screenHeight - y - height - 1;
            GL11.glScissor((int) Math.round(x * scaleFactor), (int) Math.round(y1 * scaleFactor),
                    (int) Math.round(width * scaleFactor), (int) Math.round((height + 1) * scaleFactor));
        }
    }

    public boolean isContentWider() {
        IMKWidget child = getChild();
        if (child == null) {
            return false;
        }
        return child.getWidth() > getWidth();
    }

    public boolean isContentTaller() {
        IMKWidget child = getChild();
        if (child == null) {
            return false;
        }
        return child.getHeight() > getHeight();
    }

    @Override
    public void postDraw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        if (isClipBoundsEnabled()) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        GL11.glPopMatrix();
        if (shouldDrawScrollbars()) {
            IMKWidget child = getChild();
            if (child == null) {
                return;
            }
            if (isContentTaller()) {
                float ratio = (float) getHeight() / (float) child.getHeight();
                int heightForScrollbar = Math.round(ratio * getHeight()) + 1;
                float posRatio = (float) (getY() - getOffsetY()) / (float) child.getHeight();
                int pos = (int) (posRatio * getHeight());
                int barX = getX() + getWidth() - SCROLL_BAR_WIDTH;
                int barY = getY() + pos;
                mkFill(barX, barY, barX + SCROLL_BAR_WIDTH, barY + heightForScrollbar,
                        0x7DFFFFFF);
            }
            if (isContentWider()) {
                float ratio = (float) getWidth() / (float) child.getWidth();
                int widthForScrollbar = (int) (ratio * getWidth());
                float posRatio = (float) (getX() - getOffsetX()) / (float) child.getWidth();
                int pos = Math.round(posRatio * getWidth());
                int barX = getX() + pos;
                int barY = getY() + getHeight() - SCROLL_BAR_WIDTH;
                mkFill(barX, barY, barX + widthForScrollbar, barY + SCROLL_BAR_WIDTH,
                        0x7DFFFFFF);
            }
        }
    }

    @Override
    public Vec2i getParentCoords(Vec2i pos) {
        if (getParent() == null) {
            return pos.add(getIntOffsetX(), getIntOffsetY());
        } else {
            return getParent().getParentCoords(pos.add(getIntOffsetX(), getIntOffsetY()));
        }
    }

    @Override
    public void mouseHover(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!checkHovered(mouseX, mouseY)){
            clearHovered();
            return;
        }
        for (IMKWidget child : getChildren()) {
            if (child.isVisible() && child.isEnabled()) {
                child.mouseHover(mc, mouseX - getIntOffsetX(), mouseY - getIntOffsetY(), partialTicks);
            }
        }
        onMouseHover(mc, mouseX, mouseY, partialTicks);
    }

    @Override
    public void drawChildren(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        for (IMKWidget child : getChildren()) {
            if (child.isVisible()) {
                child.drawWidget(mc, mouseX - getIntOffsetX(), mouseY - getIntOffsetY(), partialTicks);
            }
        }
    }


    @Override
    public boolean mousePressed(Minecraft minecraft, double mouseX, double mouseY, int mouseButton) {
        if (!this.isEnabled() || !this.isVisible() || !this.isInBounds(mouseX, mouseY)) {
            return false;
        }
        Iterator<IMKWidget> it = getChildren().descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            if (child.mousePressed(minecraft, mouseX - offsetX, mouseY - offsetY, mouseButton)) {
                return true;
            }
        }
        return onMousePressed(minecraft, mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseDragged(Minecraft minecraft, double mouseX, double mouseY, int mouseButton, double dX, double dY) {
        if (!this.isEnabled() || !this.isVisible() || !this.isInBounds(mouseX, mouseY)) {
            return false;
        }
        Iterator<IMKWidget> it = getChildren().descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            if (child.mouseDragged(minecraft, mouseX - offsetX, mouseY - offsetY, mouseButton, dX, dY)) {
                return true;
            }
        }
        return onMouseDragged(minecraft, mouseX, mouseY, mouseButton, dX, dY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        if (!this.isEnabled() || !this.isVisible() || !this.isInBounds(mouseX, mouseY)) {
            return false;
        }
        Iterator<IMKWidget> it = getChildren().descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            if (child.mouseReleased(mouseX - offsetX, mouseY - offsetX, mouseButton)) {
                return true;
            }
        }
        return onMouseRelease(mouseX, mouseY, mouseButton);
    }


    @Nullable
    public IMKWidget getChild() {
        if (getChildren().size() > 0) {
            return this.getChildren().getFirst();
        } else {
            return null;
        }
    }

    @Override
    public boolean onMouseDragged(Minecraft minecraft, double mouseX, double mouseY, int mouseButton, double dX, double dY) {
        if (isDragging) {
            IMKWidget child = getChild();
            if (isScrollLockOn() && child != null) {
                dX = lockScrollX(child, dX);
                dY = lockScrollY(child, dY);
            }
            if (doScrollX) {
                offsetX += dX;
            }
            if (doScrollY) {
                offsetY += dY;
            }
            return true;
        }
        return false;
    }



    public double lockScrollX(IMKWidget child, double dX) {
        int scrollX = getX();
        int childWidth = child.getWidth();
        int scrollWidth = getWidth();
//        Log.info("ScrollX : %d, childWidth : %d, scrollWidth : %d, cameraX: %d, dX : %d", scrollX, childWidth, scrollWidth, offsetX, dX);
        if (childWidth < scrollWidth) {
            if (offsetX + dX < scrollX) {
                dX = scrollX - offsetX;
            } else if (offsetX + dX + childWidth > scrollX + scrollWidth) {
                dX = scrollX + scrollWidth - offsetX - childWidth;
            }
        } else {
            if (offsetX + dX > scrollX + getScrollMarginX()) {
                dX = scrollX - offsetX + getScrollMarginX();
            } else if (offsetX + dX + childWidth <= scrollX + scrollWidth - getScrollMarginX()) {
                dX = scrollX + scrollWidth - getScrollMarginX() - offsetX - childWidth;
            }
        }
        return dX;
    }

    public double lockScrollY(IMKWidget child, double dY) {
        int scrollY = getY();
        int childHeight = child.getHeight();
        int scrollHeight = getHeight();
        if (childHeight < scrollHeight) {
            return getY() - offsetY;
        } else {
            if (offsetY + dY > scrollY + getScrollMarginY()) {
                dY = scrollY - offsetY + getScrollMarginY();
            } else if (offsetY + dY + childHeight <= scrollY + scrollHeight - getScrollMarginY()) {
                dY = scrollY + scrollHeight - getScrollMarginY() - offsetY - childHeight;
            }
        }
        return dY;
    }

    @Override
    public boolean onMousePressed(Minecraft minecraft, double mouseX, double mouseY, int mouseButton) {
        isDragging = true;
        return true;
    }

    @Override
    public boolean onMouseRelease(double mouseX, double mouseY, int mouseButton) {
        if (isDragging) {
            isDragging = false;
            return true;
        }
        return false;
    }


    @Override
    public boolean addWidget(IMKWidget widget) {
        if (getChildren().size() > 0) {
            return false;
        }
        return super.addWidget(widget);
    }
}
