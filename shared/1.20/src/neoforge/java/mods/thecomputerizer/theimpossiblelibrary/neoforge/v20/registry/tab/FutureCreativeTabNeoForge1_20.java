package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;
import static net.minecraft.world.item.CreativeModeTab.Row.TOP;

public abstract class FutureCreativeTabNeoForge1_20 extends FutureCreativeTab<CreativeModeTab> {
    
    private final List<ItemStack> suppliedItems = new ArrayList<>();
    
    public FutureCreativeTabNeoForge1_20(ResourceLocationAPI<?> registryName) {
        super(registryName);
    }
    
    protected abstract boolean canEventAccept(Object event, ItemStack stack);
    protected abstract Consumer<ItemStack> eventEntryAcceptor(Object event);
    
    /**
     * arg = RegisterEvent
     */
    @Override public void register(@Nullable Object arg) {
        if(Objects.isNull(arg) || Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot register future creative tab with null arg or registryName!");
            return;
        }
        final ResourceLocation name = this.registryName.unwrap();
        register(arg,CREATIVE_MODE_TAB,name,() -> {
            Builder builder = new Builder(TOP,0)
                    .title(TextHelper.getTranslated("itemGroup."+name.getPath()).getAsComponent());
            if(Objects.nonNull(this.iconSupplier)) builder.icon(() -> (ItemStack)this.iconSupplier.get());
            this.wrapped = builder.displayItems((args,output) -> {
                for(ItemStack stack : this.suppliedItems) output.accept(stack);
            }).build();
            this.registered = true;
            return this.wrapped;
        });
    }
    
    protected abstract <T> void register(Object event, ResourceKey<? extends Registry<T>> registryKey,
            ResourceLocation name, Supplier<T> valueSupplier);
    
    /**
     * arg = BuildCreativeModeTabContentsEvent
     */
    @Override public void supply(@Nullable Object arg, List<Supplier<ItemStackAPI<?>>> stackSuppliers) {
        if(Objects.isNull(arg)) {
            TILRef.logError("Cannot supply future creative tab with null arg!");
            return;
        }
        final Consumer<ItemStack> acceptor = eventEntryAcceptor(arg);
        for(Supplier<ItemStackAPI<?>> supplier : stackSuppliers) {
            ItemStack stack = supplier.get().unwrap();
            if(canEventAccept(arg,stack)) acceptor.accept(stack);
            if(!this.suppliedItems.contains(stack)) this.suppliedItems.add(stack);
        }
        stackSuppliers.clear();
    }
}