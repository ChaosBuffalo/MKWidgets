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

    public MKImage(int x, int y, int width, int height, ResourceLocation imageLoc) {
        super(x, y, width, height);
        this.imageLoc = imageLoc;
        texU = 0;
        texV = 0;
        texWidth = width;
        texHeight = height;
    }

    public MKImage setTexU(int value) {
        texU = value;
        return this;
    }

    public MKImage setTexV(int value) {
        texV = value;
        return this;
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
        mc.getTextureManager().bindTexture(imageLoc);
        blit(getX(), getY(), (float)getTexU(), (float)getTexV(), getWidth(), getHeight(),
                getTexWidth(), getTexHeight());
        RenderSystem.enableDepthTest();
    }
}
