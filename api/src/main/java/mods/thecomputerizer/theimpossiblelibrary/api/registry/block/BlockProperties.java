package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;

public class BlockProperties {
    
    private final BiFunction<WorldAPI<?>,BlockStateAPI<?>,BlockEntityAPI<?,?>> blockEntityCreator;
    @Getter private final Map<String,Integer> effectiveTools;
    @Getter private final MaterialAPI<?> material;
    @Getter private final MaterialColorAPI<?> materialColor;
    @Getter private final ResourceLocationAPI<?> registryName;
    private final Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer;
    private final Function<TILItemUseContext,ActionResult> useFunc;
    
    public BlockProperties(MaterialAPI<?> material, MaterialColorAPI<?> materialColor,
            Map<String,Integer> effectiveTools, ResourceLocationAPI<?> registryName,
            @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer,
            @Nullable Function<TILItemUseContext,ActionResult> useFunc,
            @Nullable BiFunction<WorldAPI<?>,BlockStateAPI<?>,BlockEntityAPI<?,?>> blockEntityCreator) {
        this.blockEntityCreator = blockEntityCreator;
        this.effectiveTools = effectiveTools;
        this.material = material;
        this.materialColor = materialColor;
        this.registryName = registryName;
        this.stateTransformer = stateTransformer;
        this.useFunc = useFunc;
    }
    
    public BlockEntityAPI<?,?> createBlockEntity(WorldAPI<?> world, BlockStateAPI<?> state) {
        return Objects.nonNull(this.blockEntityCreator) ? this.blockEntityCreator.apply(world,state) : null;
    }
    
    public BlockStateAPI<?> getDefaultState(BlockStateAPI<?> state) {
        return Objects.nonNull(this.stateTransformer) ? this.stateTransformer.apply(state) : state;
    }
    
    public ActionResult getUseResult(TILItemUseContext ctx) {
        return Objects.nonNull(this.useFunc) ? this.useFunc.apply(ctx) : PASS;
    }
    
    public boolean hasStateTransformer() {
        return Objects.nonNull(this.stateTransformer);
    }
    
    public boolean hasUseResult() {
        return Objects.nonNull(this.useFunc);
    }
    
    public boolean isBlockEntity() {
        return Objects.nonNull(this.blockEntityCreator);
    }
}