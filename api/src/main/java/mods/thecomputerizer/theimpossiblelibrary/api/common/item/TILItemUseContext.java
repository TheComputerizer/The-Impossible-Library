package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;

@Getter
public class TILItemUseContext {
    
    private final PlayerAPI<?,?> player;
    private final WorldAPI<?> world;
    private final BlockPosAPI<?> pos;
    private final BlockStateAPI<?> state;
    private final Hand hand;
    private final Facing facing;
    @Setter private ActionResult superResult = PASS;
    
    public TILItemUseContext(PlayerAPI<?,?> player, WorldAPI<?> world, BlockPosAPI<?> pos, BlockStateAPI<?> state,
            Hand hand, Facing facing) {
        this.player = player;
        this.world = world;
        this.pos = pos;
        this.state = state;
        this.hand = hand;
        this.facing = facing;
    }
    
    public ActionResult onUse(@Nullable Function<TILItemUseContext,ActionResult> onUse) {
        return Objects.nonNull(onUse) ? onUse.apply(this) : PASS;
    }
}