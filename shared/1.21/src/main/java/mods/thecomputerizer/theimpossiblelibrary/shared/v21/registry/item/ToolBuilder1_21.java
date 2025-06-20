package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class ToolBuilder1_21 extends ToolBuilderAPI {
    
    public ToolBuilder1_21(@Nullable ItemBuilderAPI builder, ToolType type) {
        super(builder,type);
    }
    
    @Override public ItemAPI<?> build() {
        mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties properties = buildProperties();
        Item item = getItem(properties,this.toolTier.unwrap());
        if(CoreAPI.isClient()) registerTextureProperties(item);
        ItemAPI<?> wrapped = WrapperHelper.wrapItem(item);
        wrapped.setRegistryName(this.registryName);
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        if(Objects.nonNull(tab)) tab.addStack(wrapped::defaultStack);
        return wrapped;
    }
    
    private Item getItem(ItemProperties properties, Tier tier) {
        switch(this.toolType) {
            case AXE: return new TILItemAxe1_21(tier,properties);
            case HOE: return new TILItemHoe1_21(tier,properties);
            case PICKAXE: return new TILItemPickaxe1_21(tier,properties);
            case SHOVEL: return new TILItemShovel1_21(tier,properties);
            case SWORD: return new TILItemSword1_21(tier,properties);
            default: {
                Set<Block> blocks = new HashSet<>();
                this.effectiveBlocks.forEach(block -> blocks.add(block.unwrap()));
                return new TILCustomTool1_21(tier,this.damageModifier,this.speedModifier,blocks,properties);
            }
        }
    }
    
    private void registerTextureProperties(Item item) {
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = property.getKey().unwrap();
            ClampedItemPropertyFunction func = (stack,world,entity,seed) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world));
            Methods.invokeStaticDirect(net.minecraft.client.renderer.item.ItemProperties.class,"register",
                                       item,location,func);
            net.minecraft.client.renderer.item.ItemProperties.register(item, location, (stack,world,entity,seed) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world)));
        }
    }
}