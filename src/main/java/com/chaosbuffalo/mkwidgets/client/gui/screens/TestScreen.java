package com.chaosbuffalo.mkwidgets.client.gui.screens;

import com.chaosbuffalo.mkwidgets.MKWidgets;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.*;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestScreen extends MKScreen {
    private int PANEL_WIDTH = 320;
    private int PANEL_HEIGHT = 256;
    private static final ResourceLocation BG_LOC = new ResourceLocation(MKWidgets.MODID,
            "textures/gui/background_320.png");

    public TestScreen(ITextComponent title) {
        super(title);
    }


    @Override
    public void setupScreen() {
        super.setupScreen();
        int xPos = width / 2 - PANEL_WIDTH / 2;
        int yPos = height / 2 - PANEL_HEIGHT / 2;
        MKWidget testRoot = new MKWidget(xPos, yPos, PANEL_WIDTH, PANEL_HEIGHT);
        testRoot.setDebugColor(0x00ffff00);
        addState("test", testRoot);
        MKScrollView scrollView = new MKScrollView(xPos + 78, yPos + 10, 120, 100, true);
        scrollView.setDebugColor(0x3fff0000);
        MKStackLayoutVertical verticalLayout = new MKStackLayoutVertical(0, 0, 120);
        verticalLayout.setDebugColor(0x3f00ff00);
        verticalLayout.doSetWidth(true).setPaddingBot(5).setMarginTop(5).setMarginRight(5).setMarginLeft(5).setMarginBot(5);
        for (int i = 0; i < 25; i++){
            MKText testText = new MKText(this.font, String.format("Test Text: %d", i));
            testText.setDebugColor(0x3f0000ff);
            verticalLayout.addWidget(testText);
        }
        scrollView.addWidget(verticalLayout);
        scrollView.centerContentX();
        testRoot.addWidget(scrollView);
        setState("test");
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        int panelWidth = PANEL_WIDTH;
        int panelHeight = PANEL_HEIGHT;
        int xPos = width / 2 - panelWidth / 2;
        int yPos = height / 2 - panelHeight / 2;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getInstance().getTextureManager().bindTexture(BG_LOC);
        RenderSystem.disableLighting();
        MKAbstractGui.mkBlitUVSizeSame(xPos, yPos, 0, 0, panelWidth, panelHeight, 512, 512);
        super.render(mouseX, mouseY, partialTicks);
        RenderSystem.enableLighting();
    }
}
