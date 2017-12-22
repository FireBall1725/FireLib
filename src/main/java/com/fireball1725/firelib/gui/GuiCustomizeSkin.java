/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.gui;

import com.fireball1725.firelib.util.Benihime;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

/**
 * Adapted from net.minecraft.client.gui.GuiCustomizeSkin overriding the cape button
 */
public class GuiCustomizeSkin extends GuiScreen {
    private final GuiScreen parentScreen;
    private String title;

    public GuiCustomizeSkin(GuiScreen parentScreenIn) {
        this.parentScreen = parentScreenIn;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        int i = 0;
        this.title = I18n.format("options.skinCustomisation.title");

        for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
            this.buttonList.add(new ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, enumplayermodelparts));
            ++i;
        }

        this.buttonList.add(new GuiOptionButton(199, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), GameSettings.Options.MAIN_HAND, this.mc.gameSettings.getKeyBinding(GameSettings.Options.MAIN_HAND)));
        ++i;

        if (i % 2 == 1) {
            ++i;
        }

        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("gui.done")));
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.gameSettings.saveOptions();
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (button.id == 199) {
                this.mc.gameSettings.setOptionValue(GameSettings.Options.MAIN_HAND, 1);
                button.displayString = this.mc.gameSettings.getKeyBinding(GameSettings.Options.MAIN_HAND);
                this.mc.gameSettings.sendSettingsToServer();
            } else if (button instanceof ButtonPart) {
                EnumPlayerModelParts enumplayermodelparts = ((ButtonPart) button).playerModelParts;
                this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
                button.displayString = this.getMessage(enumplayermodelparts);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private String getMessage(EnumPlayerModelParts playerModelParts) {
        String s;

        if (this.mc.gameSettings.getModelParts().contains(playerModelParts)) {
            s = I18n.format("options.on");
        } else {
            s = I18n.format("options.off");
            if (mc.player != null && playerModelParts == EnumPlayerModelParts.CAPE && Benihime.hasUser(mc.player.getUniqueID())) //todo: fix this so it works from main menu...
                s = "Benihime";
        }

        return playerModelParts.getName().getFormattedText() + ": " + s;
    }

    @SideOnly(Side.CLIENT)
    class ButtonPart extends GuiButton {
        private final EnumPlayerModelParts playerModelParts;

        private ButtonPart(int buttonId, int buttonX, int buttonY, int buttonWidth, int buttonHeight, EnumPlayerModelParts playerModelParts) {
            super(buttonId, buttonX, buttonY, buttonWidth, buttonHeight, getMessage(playerModelParts));
            this.playerModelParts = playerModelParts;
        }
    }
}