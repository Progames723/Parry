package dev.progames723.parry.interface_injection;

import dev.progames723.parry.ParryDamageSources;

public interface ExtDamageSources {
	default ParryDamageSources parrySources() {
		throw new AssertionError();
	}
}
