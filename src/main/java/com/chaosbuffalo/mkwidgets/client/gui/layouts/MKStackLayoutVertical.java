package com.chaosbuffalo.mkwidgets.client.gui.layouts;


import com.chaosbuffalo.mkwidgets.client.gui.constraints.LayoutRelativeWidthConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.constraints.MarginConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.constraints.VerticalStackConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;

public class MKStackLayoutVertical extends MKConstraintLayout {

    private int currentHeight;
    private boolean doSetWidth;

    public MKStackLayoutVertical(int x, int y, int width) {
        super(x, y, width, 0);
        currentHeight = 0;
        doSetWidth = false;
    }

    public MKStackLayoutVertical doSetWidth(boolean value) {
        doSetWidth = value;
        return this;
    }

    public boolean shouldSetWidth() {
        return doSetWidth;
    }

    @Override
    public void setupLayoutStartState() {
        currentHeight = 0;
        currentHeight += getMarginBot() + getMarginTop();
    }

    @Override
    public void postConstraintHandler(IMKWidget widget, int index) {
        super.postConstraintHandler(widget, index);
        if (index > 0){
            currentHeight += getPaddingBot() + getPaddingTop();
        }
        currentHeight += widget.getHeight();
    }

    @Override
    public void postLayout() {
        skipComputeSetHeight(currentHeight);
    }

    @Override
    public boolean addWidget(IMKWidget widget) {
        super.addWidget(widget);
        addConstraintToWidget(new MarginConstraint(MarginConstraint.MarginType.LEFT), widget);
        addConstraintToWidget(new VerticalStackConstraint(), widget);
        if (shouldSetWidth()){
            addConstraintToWidget(new LayoutRelativeWidthConstraint(1.0f), widget);
        }
        return true;
    }


}
