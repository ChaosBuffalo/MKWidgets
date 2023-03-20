package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class MKTextFieldWidget extends MCWidgetContainer{
    private BiConsumer<MKTextFieldWidget,String> callback;
    private Predicate<String> validator;
    private BiConsumer<MKTextFieldWidget, String> onSubmit;

    public MKTextFieldWidget(FontRenderer font, int x, int y, int width, int height, ITextComponent title) {
        super(x, y, width, height, new TextFieldWidget(font, x, y, width, height, title), true);
        TextFieldWidget wid = getContainedWidget();
        wid.setResponder(this::onTextChange);
        wid.setFilter(this::validateText);
    }

    public MKTextFieldWidget setTextChangeCallback(BiConsumer<MKTextFieldWidget, String> callback) {
        this.callback = callback;
        return this;
    }

    public MKTextFieldWidget setTextValidator(Predicate<String> validator){
        this.validator = validator;
        return this;
    }

    public String getText(){
        return getContainedWidget().getValue();
    }

    public MKTextFieldWidget setSubmitCallback(BiConsumer<MKTextFieldWidget, String> cb){
        this.onSubmit = cb;
        return this;
    }

    protected boolean validateText(String text){
        if (validator != null){
            return validator.test(text);
        } else {
            return true;
        }
    }

    public void setText(String text){
        getContainedWidget().setValue(text);
    }

    protected void onSubmitText(String text){
        if (onSubmit != null){
            onSubmit.accept(this, text);
        }
    }

    protected void onTextChange(String text){
        if (callback != null){
            callback.accept(this, text);
        }
    }

    @Override
    public TextFieldWidget getContainedWidget() {
        return (TextFieldWidget) super.getContainedWidget();
    }

    @Override
    public void onFocus() {
        super.onFocus();
        getContainedWidget().setFocus(true);
    }

    @Override
    public boolean keyPressed(Minecraft minecraft, int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER){
            onSubmitText(getContainedWidget().getValue());
            if (getScreen() != null) {
                getScreen().setFocus(null);
            }
            return true;
        } else {
            return super.keyPressed(minecraft, keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void onFocusLost() {
        super.onFocusLost();
        onSubmitText(getContainedWidget().getValue());
        getContainedWidget().setHighlightPos(getContainedWidget().getCursorPosition());
        getContainedWidget().setFocus(false);
    }
}
