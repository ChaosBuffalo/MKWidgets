package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.chaosbuffalo.mkwidgets.client.gui.constraints.IConstraint;

public abstract class MKConstraintLayout extends MKLayoutBase<MKConstraintLayout> {


    public MKConstraintLayout(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void doLayout(IMKWidget<?> widget, int index) {
        applyConstraints(widget, index);
        postConstraintHandler(widget, index);
    }

    public void postConstraintHandler(IMKWidget<?> widget, int index){

    }

    protected void skipComputeSetWidth(int newWidth){
        super.setWidth(newWidth);
    }

    protected void skipComputeSetHeight(int newHeight){
        super.setHeight(newHeight);
    }

    @Override
    public IMKWidget<MKConstraintLayout> setWidth(int newWidth) {
        super.setWidth(newWidth);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKWidget<MKConstraintLayout> setHeight(int newHeight) {
        super.setHeight(newHeight);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKWidget<MKConstraintLayout> setX(int newX) {
        super.setX(newX);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKWidget<MKConstraintLayout> setY(int newY) {
        super.setY(newY);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout<MKConstraintLayout> setPaddingTop(int value) {
        super.setPaddingTop(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout<MKConstraintLayout> setPaddingBot(int value) {
        super.setPaddingBot(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout<MKConstraintLayout> setPaddingRight(int value) {
        super.setPaddingRight(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public boolean addWidget(IMKWidget<?> widget) {
        super.addWidget(widget);
        flagNeedsRecompute();
        return true;
    }

    @Override
    public void removeWidget(IMKWidget<?> widget) {
        super.removeWidget(widget);
        flagNeedsRecompute();
    }

    @Override
    public IMKLayout<MKConstraintLayout> setPaddingLeft(int value) {
        super.setPaddingLeft(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout<MKConstraintLayout> setMarginRight(int value) {
        super.setMarginRight(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout<MKConstraintLayout> setMarginLeft(int value) {
        super.setMarginLeft(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout<MKConstraintLayout> setMarginBot(int value) {
        super.setMarginBot(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public IMKLayout<MKConstraintLayout> setMarginTop(int value) {
        super.setMarginTop(value);
        flagNeedsRecompute();
        return this;
    }

    @Override
    public void addConstraintToWidget(IConstraint constraint, IMKWidget<?> widget) {
        super.addConstraintToWidget(constraint, widget);
        flagNeedsRecompute();
    }

    @Override
    public void removeConstraintFromWidget(IConstraint constraint, IMKWidget<?> widget) {
        super.removeConstraintFromWidget(constraint, widget);
        flagNeedsRecompute();
    }

    @Override
    public void clearWidgetConstraints(IMKWidget<?> widget) {
        super.clearWidgetConstraints(widget);
        flagNeedsRecompute();
    }

    @Override
    public void clearWidgets() {
        super.clearWidgets();
        flagNeedsRecompute();
    }
}

