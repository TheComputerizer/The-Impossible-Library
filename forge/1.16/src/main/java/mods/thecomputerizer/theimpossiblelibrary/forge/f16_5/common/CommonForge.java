package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.TILForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.network.NetworkForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryHandlerForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.tag.TagForge;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class CommonForge implements CommonAPI {

    private final CommonEventsAPI events;
    private final ModAPI mod;
    private final NetworkAPI<SimpleChannel,NetworkDirection> network;
    private final Reference reference;
    private final RegistryHandlerAPI<RegistryForge<?>> registry;
    private final ResourceAPI resource;
    private final TagAPI tag;

    public CommonForge() {
        this.events = new CommonEventsForge();
        this.mod = new ModForge();
        this.network = new NetworkForge();
        this.reference = TILForge.FORGE_REF;
        this.registry = new RegistryHandlerForge();
        this.resource = new ResourceForge();
        this.tag = new TagForge();
    }


    @Override
    public CommonEventsAPI getCommonEventsAPI() {
        return this.events;
    }

    @Override
    public ModAPI getModAPI() {
        return this.mod;
    }

    @Override
    public NetworkAPI<?,?> getNetworkAPI() {
        return this.network;
    }

    @Override
    public Reference getReference() {
        return this.reference;
    }

    @Override
    public RegistryHandlerAPI<?> getRegistryHandlerAPI() {
        return this.registry;
    }

    @Override
    public ResourceAPI getResourceAPI() {
        return this.resource;
    }

    @Override
    public TagAPI getTagAPI() {
        return this.tag;
    }
}
