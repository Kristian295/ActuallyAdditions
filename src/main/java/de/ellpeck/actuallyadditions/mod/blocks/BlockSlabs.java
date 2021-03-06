/*
 * This file ("BlockSlabs.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockSlabs extends BlockBase{

    private Block fullBlock;
    private int meta;

    public BlockSlabs(String name, Block fullBlock){
        this(name, fullBlock, 0);
    }

    public BlockSlabs(String name, Block fullBlock, int meta){
        super(fullBlock.getMaterial(), name);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.fullBlock = fullBlock;
        this.meta = meta;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return this.fullBlock.getIcon(side, this.meta);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity){
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public int onBlockPlaced(World par1World, int blockX, int blockY, int blockZ, int side, float hitX, float hitY, float hitZ, int meta){
        if(side == 1){
            return meta;
        }
        if(side == 0 || hitY >= 0.5F){
            return meta+1;
        }
        return meta;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);
        float minY = meta == 1 ? 0.5F : 0.0F;
        float maxY = meta == 1 ? 1.0F : 0.5F;
        this.setBlockBounds(0.0F, minY, 0F, 1.0F, maxY, 1.0F);
    }

    @Override
    public void setBlockBoundsForItemRender(){
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){

    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock(){
        return TheItemBlock.class;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.common;
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
            if(world.getBlock(x, y, z) == this.field_150939_a && ((side == 1 && world.getBlockMetadata(x, y, z) == 0) || (side == 0 && world.getBlockMetadata(x, y, z) == 1))){
                if(world.setBlock(x, y, z, ((BlockSlabs)this.field_150939_a).fullBlock, ((BlockSlabs)this.field_150939_a).meta, 3)){
                    world.playSoundEffect(x+0.5F, y+0.5F, z+0.5F, this.field_150939_a.stepSound.getBreakSound(), (this.field_150939_a.stepSound.getVolume()+1.0F)/2.0F, this.field_150939_a.stepSound.getPitch()*0.8F);
                    stack.stackSize--;
                    return true;
                }
            }
            return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }
    }
}
