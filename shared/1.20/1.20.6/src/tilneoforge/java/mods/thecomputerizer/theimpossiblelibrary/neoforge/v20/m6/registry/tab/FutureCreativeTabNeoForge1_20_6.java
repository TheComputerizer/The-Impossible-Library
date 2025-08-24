package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.registry.tab.FutureCreativeTabNeoForge1_20;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FutureCreativeTabNeoForge1_20_6 extends FutureCreativeTabNeoForge1_20 {
    
    public FutureCreativeTabNeoForge1_20_6(ResourceLocationAPI<?> registryName) {
        super(registryName);
    }
    
    @Override protected boolean canEventAccept(Object event, ItemStack stack) {
        return !((BuildCreativeModeTabContentsEvent)event).getEntries().contains(stack);
    }
    
    @Override protected Consumer<ItemStack> eventEntryAcceptor(Object event) {
        return ((BuildCreativeModeTabContentsEvent)event)::accept;
    }
    
    @Override protected <T> void register(Object event, ResourceKey<? extends Registry<T>> registryKey,
            ResourceLocation name, Supplier<T> valueSupplier) {
        ((RegisterEvent)event).register(registryKey,name,valueSupplier);
    }
}