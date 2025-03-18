package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.advancement;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementDisplayInfoAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.core.registries.BuiltInRegistries.REGISTRY;
import static net.minecraft.core.registries.Registries.ADVANCEMENT;

public class Advancement1_21 extends AdvancementAPI<Advancement> {
    
    private final AdvancementDisplayInfo1_21 display;
    private RegistryAccess access;

    public Advancement1_21(Object advancement) {
        super(advancement instanceof AdvancementHolder ? ((AdvancementHolder)advancement).value() :
                      (Advancement)advancement);
        this.display = new AdvancementDisplayInfo1_21(this.wrapped.display().orElse(null));
    }

    @Override public AdvancementDisplayInfoAPI getDisplayInfo() {
        return this.display;
    }

    @Override public ResourceLocationAPI<?> getID() {
        Registry<Advancement> registry = getRegistry();
        if(Objects.isNull(registry)) return null;
        return WrapperHelper.wrapResourceLocation(registry.getKey(this.wrapped));
    }

    @Override public Advancement getParent() {
        ResourceLocation parentLocation = this.wrapped.parent().orElse(null);
        if(Objects.isNull(parentLocation)) return null;
        Registry<Advancement> registry = getRegistry();
        if(Objects.isNull(registry)) return null;
        return registry.get(parentLocation);
    }
    
    private @Nullable Registry<Advancement> getRegistry() {
        return getRegistryAccess().registry(ADVANCEMENT).orElse(null);
    }
    
    private RegistryAccess getRegistryAccess() {
        if(Objects.isNull(this.access)) {
            if(CoreAPI.isClient()) {
                ClientLevel level = Minecraft.getInstance().level;
                this.access = Objects.nonNull(level) ? level.registryAccess() :
                        RegistryAccess.fromRegistryOfRegistries(REGISTRY);
            } else this.access = RegistryAccess.fromRegistryOfRegistries(REGISTRY);
        }
        return this.access;
    }
}