/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.renderer.layers;

import com.fireball1725.firelib.ModInfo;
import com.fireball1725.firelib.renderer.obj.AdvancedModelLoader;
import com.fireball1725.firelib.renderer.obj.IModelCustom;
import com.fireball1725.firelib.util.Benihime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.UUID;

public class LayerSword implements LayerRenderer<AbstractClientPlayer> {
    private static final ResourceLocation swordObj = new ResourceLocation(ModInfo.MOD_ID, "textures/sword/benihime.obj");
    private static final ResourceLocation swordTex = new ResourceLocation(ModInfo.MOD_ID, "textures/sword/benihime.png");
    private final RenderPlayer renderPlayer;
    private final IModelCustom swordModel;

    @SideOnly(Side.CLIENT)
    public LayerSword(RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
        swordModel = AdvancedModelLoader.loadModel(swordObj);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        UUID playerUUID = entitylivingbaseIn.getGameProfile().getId();

        if (!Benihime.hasUser(playerUUID) || entitylivingbaseIn.isInvisible())
            return;

        if (entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE))
            return;

        if (entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(0.0f, 0.2f, 0.0f);
            GlStateManager.rotate(28.6479f, 1.0f, 0.0f, 0.0f);
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(swordTex);

        GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-0.25f, 0.14f, -0.05f);
        GlStateManager.rotate(-38.0f, 0.0f, 1.0f, 0.0f);
        swordModel.renderAllExcept("Benihime_Tassle");

        if (entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(0.25f, -0.14f, 0.05f);
            GlStateManager.rotate(38.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-28.6479f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(-0.25f, 0.14f, -0.05f);
            GlStateManager.rotate(-38.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.026f, -0.021f, -0.101f);
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks);
        double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks);
        double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks);
        float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
        double d3 = MathHelper.sin(f * 0.017453292f);
        double d4 = -MathHelper.cos(f * 0.017453292f);
        float f1 = MathHelper.clamp((float) d1 * 50.0f, -6.0f, 180.0f);
        float f2 = Math.max(0, (float) (d0 * d3 + d2 * d4) * 100.0f);
        float f3 = (float) (d0 * d4 - d2 * d3) * 100.0f;
        float f4 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
        f1 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0f) * 32.0f * f4;
        GlStateManager.rotate(-51f, 0f, 1f, 0f);
        GlStateManager.translate(0.2017f, 0.0242f, 0.235f);
        GlStateManager.rotate(Math.max(-f2 / 2.0f - f1, -180), 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f3 / 2.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-f3 / 2.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0585f, -0.026f, -0.3f);
        swordModel.renderOnly("Benihime_Tassle");
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
