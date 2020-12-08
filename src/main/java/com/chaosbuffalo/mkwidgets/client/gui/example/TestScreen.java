package com.chaosbuffalo.mkwidgets.client.gui.example;

import com.chaosbuffalo.mkwidgets.MKWidgets;
import com.chaosbuffalo.mkwidgets.client.gui.constraints.*;
import com.chaosbuffalo.mkwidgets.client.gui.layouts.MKLayout;
import com.chaosbuffalo.mkwidgets.client.gui.layouts.MKStackLayoutHorizontal;
import com.chaosbuffalo.mkwidgets.client.gui.layouts.MKStackLayoutVertical;
import com.chaosbuffalo.mkwidgets.client.gui.screens.MKScreen;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestScreen extends MKScreen {
    private final int PANEL_WIDTH = 320;
    private final int PANEL_HEIGHT = 240;
    private static final ResourceLocation BG_LOC = new ResourceLocation(MKWidgets.MODID,
            "textures/gui/background_320.png");
    private static final ResourceLocation CB_LOGO = new ResourceLocation(MKWidgets.MODID,
            "textures/gui/chaosbuffalologo.png");
    private MKModal testPopup;

    public TestScreen(ITextComponent title) {
        super(title);
    }

    public MKLayout getIntro(int xPos, int yPos){
        MKLayout root = new MKLayout(xPos, yPos, PANEL_WIDTH, PANEL_HEIGHT);
        root.setMargins(5, 5, 5, 5);
        root.setPaddingTop(5).setPaddingBot(5);
        MKText introText = new MKText(font, "Welcome to the MK Widgets toolkit demo.");
        introText.setIsCentered(true).setMultiline(true).setWidth(PANEL_WIDTH/2);
        root.addWidget(introText);
        root.addConstraintToWidget(new LayoutRelativeYPosConstraint(.3f), introText);
        root.addConstraintToWidget(new CenterXConstraint(), introText);
        MKButton textListDemo = new MKButton("Text List Demo");
        root.addWidget(textListDemo);
        root.addConstraintToWidget(new VerticalStackConstraint(), textListDemo);
        root.addConstraintToWidget(new CenterXConstraint(), textListDemo);
        textListDemo.setPressedCallback((button, mouseButton) -> {
            pushState("testList");
            return true;
        });
        MKButton imageBoxDemo = new MKButton("Image Box Demo");
        root.addWidget(imageBoxDemo);
        root.addConstraintToWidget(new VerticalStackConstraint(), imageBoxDemo);
        root.addConstraintToWidget(new CenterXConstraint(), imageBoxDemo);
        imageBoxDemo.setPressedCallback((button, mouseButton) -> {
            pushState("imageBox");
            return true;
        });
        MKButton popupDemo = new MKButton("Popup Demo");
        root.addWidget(popupDemo);
        root.addConstraintToWidget(new VerticalStackConstraint(), popupDemo);
        root.addConstraintToWidget(new CenterXConstraint(), popupDemo);
        popupDemo.setPressedCallback((button, mouseButton) -> {
            pushState("popupDemo");
            return true;
        });
        MKButton tooltipDemo = new MKButton("Tooltip Demo");
        root.addWidget(tooltipDemo);
        root.addConstraintToWidget(new VerticalStackConstraint(), tooltipDemo);
        root.addConstraintToWidget(new CenterXConstraint(), tooltipDemo);
        tooltipDemo.setPressedCallback((button, mouseButton) -> {
            pushState("tooltipDemo");
            return true;
        });
        return root;
    }

    private MKLayout getToolTipTest(int xPos, int yPos){
        MKText textWithLongHover = new MKText(font, "This text will have tooltip.");
        textWithLongHover.setTooltip("This is a tooltip.");
        MKLayout root = getRootWithTitle(xPos, yPos, "Tooltip Test");
        root.addWidget(textWithLongHover);
        root.addConstraintToWidget(new CenterXConstraint(), textWithLongHover);
        root.addConstraintToWidget(new LayoutRelativeYPosConstraint(.5f), textWithLongHover);
        addBackButton(root);
        return root;

    }

    private MKLayout getPopupTest(int xPos, int yPos){
        MKLayout root = getRootWithTitle(xPos, yPos, "Popup Test");
        MKButton openPopup = new MKButton("Open Popup");
        root.addWidget(openPopup);
        root.addConstraintToWidget(new VerticalStackConstraint(), openPopup);
        root.addConstraintToWidget(new CenterXConstraint(), openPopup);
        testPopup = new MKModal();
        MKLayout popupContents = new MKLayout(xPos, yPos, PANEL_WIDTH, PANEL_HEIGHT);
        testPopup.addWidget(popupContents);
        MKButton closePopup = new MKButton("Close Popup");
        popupContents.addWidget(closePopup);
        popupContents.addConstraintToWidget(new CenterXConstraint(), closePopup);
        popupContents.addConstraintToWidget(new LayoutRelativeYPosConstraint(.5f), closePopup);
        closePopup.setPressedCallback((button, mouseButton) ->{
            this.closeModal(testPopup);
            return true;
        });
        openPopup.setPressedCallback((button, mouseButton) -> {
            this.addModal(testPopup);
            return true;
        });
        addBackButton(root);
        return root;
    }

    private MKLayout getImageBox(int xPos, int yPos, int width, int height, ResourceLocation imageLoc,
                                 MarginConstraint.MarginType verticalMargin,
                                 MarginConstraint.MarginType horizontalMargin){

        MKLayout root = new MKLayout(xPos, yPos, width, height);
        // we pass in the original image size here
        MKImage image = new MKImage(xPos, yPos, 400, 400, imageLoc);
        root.addWidget(image);
        root.addConstraintToWidget(new LayoutRelativeWidthConstraint(.5f), image);
        root.addConstraintToWidget(new LayoutRelativeHeightConstraint(.5f), image);
        root.addConstraintToWidget(new MarginConstraint(verticalMargin), image);
        root.addConstraintToWidget(new MarginConstraint(horizontalMargin), image);
        root.setDrawDebug(true);
        root.setDebugColor(0xff00ffff);
        return root;
    }

    private MKLayout get2ImageBoxRow(int height, ResourceLocation image1, ResourceLocation image2,
                                     MarginConstraint.MarginType verticalMargin){
        MKStackLayoutHorizontal row = new MKStackLayoutHorizontal(0, 0, height);
        row.setPaddings(5, 5, 5, 5);
        MKLayout img1 = getImageBox(0, 0, height, height, image1, verticalMargin,
                MarginConstraint.MarginType.LEFT);
        MKLayout img2 = getImageBox(0, 0, height, height, image2, verticalMargin,
                MarginConstraint.MarginType.RIGHT);
        row.addWidget(img1);
        row.addWidget(img2);
        return row;
    }

    private MKLayout getImageBoxLayout(int rowHeight, ResourceLocation image){
        MKStackLayoutVertical layout = new MKStackLayoutVertical(0, 0, rowHeight * 2 + 30);
        layout.setMargins(10, 10, 10, 10);
        layout.setPaddings(5, 5, 5, 5);
        MKLayout row1 = get2ImageBoxRow(rowHeight, image, image, MarginConstraint.MarginType.TOP);
        MKLayout row2 = get2ImageBoxRow(rowHeight, image, image, MarginConstraint.MarginType.BOTTOM);
        layout.addWidget(row1);
        layout.addWidget(row2);
        return layout;
    }

    private MKLayout getRootWithTitle(int xPos, int yPos, String title){
        MKLayout root = new MKLayout(xPos, yPos, PANEL_WIDTH, PANEL_HEIGHT);
        root.setMargins(5, 5, 5, 5);
        root.setPaddingBot(10);
        root.setPaddingTop(10);
        MKText titleText = new MKText(font, title);
        root.addWidget(titleText);
        root.addConstraintToWidget(new MarginConstraint(MarginConstraint.MarginType.TOP), titleText);
        root.addConstraintToWidget(new CenterXConstraint(), titleText);
        return root;
    }

    private MKLayout imageBoxDemo(int xPos, int yPos){
        MKLayout root = getRootWithTitle(xPos, yPos, "Image Box Demo");
        MKLayout imageBox = getImageBoxLayout(50, CB_LOGO);
        imageBox.manualRecompute();
        root.addWidget(imageBox);
        root.addConstraintToWidget(new CenterXConstraint(), imageBox);
        root.addConstraintToWidget(new VerticalStackConstraint(), imageBox);
        addBackButton(root);
        return root;
    }

    private void addBackButton(MKLayout layout){
        MKButton back = new MKButton("Back to Main");
        layout.addWidget(back);
        layout.addConstraintToWidget(new MarginConstraint(MarginConstraint.MarginType.BOTTOM), back);
        layout.addConstraintToWidget(new CenterXConstraint(), back);
        back.setPressedCallback((button, mouseButton) -> {
            popState();
            return true;
        });
    }


    public MKLayout textListDemo(int xPos, int yPos){
        MKLayout root = getRootWithTitle(xPos, yPos, "Scrollable List Demo");
        MKScrollView scrollView = new MKScrollView(0, 0, 120, 100, true);
        root.addWidget(scrollView);
        scrollView.setScrollVelocity(3.0);
        root.addConstraintToWidget(new VerticalStackConstraint(), scrollView);
        root.addConstraintToWidget(new CenterXConstraint(), scrollView);
        MKStackLayoutVertical verticalLayout = new MKStackLayoutVertical(0, 0, 120);
        verticalLayout.doSetChildWidth(true).setPaddingBot(5).setMarginTop(5).setMarginRight(5).setMarginLeft(5).setMarginBot(5);
        for (int i = 0; i < 25; i++){
            String buttonText = String.format("Test Text: %d", i);
            MKText testText = new MKText(this.font, buttonText);
            testText.setTooltip(buttonText);
            testText.setIsCentered(true);
            testText.setDebugColor(0x3f0000ff);
            verticalLayout.addWidget(testText);
        }
        scrollView.addWidget(verticalLayout);
        // we need to resolve constraints so we can center scrollview content properly
        root.manualRecompute();
        scrollView.centerContentX();
        scrollView.setToTop();
        addBackButton(root);
        return root;
    }

    @Override
    public void setupScreen() {
        super.setupScreen();
        int xPos = width / 2 - PANEL_WIDTH / 2;
        int yPos = height / 2 - PANEL_HEIGHT / 2;
        addState("intro", () -> getIntro(xPos, yPos));
        addState("testList", () -> textListDemo(xPos, yPos));
        addState("imageBox", () -> imageBoxDemo(xPos, yPos));
        addState("popupDemo", () -> getPopupTest(xPos, yPos));
        addState("tooltipDemo", () -> getToolTipTest(xPos, yPos));
        pushState("intro");
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int xPos = width / 2 - PANEL_WIDTH / 2;
        int yPos = height / 2 - PANEL_HEIGHT / 2;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getInstance().getTextureManager().bindTexture(BG_LOC);
        RenderSystem.disableLighting();
        MKAbstractGui.mkBlitUVSizeSame(matrixStack, xPos, yPos, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, 512, 512);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.enableLighting();
    }
}
