package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiFunction;

public class MKButton extends MKWidget {
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    public ITextComponent buttonText;
    public BiFunction<MKButton, Integer, Boolean> pressedCallback;
    public static final int DEFAULT_HEIGHT = 20;
    public static final int DEFAULT_WIDTH = 200;

    public MKButton(int x, int y, String buttonText) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, buttonText);
    }

    public MKButton(int x, int y, ITextComponent buttonText) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, buttonText);
    }

    public MKButton(String buttonText, int width, int height) {
        this(0, 0, width, height, buttonText);
    }

    public MKButton(ITextComponent buttonText, int width, int height) {
        this(0, 0, width, height, buttonText);
    }

    public MKButton(String buttonText) {
        this(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, buttonText);
    }

    public MKButton(ITextComponent buttonText) {
        this(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, buttonText);
    }

    public MKButton(String buttonText, int height) {
        this(0, 0, DEFAULT_WIDTH, height, buttonText);
    }

    public MKButton(ITextComponent buttonText, int height) {
        this(0, 0, DEFAULT_WIDTH, height, buttonText);
    }

    public MKButton(int width, String buttonText) {
        this(0, 0, width, DEFAULT_HEIGHT, buttonText);
    }

    public MKButton(int width, ITextComponent buttonText) {
        this(0, 0, width, DEFAULT_HEIGHT, buttonText);
    }

    public MKButton(int x, int y, int width, int height, String buttonText) {
        this(x, y, width, height, new StringTextComponent(buttonText));
    }

    public MKButton(int x, int y, int width, int height, ITextComponent buttonText) {
        super(x, y, width, height);
        setCanFocus(true);
        this.buttonText = buttonText;
    }

    public MKButton setPressedCallback(BiFunction<MKButton, Integer, Boolean> callback) {
        this.pressedCallback = callback;
        return this;
    }

    @Override
    public boolean onMousePressed(Minecraft minecraft, double mouseX, double mouseY, int mouseButton) {
        if (pressedCallback != null) {
            if (pressedCallback.apply(this, mouseButton)) {
                playPressSound(minecraft.getSoundHandler());
                return true;
            }
        }
        return false;
    }

    public int getHoverState(boolean isHovering) {
        int i = 1;
        if (!this.isEnabled()) {
            i = 0;
        } else if (isHovering || (getScreen() != null && this.equals(getScreen().getFocus()))) {
            i = 2;
        }
        return i;
    }

    @Override
    public boolean keyPressed(Minecraft minecraft, int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_E){
            if (pressedCallback != null) {
                if (pressedCallback.apply(this, GLFW.GLFW_MOUSE_BUTTON_1)) {
                    playPressSound(minecraft.getSoundHandler());
                    return true;
                }
            }
        }
        return super.keyPressed(minecraft, keyCode, scanCode, modifiers);
    }

    @Override
    public void draw(MatrixStack matrixStack, Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        FontRenderer fontrenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        int i = getHoverState(isHovered());
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.blit(
                matrixStack,
                this.getX(),
                this.getY(),
                0,
                46 + i * 20,
                this.getWidth() / 2, this.getHeight());
        this.blit(
                matrixStack,
                this.getX() + this.getWidth() / 2,
                this.getY(),
                200 - this.getWidth() / 2,
                46 + i * 20,
                this.getWidth() / 2, this.getHeight());
        int j = 14737632;
        if (!this.isEnabled()) {
            j = 10526880;
        } else if (isHovered()) {
            j = 16777120;
        }
        drawCenteredString(matrixStack, fontrenderer, this.buttonText,
                this.getX() + this.getWidth() / 2,
                this.getY() + (this.getHeight() - 8) / 2, j);
    }

    public void playPressSound(SoundHandler soundHandler) {
        soundHandler.play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

}
