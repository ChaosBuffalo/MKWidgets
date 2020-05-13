package com.chaosbuffalo.mkwidgets.client.gui.screens;

import com.chaosbuffalo.mkwidgets.MKWidgets;
import com.chaosbuffalo.mkwidgets.client.gui.instructions.IInstruction;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKModal;
import com.chaosbuffalo.mkwidgets.client.gui.widgets.IMKWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

import java.util.*;

public class MKScreen extends Screen implements IMKScreen {
    public final ArrayDeque<IMKWidget> children;
    public static String NO_STATE = "NO_STATE";
    private final HashMap<Integer, IMKWidget> selectedWidgets;
    public boolean firstRender;
    private final Stack<String> stateStack;
    public HashMap<String, IMKWidget> states;
    private final ArrayList<Runnable> postSetupCallbacks;
    private final ArrayList<Runnable> preDrawRunnables;
    private final ArrayList<IInstruction> postRenderInstructions;
    private final ArrayDeque<IMKModal> modals;

    public MKScreen(ITextComponent title) {
        super(title);
        firstRender = true;
        children = new ArrayDeque<>();
        states = new HashMap<>();
        stateStack = new Stack<>();
        postSetupCallbacks = new ArrayList<>();
        selectedWidgets = new HashMap<>();
        preDrawRunnables = new ArrayList<>();
        postRenderInstructions = new ArrayList<>();
        modals = new ArrayDeque<>();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollAmount) {
        Iterator<IMKModal> modalIt = modals.descendingIterator();
        while (modalIt.hasNext()) {
            IMKModal child = modalIt.next();
            if (!child.isVisible()) {
                continue;
            }
            if (child.mouseScrollWheel(this.minecraft, mouseX, mouseY, scrollAmount)) {
                return true;
            }
        }
        Iterator<IMKWidget> it = children.descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            if (!child.isVisible()) {
                continue;
            }
            if (child.mouseScrollWheel(this.minecraft, mouseX, mouseY, scrollAmount)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void addPostRenderInstruction(IInstruction instruction) {
        postRenderInstructions.add(instruction);
    }

    @Override
    public void addModal(IMKModal modal) {
        modal.inheritScreen(this);
        modal.setWidth(width);
        modal.setHeight(height);
        this.modals.add(modal);
    }

    @Override
    public void closeModal(IMKModal modal) {
        if (this.modals.removeIf((x) -> x.getId().equals(modal.getId()))) {
            if (modal.getOnCloseCallback() != null) {
                modal.getOnCloseCallback().run();
            }
            modal.inheritScreen(null);
        }
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        MKWidgets.LOGGER.info("Resize event called?");
        for (IMKModal modal : modals){
            closeModal(modal);
        }
        flagNeedSetup();
    }

    @Override
    public void addPreDrawRunnable(Runnable runnable) {
        preDrawRunnables.add(runnable);
    }

    @Override
    public void removePreDrawRunnable(Runnable runnable) {
        preDrawRunnables.remove(runnable);
    }

    @Override
    public void clearPreDrawRunnables() {
        preDrawRunnables.clear();
    }

    @Override
    public void addRestoreStateCallbacks() {
        String state = popState();
        addPostSetupCallback(() -> pushState(state));
    }

    @Override
    public void addPostSetupCallback(Runnable callback) {
        postSetupCallbacks.add(callback);
    }

    @Override
    public void addState(String name, IMKWidget root) {
        this.states.put(name, root);
    }

    @Override
    public void removeState(String name) {
        this.states.remove(name);
    }

    @Override
    public void pushState(String newState) {
        if (newState.equals(getState())) {
            return;
        }
        if (handleStateTransition(newState, getState())){
            stateStack.push(newState);
        }
    }

    private boolean handleStateTransition(String newState, String oldState){
        if (newState.equals(NO_STATE) || states.containsKey(newState)) {
            if (!oldState.equals(NO_STATE)) {
                this.removeWidget(states.get(oldState));
            }
            if (!newState.equals(NO_STATE)) {
                this.addWidget(states.get(newState));
            }
            return true;
        } else {
            MKWidgets.LOGGER.warn("Tried to set screen state to: {}, but state doesn't exist.", newState);
            return false;
        }
    }

    @Override
    public String popState() {
        String oldState = stateStack.pop();
        String newState = getState();
        handleStateTransition(newState, oldState);
        return oldState;
    }

    @Override
    public String getState() {
        if (stateStack.empty()){
            return NO_STATE;
        }
        return stateStack.peek();
    }

    private void runSetup() {
        setupScreen();
        for (Runnable cb : postSetupCallbacks) {
            cb.run();
        }
        postSetupCallbacks.clear();
    }

    public void setupScreen() {
        clearWidgets();
        clearPreDrawRunnables();
        this.states.clear();
    }

    public void flagNeedSetup() {
        firstRender = true;
        addRestoreStateCallbacks();
    }

    @Override
    public void addWidget(IMKWidget widget) {
        widget.inheritScreen(this);
        this.children.add(widget);
    }

    @Override
    public void removeWidget(IMKWidget widget) {
        if (containsWidget(widget)) {
            if (children.removeIf((x) -> x.getId().equals(widget.getId()))) {
                widget.inheritScreen(null);
            }
        }
    }

    @Override
    public boolean containsWidget(IMKWidget widget) {
        for (IMKWidget child : children) {
            if (widget.getId().equals(child.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (firstRender) {
            runSetup();
            firstRender = false;
        }
        for (Runnable runnable : preDrawRunnables) {
            runnable.run();
        }
        for (IMKWidget child : children){
            child.mouseHover(this.minecraft, mouseX, mouseY, partialTicks);
        }
        for (IMKWidget child : children) {
            if (child.isVisible()) {
                child.drawWidget(this.minecraft, mouseX, mouseY, partialTicks);
            }
        }
        for (IMKModal modal : modals) {
            if (modal.isVisible()) {
                modal.drawWidget(this.minecraft, mouseX, mouseY, partialTicks);
            }
        }
        for (IInstruction instruction : postRenderInstructions) {
            instruction.draw(getMinecraft().fontRenderer, this.width, this.height);
        }
        postRenderInstructions.clear();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int mouseButton,
                                double dX, double dY) {
        if (selectedWidgets.get(mouseButton) != null) {
            if (selectedWidgets.get(mouseButton).mouseDragged(this.minecraft, mouseX, mouseY, mouseButton, dX, dY)) {
                return true;
            }
        }
        return super.mouseDragged(mouseX, mouseY, mouseButton, dX, dY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        Iterator<IMKModal> modalIt = modals.descendingIterator();
        while (modalIt.hasNext()) {
            IMKModal child = modalIt.next();
            if (!child.isVisible()) {
                continue;
            }
            IMKWidget clickHandler = child.mousePressed(this.minecraft, mouseX, mouseY, mouseButton);
            if (clickHandler != null) {
                selectedWidgets.put(mouseButton, clickHandler);
                return true;
            }
        }
        Iterator<IMKWidget> it = children.descendingIterator();
        while (it.hasNext()) {
            IMKWidget child = it.next();
            if (!child.isVisible()) {
                continue;
            }
            IMKWidget clickHandler = child.mousePressed(this.minecraft, mouseX, mouseY, mouseButton);
            if (clickHandler != null) {
                selectedWidgets.put(mouseButton, clickHandler);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void clearWidgets() {
        for (IMKWidget widget : children) {
            widget.inheritScreen(null);
        }
        children.clear();
    }

    @Override
    public void clearModals() {
        for (IMKModal modal : modals) {
            modal.inheritScreen(null);
        }
        modals.clear();
    }

    @Override
    public void clear() {
        clearWidgets();
        clearModals();
        postRenderInstructions.clear();
    }


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        if (selectedWidgets.get(mouseButton) != null) {
            IMKWidget selected = selectedWidgets.get(mouseButton);
            selectedWidgets.remove(mouseButton);
            if (selected.mouseReleased(mouseX, mouseY, mouseButton)) {
                return true;
            }
        }
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }
}
