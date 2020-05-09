package com.chaosbuffalo.mkwidgets.client.gui.constraints;

import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKLayout;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;

public class LayoutRelativeHeightConstraint extends BaseConstraint {
    private final float heightScale;

    public LayoutRelativeHeightConstraint(float heightScale){
        super();
        this.heightScale = heightScale;
    }

    @Override
    public void applyConstraint(IMKLayout<?> layout, IMKWidget<?> widget, int widgetIndex) {
        float availableSpace = layout.getHeight() - layout.getMarginTop() - layout.getMarginBot();
        widget.setHeight(Math.round(availableSpace * heightScale));
    }
}
