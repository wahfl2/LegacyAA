package com.wahfl.legacyaa.mixin;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.shader.Framebuffer.class)
public class FramebufferMixin {
    @Shadow
    public int framebufferWidth;
    @Shadow
    public int framebufferHeight;

    @Inject(method = "createFramebuffer", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER, target = "Lnet/minecraft/client/shader/Framebuffer;framebufferHeight:I"))
    public void createMixin(int p_147605_1_, int p_147605_2_, CallbackInfo ci) {
        this.framebufferWidth *= 2;
        this.framebufferHeight *= 2;
    }
}
