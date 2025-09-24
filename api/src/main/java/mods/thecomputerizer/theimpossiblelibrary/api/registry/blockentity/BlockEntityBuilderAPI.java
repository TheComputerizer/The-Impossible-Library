package mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class BlockEntityBuilderAPI extends RegistryEntryBuilder<BlockEntityAPI<?,?>> {
    
    /**
     * These are needed in 1.18.2+ since BlockEntitySupplier is package-private without forge or fabric present.
     */
    private static final String CREATE = NAMED_ENV ? "create" : (SRG_ENV ? "m_155267_" : "create");
    private static final String OF = NAMED_ENV ? "of" : (SRG_ENV ? "m_155273_" : "method_20528");
    private static final String SUPPLIER = "net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier";
    
    protected static <B> B getBuilder(Class<?> builderClass, BiFunction<?,?,?> supplier,
            Object[] blocks) {
        Object blockEntitySupplier = getSupplier(supplier);
        if(Objects.isNull(blockEntitySupplier)) {
            TILRef.logError("Unable to build BlockEntityType from null BlockEntitySupplier!");
            return null;
        }
        return Hacks.invokeStaticDirect(builderClass,OF,blockEntitySupplier,blocks);
    }
    
    @SuppressWarnings("unchecked")
    private static <P1,P2>Object getSupplier(BiFunction<P1,P2,?> supplier) {
        Class<?> supplierClass = Hacks.findClass(SUPPLIER);
        if(Objects.isNull(supplierClass)) {
            TILRef.logError("Unable to get BlockEntitySupplier class! {}", SUPPLIER);
            return null;
        }
        return ClassHelper.newGenericProxy(supplierClass,CREATE,args -> supplier.apply((P1)args[0],(P2)args[1]));
    }
    
    protected Consumer<BlockEntityAPI<?,?>> onTick;
    protected Supplier<Collection<BlockAPI<?>>> validBlocks;
    
    protected BlockEntityBuilderAPI(@Nullable BlockEntityBuilderAPI parent) {
        if(Objects.nonNull(parent)) {
            this.onTick = parent.onTick;
            this.validBlocks = parent.validBlocks;
        } else {
            this.onTick = null;
            this.validBlocks = null;
        }
    }
    
    @Override public BlockEntityBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    public BlockEntityBuilderAPI setOnTick(Consumer<BlockEntityAPI<?,?>> consumer) {
        this.onTick = consumer;
        return this;
    }
    
    public BlockEntityBuilderAPI setValidBlocks(Supplier<Collection<BlockAPI<?>>> supplier) {
        this.validBlocks = supplier;
        return this;
    }
}