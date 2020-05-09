package com.chaosbuffalo.mkwidgets.client.gui.constraints;

import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKLayout;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;

public class HorizontalStackConstraint extends BaseConstraint {

    public HorizontalStackConstraint() {
        super();
    }

    @Override
    public void applyConstraint(IMKLayout<?> layout, IMKWidget<?> widget, int widgetIndex) {
        int xPos;
        if (widgetIndex == 0){
            xPos = layout.getX() + layout.getMarginLeft();
        } else {
            IMKWidget<?> previous = layout.getChild(widgetIndex - 1);
            if (previous != null){
                xPos = previous.getX() + previous.getWidth() + layout.getPaddingRight() + layout.getPaddingLeft();
            } else {
                xPos = layout.getX() + layout.getMarginLeft();
            }
        }
        widget.setX(xPos);
    }
}
