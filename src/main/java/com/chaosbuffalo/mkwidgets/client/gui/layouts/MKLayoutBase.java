package com.chaosbuffalo.mkwidgets.client.gui.layouts;

import com.chaosbuffalo.mkwidgets.client.gui.constraints.IConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.MKWidgetBase;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class MKLayoutBase extends MKWidgetBase implements IMKLayout {
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBot;
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int marginBot;
    private boolean needsRecompute;
    private HashMap<UUID, ArrayList<IConstraint>> constraints;

    public MKLayoutBase(int x, int y, int width, int height){
        super(x, y, width, height);
        constraints = new HashMap<>();
        needsRecompute = false;
    }

    public void flagNeedsRecompute(){
        needsRecompute = true;
    }

    public void recomputeChildren() {
        setupLayoutStartState();
        int i = 0;
        for (IMKWidget child : getChildren()) {
            doLayout(child, i);
            i++;
        }
        postLayout();
    }

    @Override
    public void addConstraintToWidget(IConstraint constraint, IMKWidget widget) {
        if (!constraints.containsKey(widget.getId())){
            constraints.put(widget.getId(), new ArrayList<>());
        }
        ArrayList<IConstraint> widgetConstraints = constraints.get(widget.getId());
        widgetConstraints.add(constraint);
    }

    @Override
    public void removeConstraintFromWidget(IConstraint constraint, IMKWidget widget) {
        if (constraints.containsKey(widget.getId())){
            ArrayList<IConstraint> widgetConstraints = constraints.get(widget.getId());
            widgetConstraints.removeIf((con) -> con.getConstraintID().equals(constraint.getConstraintID()));
        }
    }

    public void applyConstraints(IMKWidget widget, int widgetIndex){
        if (constraints.containsKey(widget.getId())){
            ArrayList<IConstraint> widgetConstraints = constraints.get(widget.getId());
            for (IConstraint constraint : widgetConstraints){
                constraint.applyConstraint(this, widget, widgetIndex);
            }
        }

    }

    @Override
    public void removeWidget(IMKWidget widget) {
        super.removeWidget(widget);
        constraints.remove(widget.getId());
    }

    @Override
    public void clearWidgetConstraints(IMKWidget widget) {
        if (constraints.containsKey(widget.getId())){
            constraints.remove(widget.getId());
        }
    }

    public void manualRecompute(){
        recomputeChildren();
        needsRecompute = false;
    }

    @Override
    public void drawWidget(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (needsRecompute){
            recomputeChildren();
            needsRecompute = false;
        }
        handleHoverDetection(mouseX, mouseY, partialTicks);
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        if (doDrawWidgetBbox()){
            drawWidgetBbox(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        }
        preDraw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        draw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        int i = 0;
        for (IMKWidget child : getChildren()) {
            if (child.isVisible()) {
                child.drawWidget(mc, mouseX, mouseY, partialTicks);
            }
            i++;
        }
        postDraw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
        handleLongHoverDraw(mc, x, y, width, height, mouseX, mouseY, partialTicks);
    }


    @Override
    public IMKLayout setMarginTop(int value) {
        marginTop = value;
        return this;
    }

    @Override
    public int getMarginTop() {
        return marginTop;
    }

    @Override
    public IMKLayout setMarginBot(int value) {
        marginBot = value;
        return this;
    }

    @Override
    public int getMarginBot() {
        return marginBot;
    }

    @Override
    public IMKLayout setMarginLeft(int value) {
        marginLeft = value;
        return this;
    }

    @Override
    public int getMarginLeft() {
        return marginLeft;
    }

    @Override
    public IMKLayout setMarginRight(int value) {
        marginRight = value;
        return this;
    }

    @Override
    public int getMarginRight() {
        return marginRight;
    }

    @Override
    public IMKLayout setPaddingTop(int value) {
        paddingTop = value;
        return this;
    }

    @Override
    public int getPaddingTop() {
        return paddingTop;
    }

    @Override
    public IMKLayout setPaddingBot(int value) {
        paddingBot = value;
        return this;
    }

    @Override
    public int getPaddingBot() {
        return paddingBot;
    }

    @Override
    public IMKLayout setPaddingLeft(int value) {
        paddingLeft = value;
        return this;
    }

    @Override
    public int getPaddingLeft() {
        return paddingLeft;
    }

    @Override
    public IMKLayout setPaddingRight(int value) {
        paddingRight = value;
        return this;
    }

    @Override
    public int getPaddingRight() {
        return paddingRight;
    }
}
