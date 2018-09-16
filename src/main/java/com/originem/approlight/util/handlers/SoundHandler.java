package com.originem.approlight.util.handlers;

import com.originem.approlight.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler {
    public static SoundEvent BLOCK_CRYSTAL_CHISEL_DIG;

    public static void registerSounds() {
        BLOCK_CRYSTAL_CHISEL_DIG = registerSound("block.chisel.dig");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(Reference.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
