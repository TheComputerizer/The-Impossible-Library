package mods.thecomputerizer.theimpossiblelibrary.api.common;

/**
 * Common entrypoint API. Uses generics to validate event types. Unused generics may be set to Object
 */
public interface EntryPointAPI<CONSTRUCTION,PREINIT,INIT,POSTINIT,LOADCOMPLETE,SERVERSTARTING,SERVERSTARTED> {

    void onModConstruction(CONSTRUCTION event);
    void onPreInit(PREINIT event);
    void onInit(INIT event);
    void onPostInit(POSTINIT event);
    void onLoadComplete(LOADCOMPLETE event);
    void onServerStarting(SERVERSTARTING event);
    void onServerStarted(SERVERSTARTED event);
}