package com.chaosbuffalo.mkwidgets.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class MKImage extends MKWidget {
    private ResourceLocation imageLoc;
    private int texU;
    private int texV;
    private int texWidth;
    private int texHeight;
    private int sourceWidth;
    private int sourceHeight;

    public MKImage(int x, int y, int width, int height, ResourceLocation imageLoc) {
        this(x, y, width, height, width, height, 0, 0, width, height, imageLoc);
    }

    public MKImage(int x, int y, int width, int height, int sourceWidth,
                   int sourceHeight, int imageU, int imageV,
                   int imageWidth, int imageHeight,
                   ResourceLocation imageLoc){
        super(x, y, width, height);
        this.imageLoc = imageLoc;
        texU = imageU;
        texV = imageV;
        this.sourceHeight = sourceHeight;
        this.sourceWidth = sourceWidth;
        texWidth = imageWidth;
        texHeight = imageHeight;
    }

    public MKImage setTexU(int value) {
        texU = value;
        return this;
    }

    public int getSourceWidth() {
        return sourceWidth;
    }

    public int getSourceHeight() {
        return sourceHeight;
    }

    public MKImage setSourceHeight(int sourceHeight) {
        this.sourceHeight = sourceHeight;
        return this;
    }

    public MKImage setSourceWidth(int sourceWidth) {
        this.sourceWidth = sourceWidth;
        return this;
    }

    public MKImage setTexV(int value) {
        texV = value;
        return this;
    }

    public MKImage setImageLoc(ResourceLocation imageLoc) {
        this.imageLoc = imageLoc;
        return this;
    }

    public ResourceLocation getImageLoc() {
        return imageLoc;
    }

    public int getTexU() {
        return texU;
    }

    public int getTexV() {
        return texV;
    }

    public MKImage setTexWidth(int value) {
        texWidth = value;
        return this;
    }

    public MKImage setTexHeight(int value) {
        texHeight = value;
        return this;
    }

    public int getTexWidth() {
        return texWidth;
    }

    public int getTexHeight() {
        return texHeight;
    }

    @Override
    public void draw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.disableDepthTest();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(getImageLoc());
        mkBlitUVSizeDifferent(getX(), getY(), getWidth(), getHeight(),
                (float)getTexU(), (float)getTexV(), getTexWidth(), getTexHeight(), getSourceWidth(), getSourceHeight());
        RenderSystem.enableDepthTest();
    }
}
