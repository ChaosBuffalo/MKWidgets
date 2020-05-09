package com.chaosbuffalo.mkwidgets.client.gui.widgets;

public interface IMKModal<T extends IMKModal<T>> extends IMKWidget<T> {

    IMKModal<T> setCloseOnClickOutside(boolean value);

    boolean shouldCloseOnClickOutside();

    IMKModal<T> setOnCloseCallback(Runnable callback);

    Runnable getOnCloseCallback();

}
