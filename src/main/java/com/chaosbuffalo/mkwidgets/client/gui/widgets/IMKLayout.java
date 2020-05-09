package com.chaosbuffalo.mkwidgets.client.gui.widgets;


import com.chaosbuffalo.mkwidgets.client.gui.constraints.IConstraint;

public interface IMKLayout<T extends IMKLayout<T>> extends IMKWidget<T> {

    IMKLayout<T> setMarginTop(int value);

    int getMarginTop();

    default IMKLayout<T> setMargins(int left, int right, int top, int bottom){
        return setMarginLeft(left).setMarginRight(right).setMarginTop(top).setMarginBot(bottom);
    }

    default void doLayout(IMKWidget<?> widget, int index) {
    }

    void addConstraintToWidget(IConstraint constraint, IMKWidget<?> widget);

    void clearWidgetConstraints(IMKWidget<?> widget);

    void removeConstraintFromWidget(IConstraint constraint, IMKWidget<?> widget);

    default void setupLayoutStartState() {
    }

    default void postLayout() {

    }

    default IMKLayout<T> setPaddings(int left, int right, int top, int bottom){
        return setPaddingLeft(left).setPaddingRight(right).setPaddingTop(top).setPaddingBot(bottom);
    }

    IMKLayout<T> setMarginBot(int value);

    int getMarginBot();

    IMKLayout<T> setMarginLeft(int value);

    int getMarginLeft();

    IMKLayout<T> setMarginRight(int value);

    int getMarginRight();

    IMKLayout<T> setPaddingTop(int value);

    int getPaddingTop();

    IMKLayout<T> setPaddingBot(int value);

    int getPaddingBot();

    IMKLayout<T> setPaddingLeft(int value);

    int getPaddingLeft();

    IMKLayout<T> setPaddingRight(int value);

    int getPaddingRight();

}
