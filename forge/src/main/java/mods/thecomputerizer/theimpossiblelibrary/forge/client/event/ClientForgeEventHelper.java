package mods.thecomputerizer.theimpossiblelibrary.forge.client.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.PlayerPunchEmptyEventForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.PlayerPushOutOfBlocksEventForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEventHelper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUSH_OUT_OF_BLOCKS;

public interface ClientForgeEventHelper extends CommonForgeEventHelper {
    
    @Override default void defaultEventDefinitions() {
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEventForge());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEventForge());
    }
}
