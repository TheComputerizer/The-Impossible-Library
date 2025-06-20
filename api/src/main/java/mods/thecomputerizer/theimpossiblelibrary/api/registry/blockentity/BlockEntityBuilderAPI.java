package mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

@SuppressWarnings("unused")
public abstract class BlockEntityBuilderAPI extends RegistryEntryBuilder<BlockEntityAPI<?,?>> {
    
    /**
     * These are needed in 1.18.2+ since BlockEntitySupplier is package-private without forge or fabric present.
     */
    private static final String CREATE = NAMED_ENV ? "create" : (SRG_ENV ? "m_155267_" : "create");
    private static final String OF = NAMED_ENV ? "of" : (SRG_ENV ? "m_155273_" : "method_20528");
    private static final String SUPPLIER = "net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier";
    
    protected static <B> B getBuilder(Class<?> builderClass, BiFunction<?,?,?> supplier,
            Object[] blocks) {
        Object blockEntitySupplier = getSupplier(supplier);
        if(Objects.isNull(blockEntitySupplier)) {
            TILRef.logError("Unable to build BlockEntityType from null BlockEntitySupplier!");
            return null;
        }
        return Methods.invokeStaticDirect(builderClass,OF,blockEntitySupplier,blocks);
    }
    
    @SuppressWarnings("unchecked")
    private static <BP,BS> InvocationHandler getSupplierHandler(final BiFunction<BP,BS,?> supplier) {
        return (supplierProxy,method,args) -> {
            String methodName = method.getName();
            switch(methodName) {
                case "equals": return args.length>0 && supplierProxy==args[0];
                case "hashCode": return 0;
                default: {
                    TILRef.logInfo("Invoking BlockEntitySupplier method {}",methodName);
                    return CREATE.equals(methodName) && args.length>=2 ? supplier.apply((BP)args[0],(BS)args[1]) : null;
                }
            }
        };
    }
    
    private static Object getSupplier(BiFunction<?,?,?> supplier) {
        Class<?> supplierClass = ClassHelper.findClass(SUPPLIER);
        if(Objects.isNull(supplierClass)) {
            TILRef.logError("Unable to get BlockEntitySupplier class! {}", SUPPLIER);
            return null;
        }
        return ClassHelper.newProxy(supplierClass,getSupplierHandler(supplier));
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