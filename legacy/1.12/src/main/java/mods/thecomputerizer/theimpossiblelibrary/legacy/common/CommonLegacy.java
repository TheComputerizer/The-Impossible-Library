package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.TILLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.network.NetworkLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryHandlerLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.tag.TagLegacy;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonLegacy implements CommonAPI {

    private final CommonEventsAPI events;
    private final ModAPI mod;
    private final NetworkAPI<SimpleNetworkWrapper,Side> network;
    private final Reference reference;
    private final RegistryHandlerAPI<RegistryLegacy<?>> registry;
    private final ResourceAPI resource;
    private final TagAPI tag;

    public CommonLegacy() {
        this.events = new CommonEventsLegacy();
        this.mod = new ModLegacy();
        this.network = new NetworkLegacy();
        this.reference = TILLegacy.LEGACY_REF;
        this.registry = new RegistryHandlerLegacy();
        this.resource = new ResourceLegacy();
        this.tag = new TagLegacy();
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
