package com.chaosbuffalo.mkwidgets.client.gui.widgets;


import com.chaosbuffalo.mkwidgets.client.gui.constraints.*;

public class MKStackLayoutHorizontal extends MKConstraintLayout {
    private int currentWidth;
    private boolean doSetHeight;

    public MKStackLayoutHorizontal(int x, int y, int height) {
        super(x, y, 0, height);
        currentWidth = 0;
        doSetHeight = false;
    }

    public MKStackLayoutHorizontal doSetHeight(boolean value) {
        doSetHeight = value;
        return this;
    }

    public boolean shouldSetHeight() {
        return doSetHeight;
    }

    @Override
    public void setupLayoutStartState() {
        currentWidth = 0;
        currentWidth += getMarginLeft() + getMarginRight();
    }

    @Override
    public void postConstraintHandler(IMKWidget<?> widget, int index) {
        super.postConstraintHandler(widget, index);
        if (index > 0){
            currentWidth += getPaddingLeft() + getPaddingRight();
        }
        currentWidth += widget.getWidth();
    }

    @Override
    public void postLayout() {
        skipComputeSetWidth(currentWidth);
    }

    @Override
    public boolean addWidget(IMKWidget<?> widget) {
        super.addWidget(widget);
        addConstraintToWidget(new MarginConstraint(MarginConstraint.MarginType.TOP), widget);
        addConstraintToWidget(new HorizontalStackConstraint(), widget);
        if (shouldSetHeight()){
            addConstraintToWidget(new LayoutRelativeHeightConstraint(1.0f), widget);
        }
        return true;
    }
}
