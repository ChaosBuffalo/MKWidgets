package com.chaosbuffalo.mkwidgets.client.gui.constraints;

import com.chaosbuffalo.mkwidgets.client.gui.layouts.IMKLayout;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;

public class MarginConstraint extends BaseConstraint{

    public enum MarginType {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
    private final MarginType type;

    public MarginConstraint(MarginType type){
        super();
        this.type = type;
    }

    @Override
    public void applyConstraint(IMKLayout layout, IMKWidget widget, int widgetIndex) {
        switch (type){
            case TOP:
                widget.setY(layout.getY() + layout.getMarginTop());
                break;
            case BOTTOM:
                widget.setY(layout.getY() + layout.getHeight() - layout.getMarginBot() - widget.getHeight());
                break;
            case LEFT:
                widget.setX(layout.getX() + layout.getMarginLeft());
                break;
            case RIGHT:
                widget.setX(layout.getX() + layout.getWidth() - layout.getMarginRight() - widget.getWidth());
                break;
        }
    }
}
