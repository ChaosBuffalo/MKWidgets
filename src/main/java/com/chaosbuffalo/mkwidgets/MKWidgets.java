package com.chaosbuffalo.mkwidgets;

import com.chaosbuffalo.mkwidgets.client.gui.example.TestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.awt.event.KeyEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mkwidgets")
@Mod.EventBusSubscriber
public class MKWidgets
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "mkwidgets";
    public static final KeyBinding openTestUi = new KeyBinding("key.mkwidgets.test.desc",
            GLFW.GLFW_KEY_APOSTROPHE,
            "key.mkwidgets.category");

    public MKWidgets() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event){
        ClientRegistry.registerKeyBinding(openTestUi);
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onEvent(InputEvent.KeyInputEvent event){
        if (openTestUi.isPressed()){
            Minecraft.getInstance().displayGuiScreen(new TestScreen(
                    new StringTextComponent("MK Widgets Test")));
        }
    }
}
