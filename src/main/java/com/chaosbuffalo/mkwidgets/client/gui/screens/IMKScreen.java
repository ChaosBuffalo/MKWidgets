package com.chaosbuffalo.mkwidgets.client.gui.screens;


import com.chaosbuffalo.mkwidgets.client.gui.instructions.IInstruction;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKModal;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;
import com.chaosbuffalo.mkwidgets.client.gui.instructions.HoveringTextInstruction;

public interface IMKScreen {

    void addModal(IMKModal modal);

    void closeModal(IMKModal modal);

    void addPostRenderInstruction(IInstruction instruction);

    void addPreDrawRunnable(Runnable runnable);

    void removePreDrawRunnable(Runnable runnable);

    void clearPreDrawRunnables();

    void addRestoreStateCallbacks();

    void addPostSetupCallback(Runnable callback);

    void removeState(String name);

    void addState(String name, IMKWidget root);

    void pushState(String newState);

    String popState();

    String getState();

    void addWidget(IMKWidget widget);

    void removeWidget(IMKWidget widget);

    boolean containsWidget(IMKWidget widget);

    void clear();

    void clearModals();

    void clearWidgets();

    int getWidth();

    int getHeight();

}
