package com.chaosbuffalo.mkwidgets.client.gui.example;

import com.chaosbuffalo.mkwidgets.MKWidgets;
import com.chaosbuffalo.mkwidgets.client.gui.constraints.CenterXConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.constraints.LayoutRelativeYPosConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.constraints.MarginConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.constraints.VerticalStackConstraint;
import com.chaosbuffalo.mkwidgets.client.gui.layouts.MKLayout;
import com.chaosbuffalo.mkwidgets.client.gui.layouts.MKStackLayoutVertical;
import com.chaosbuffalo.mkwidgets.client.gui.screens.MKScreen;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.*;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestScreen extends MKScreen {
    private final int PANEL_WIDTH = 320;
    private final int PANEL_HEIGHT = 240;
    private static final ResourceLocation BG_LOC = new ResourceLocation(MKWidgets.MODID,
            "textures/gui/background_320.png");

    public TestScreen(ITextComponent title) {
        super(title);
    }

    public MKLayout getIntro(int xPos, int yPos){
        MKLayout root = new MKLayout(xPos, yPos, PANEL_WIDTH, PANEL_HEIGHT);
        root.setMargins(5, 5, 5, 5);
        MKText introText = new MKText(font, "Welcome to the MK Widgets toolkit demo.");
        introText.setIsCentered(true).setMultiline(true).setWidth(PANEL_WIDTH/2);
        root.addWidget(introText);
        root.addConstraintToWidget(new LayoutRelativeYPosConstraint(.6f), introText);
        root.addConstraintToWidget(new CenterXConstraint(), introText);
        MKButton introButton = new MKButton("Screen 2");
        root.addWidget(introButton);
        root.addConstraintToWidget(new MarginConstraint(MarginConstraint.MarginType.BOTTOM), introButton);
        root.addConstraintToWidget(new CenterXConstraint(), introButton);
        introButton.setPressedCallback((button, mouseButton) -> {
            pushState("testList");
            return true;
        });
        return root;
    }

    public MKLayout textListDemo(int xPos, int yPos){
        MKLayout root = new MKLayout(xPos, yPos, PANEL_WIDTH, PANEL_HEIGHT);
        root.setMargins(5, 5, 5, 5);
        root.setPaddingBot(10);
        root.setPaddingTop(10);
        MKText titleText = new MKText(font, "Scrollable List Demo");
        root.addWidget(titleText);
        root.addConstraintToWidget(new MarginConstraint(MarginConstraint.MarginType.TOP), titleText);
        root.addConstraintToWidget(new CenterXConstraint(), titleText);
        MKScrollView scrollView = new MKScrollView(0, 0, 120, 100, true);
        root.addWidget(scrollView);
        root.addConstraintToWidget(new VerticalStackConstraint(), scrollView);
        root.addConstraintToWidget(new CenterXConstraint(), scrollView);
        MKStackLayoutVertical verticalLayout = new MKStackLayoutVertical(0, 0, 120);
        verticalLayout.doSetChildWidth(true).setPaddingBot(5).setMarginTop(5).setMarginRight(5).setMarginLeft(5).setMarginBot(5);
        for (int i = 0; i < 25; i++){
            MKText testText = new MKText(this.font, String.format("Test Text: %d", i));
            testText.setIsCentered(true);
            testText.setDebugColor(0x3f0000ff);
            verticalLayout.addWidget(testText);
        }
        scrollView.addWidget(verticalLayout);
        // we need to resolve constraints so we can center scrollview content properly
        root.manualRecompute();
        scrollView.centerContentX();
        scrollView.setToTop();
        MKButton back = new MKButton("Back to Main");
        root.addWidget(back);
        root.addConstraintToWidget(new MarginConstraint(MarginConstraint.MarginType.BOTTOM), back);
        root.addConstraintToWidget(new CenterXConstraint(), back);
        back.setPressedCallback((button, mouseButton) -> {
            popState();
            return true;
        });
        return root;
    }

    @Override
    public void setupScreen() {
        super.setupScreen();
        int xPos = width / 2 - PANEL_WIDTH / 2;
        int yPos = height / 2 - PANEL_HEIGHT / 2;
        MKLayout intro = getIntro(xPos, yPos);
        addState("intro", intro);
        MKLayout testList = textListDemo(xPos, yPos);
        addState("testList", testList);
        pushState("intro");
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        int xPos = width / 2 - PANEL_WIDTH / 2;
        int yPos = height / 2 - PANEL_HEIGHT / 2;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getInstance().getTextureManager().bindTexture(BG_LOC);
        RenderSystem.disableLighting();
        MKAbstractGui.mkBlitUVSizeSame(xPos, yPos, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, 512, 512);
        super.render(mouseX, mouseY, partialTicks);
        RenderSystem.enableLighting();
    }
}
