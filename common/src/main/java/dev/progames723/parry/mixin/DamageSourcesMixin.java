package dev.progames723.parry.mixin;

import dev.progames723.parry.ParryDamageSources;
import dev.progames723.parry.interface_injection.ExtDamageSources;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public class DamageSourcesMixin implements ExtDamageSources {
	@Unique public ParryDamageSources parry$parryDamageSources;
	@Override @Unique public ParryDamageSources parrySources() {
		return this.parry$parryDamageSources;
	}
	@Inject(method = "<init>", at = @At(value = "RETURN"))
	private void init(RegistryAccess registryAccess, CallbackInfo ci) {
		parry$parryDamageSources = new ParryDamageSources(registryAccess);
	}
}
