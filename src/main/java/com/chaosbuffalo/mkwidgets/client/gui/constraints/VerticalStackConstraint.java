package com.chaosbuffalo.mkwidgets.client.gui.constraints;

import com.chaosbuffalo.mkwidgets.client.gui.layouts.IMKLayout;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;

public class VerticalStackConstraint extends BaseConstraint {

    public VerticalStackConstraint() {
            super();
    }

    @Override
    public void applyConstraint(IMKLayout layout, IMKWidget widget, int widgetIndex) {
        int yPos;
        if (widgetIndex == 0){
           yPos = layout.getY() + layout.getMarginTop();
        } else {
            IMKWidget previous = layout.getChild(widgetIndex - 1);
            if (previous != null){
                yPos = previous.getY() + previous.getHeight() + layout.getPaddingBot() + layout.getPaddingTop();
            } else {
                yPos = layout.getY() + layout.getMarginTop();
            }
        }
        widget.setY(yPos);
    }
}
