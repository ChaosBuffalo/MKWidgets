package com.chaosbuffalo.mkwidgets.client.gui.layouts;

import com.chaosbuffalo.mkwidgets.client.gui.constraints.IConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;

public abstract class MKConstraintLayout extends MKLayoutBase {


    public MKConstraintLayout(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void layoutWidget(IMKWidget widget, int index) {
        applyConstraints(widget, index);
        postLayoutWidget(widget, index);
    }

    public void postLayoutWidget(IMKWidget widget, int index){

    }

    protected void skipComputeSetWidth(int newWidth){
        super.setWidth(newWidth);
    }

    protected void skipComputeSetHeight(int newHeight){
        super.setHeight(newHeight);
    }

    @Override
    public IMKWidget setWidth(int newWidth) {
        super.setWidth(newWidth);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKWidget setHeight(int newHeight) {
        super.setHeight(newHeight);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKWidget setX(int newX) {
        super.setX(newX);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKWidget setY(int newY) {
        super.setY(newY);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout setPaddingTop(int value) {
        super.setPaddingTop(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout setPaddingBot(int value) {
        super.setPaddingBot(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout setPaddingRight(int value) {
        super.setPaddingRight(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public boolean addWidget(IMKWidget widget) {
        super.addWidget(widget);
        flagNeedsRecompute();
        return true;
    }

    @Override
    public void removeWidget(IMKWidget widget) {
        super.removeWidget(widget);
        flagNeedsRecompute();
    }

    @Override
    public IMKLayout setPaddingLeft(int value) {
        super.setPaddingLeft(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout setMarginRight(int value) {
        super.setMarginRight(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout setMarginLeft(int value) {
        super.setMarginLeft(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout setMarginBot(int value) {
        super.setMarginBot(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout setMarginTop(int value) {
        super.setMarginTop(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public void addConstraintToWidget(IConstraint constraint, IMKWidget widget) {
        super.addConstraintToWidget(constraint, widget);
        flagNeedsRecompute();
    }

    @Override
    public void removeConstraintFromWidget(IConstraint constraint, IMKWidget widget) {
        super.removeConstraintFromWidget(constraint, widget);
        flagNeedsRecompute();
    }

    @Override
    public void clearWidgetConstraints(IMKWidget widget) {
        super.clearWidgetConstraints(widget);
        flagNeedsRecompute();
    }

    @Override
    public void clearWidgets() {
        super.clearWidgets();
        flagNeedsRecompute();
    }
}

