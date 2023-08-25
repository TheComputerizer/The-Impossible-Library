package mods.thecomputerizer.theimpossiblelibrary.common.test;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.events.AdvancementEvents;
import mods.thecomputerizer.theimpossiblelibrary.events.EntityAddedEvent;
import mods.thecomputerizer.theimpossiblelibrary.events.ServerPlayerLoginEvent;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Objects;

public class CommonEventTest {

    private static final boolean ENTITY = false;
    private static final boolean LOGIN = false;
    private static final boolean ADVANCEMENT = false;

    public static void init() {
        Constants.testLog("INITIALIZING COMMON EVENT TESTS");
        if(ENTITY) {
            Constants.testLog("ENABLED TEST ENTITY EVENT");
            EntityAddedEvent.EVENT.register(CommonEventTest::testEntityEvent);
        }
        if(LOGIN) {
            Constants.testLog("ENABLED TEST LOGIN EVENT");
            ServerPlayerLoginEvent.EVENT.register(CommonEventTest::testPlayerLoginEvent);
        }
        if(ADVANCEMENT) {
            Constants.testLog("ENABLED TEST ADVANCEMENT EVENT");
            AdvancementEvents.SERVER_GRANTED.register(CommonEventTest::testAdvancementEvent);
        }
    }

    private static boolean testEntityEvent(Entity entity) {
        Constants.testLog("TESTING ENTITY ADDED EVENT {}",Objects.nonNull(entity) ?
                entity.getDisplayName().getString() : null);
        return true;
    }

    private static void testPlayerLoginEvent(ServerPlayer player) {
        Constants.testLog("TESTING PLAYER LOGIN EVENT {}",Objects.nonNull(player) ?
                player.getDisplayName().getString() : null);
    }

    private static void testAdvancementEvent(ServerPlayer player, Advancement advancement) {
        Constants.testLog("TESTING SERVER ADVANCEMENT EVENT {}", Objects.nonNull(advancement) ?
                advancement.getId() : null);
    }
}