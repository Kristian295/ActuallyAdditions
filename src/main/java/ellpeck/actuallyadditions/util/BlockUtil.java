package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil{

    public static String createUnlocalizedName(Block block){
        return ModUtil.MOD_ID_LOWER + "." + ((INameableItem)block).getName();
    }

    @SuppressWarnings("unchecked")
    public static void addInformation(Block block, List list, int lines, String extraName){
        if(KeyUtil.isShiftPressed()){
            for(int i = 0; i < lines; i++){
                list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((INameableItem)block).getName() + extraName + ".desc" + (lines > 1 ? "." +(i+1) : "")));
            }
        }
        else list.add(ItemUtil.shiftForInfo());
    }

    @SuppressWarnings("unchecked")
    public static void addPowerUsageInfo(List list, int usage){
        if(KeyUtil.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".uses.desc") + " " + usage + " RF/t");
        }
    }

    @SuppressWarnings("unchecked")
    public static void addPowerProductionInfo(List list, int produce){
        if(KeyUtil.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".produces.desc") + " " + produce + " RF/t");
        }
    }

    public static final ArrayList<Block> wailaRegisterList = new ArrayList<Block>();

    public static void register(Block block, Class<? extends ItemBlock> itemBlock, boolean addTab, Enum[] list){
        block.setCreativeTab(addTab ? CreativeTab.instance : null);
        block.setBlockName(createUnlocalizedName(block));
        GameRegistry.registerBlock(block, itemBlock, ((INameableItem)block).getName());
        if(list != null){
            for(Enum current : list){
                if(!((INameableItem)current).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)current).getOredictName(), new ItemStack(block, 1, current.ordinal()));
            }
        }
        else{
            if(!((INameableItem)block).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)block).getOredictName(), new ItemStack(block, 1, Util.WILDCARD));
        }

        wailaRegisterList.add(block);
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock, Enum[] list){
        register(block, itemBlock, true, list);
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock){
        register(block, itemBlock, true);
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock, boolean addTab){
        register(block, itemBlock, addTab, null);
    }
}
