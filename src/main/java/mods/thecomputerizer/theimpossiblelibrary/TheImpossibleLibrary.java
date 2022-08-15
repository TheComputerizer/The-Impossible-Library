package mods.thecomputerizer.theimpossiblelibrary;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES)
public class TheImpossibleLibrary {

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //registration stuff has to happen here!
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        //keybindings and other less important stuff can go here
    }
}
