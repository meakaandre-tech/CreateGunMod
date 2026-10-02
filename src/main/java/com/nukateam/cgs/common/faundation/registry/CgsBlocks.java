
package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.block.GuanoPileBlock;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import com.nukateam.ntgl.platform.DeferredRegister;
import net.minecraft.core.registries.Registries;
import com.nukateam.ntgl.platform.DeferredHolder;

import java.util.function.Function;
import java.util.function.Supplier;

public class CgsBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Gunsmithing.MOD_ID);
    public static final DeferredHolder<Block, Block> SULFUR_ORE = registerBlock("sulfur_ore",
            p -> new DropExperienceBlock(UniformInt.of(0, 2), p), () -> Block.Properties.of()
                    .sound(SoundType.NETHERRACK)
                    .mapColor(MapColor.NETHER)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(0.4F));
    public static final DeferredHolder<Block, Block> LEAD_ORE = registerBlock("lead_ore",
            p -> new DropExperienceBlock(ConstantInt.of(0), p), () -> Block.Properties.of()
                    .sound(SoundType.STONE)
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F));
    public static final DeferredHolder<Block, Block> DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore",
            p -> new DropExperienceBlock(ConstantInt.of(0), p), () -> Block.Properties.of()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(4.5F, 3.0F)
                    .sound(SoundType.DEEPSLATE));
    public static final DeferredHolder<Block, Block> RAW_LEAD_BLOCK = registerBlock("raw_lead_block",
            Block::new, () -> Block.Properties.of()
                    .mapColor(MapColor.RAW_IRON).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F));
    public static final DeferredHolder<Block, Block> LEAD_BLOCK = registerBlock("lead_block",
            Block::new, () -> Block.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 6.0F)
                    .sound(SoundType.METAL));
    public static final DeferredHolder<Block, Block> STEEL_BLOCK = registerBlock("steel_block",
            Block::new, () -> Block.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 6.0F)
                    .sound(SoundType.METAL));
    public static final DeferredHolder<Block, Block> GUANO_BLOCK = registerBlockWithoutItem("guano_block",
            GuanoPileBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SNOW)
                    .strength(0.5f)
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.DRIPSTONE_BLOCK)
                    .randomTicks()
                    .noOcclusion());

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block,
                                                                            Supplier<BlockBehaviour.Properties> properties) {
        var toReturn = BLOCKS.registerBlock(name, block, properties);
        CgsItems.ITEMS.registerItem(name, p -> new BlockItem(toReturn.get(), p.useBlockDescriptionPrefix()));
        return toReturn;
    }

    private static <T extends Block> DeferredHolder<Block, T> registerBlockWithoutItem(String name, Function<BlockBehaviour.Properties, T> block,
                                                                                       Supplier<BlockBehaviour.Properties> properties) {
        return BLOCKS.registerBlock(name, block, properties);
    }

    public static void register() {
    }
}
