package com.chaosbuffalo.mkwidgets.client.gui.constraints;

import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKLayout;

import java.util.UUID;

public abstract class BaseConstraint implements IConstraint {
    private final UUID constraintId;

    public BaseConstraint(){
        constraintId = UUID.randomUUID();
    }

    public int getAvailableWidth(IMKLayout<?> layout){
        return layout.getWidth() - layout.getMarginRight() - layout.getMarginLeft();
    }

    public int getAvailableHeight(IMKLayout<?> layout){
        return layout.getHeight() - layout.getMarginTop() - layout.getMarginBot();
    }

    @Override
    public UUID getConstraintID() {
        return constraintId;
    }
}
