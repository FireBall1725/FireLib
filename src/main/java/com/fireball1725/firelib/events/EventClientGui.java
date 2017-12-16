package com.fireball1725.firelib.events;

import com.fireball1725.firelib.gui.GuiCustomizeSkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventClientGui {
    @SubscribeEvent
    public void onButtonClickPre(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.getGui() instanceof GuiOptions) {
            if (event.getButton().id == 110) {
                GuiCustomizeSkin guiCustomizeSkin = new GuiCustomizeSkin(Minecraft.getMinecraft().currentScreen);
                event.getGui().mc.displayGuiScreen(guiCustomizeSkin);
                event.getButton().playPressSound(Minecraft.getMinecraft().getSoundHandler());
                event.setCanceled(true);
            }
        }
    }
}
