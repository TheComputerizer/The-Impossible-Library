package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.fml.common.InjectedModContainer;

import java.util.HashMap;
import java.util.Map;

public class InjectedModContainer1_12_2 extends InjectedModContainer {

    private static Map<String,Object> getDescriptor(MultiVersionModInfo info) {
        Map<String,Object> descriptor = new HashMap<>();
        descriptor.put("language","java");
        descriptor.put("modid",info.getModID());
        descriptor.put("name",info.getName());
        descriptor.put("version",info.getVersion());
        return descriptor;
    }

    public InjectedModContainer1_12_2(String modid) {
        super(TILCore1_12_2.getFMLModContainer(modid),TILCore1_12_2.getModSource(modid));
    }
}
