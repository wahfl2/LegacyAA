package com.wahfl.legacyaa.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.util.Session;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.net.Proxy;

@Mixin(net.minecraft.client.Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public int displayWidth;
    @Shadow
    public int displayHeight;
    @Shadow
    private int tempDisplayWidth;
    @Shadow
    private int tempDisplayHeight;

    @Shadow
    private boolean fullscreen;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initMixin(Session p_i1103_1_, int p_i1103_2_, int p_i1103_3_, boolean p_i1103_4_,
                           boolean p_i1103_5_, File p_i1103_6_, File p_i1103_7_, File p_i1103_8_,
                           Proxy p_i1103_9_, String p_i1103_10_, Multimap p_i1103_11_, String p_i1103_12_,
                           CallbackInfo ci) {
        this.tempDisplayWidth *= 2;
        this.tempDisplayHeight *= 2;
        System.out.println("Hewwo!!");
    }

    @Inject(method="startGame", at=@At(value="INVOKE", target = "Lorg/lwjgl/opengl/Display;setResizable(Z)V"))
    private void startGameMixin(CallbackInfo info) throws LWJGLException {
        legacyAA$doubleDimensions();

        if (!this.fullscreen) {
            Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
        }
    }

    @Inject(method = "updateDisplayMode", at = @At("TAIL"))
    private void updateDisplayMixin(CallbackInfo ci) {
        legacyAA$doubleDimensions();
    }

    @Inject(method = "func_147120_f", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lorg/lwjgl/opengl/Display;getHeight()I"))
    private void idkMixin(CallbackInfo ci) {
        legacyAA$doubleDimensions();
    }

    @Inject(method = "toggleFullscreen", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lorg/lwjgl/opengl/DisplayMode;getHeight()I"))
    private void toggleFullscreenMixin(CallbackInfo ci) {
        legacyAA$doubleDimensions();
    }

    @Unique
    private void legacyAA$doubleDimensions() {
        this.displayWidth *= 2;
        this.displayHeight *= 2;
    }
}
