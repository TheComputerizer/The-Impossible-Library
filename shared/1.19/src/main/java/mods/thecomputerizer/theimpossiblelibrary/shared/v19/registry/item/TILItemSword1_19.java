package mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_4;

public class TILItemSword1_19 extends SwordItem implements ItemHelpers1_19 {
    
    static Properties tab(Properties iProperties, ItemProperties properties) {
        return VERSION==V19_4 ? iProperties : tab19_2(iProperties,properties);
    }
    
    static Properties tab19_2(Properties iProperties, ItemProperties properties) {
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        return Objects.nonNull(tab) ? tab.withItemProperties(iProperties) : iProperties;
    }
    
    protected final ItemProperties properties;
    
    @IndirectCallers
    public TILItemSword1_19(Tier tier, int damage, float speed, ItemProperties properties) {
        super(tier,damage,speed,tab(new Properties().stacksTo(properties.getStackSize()),properties));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        defaultAppendHoverText(stack,world,components);
    }
    
    @Override public @NotNull InteractionResult useOn(UseOnContext ctx) {
        return defaultUseOn(ctx,super::useOn);
    }
    
    @Override public ItemProperties getProperties() {
        return this.properties;
    }
}