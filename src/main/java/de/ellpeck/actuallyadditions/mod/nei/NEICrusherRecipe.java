/*
 * This file ("NEICrusherRecipe.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.INEIRecipeHandler;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiGrinder;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NEICrusherRecipe extends TemplateRecipeHandler implements INEIRecipeHandler{

    public NEICrusherRecipe(){
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockGrinder));
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(80, 40, 24, 22), this.getBaseName()));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(this.getBaseName()) && (getClass() == NEICrusherRecipe.class || getClass() == Double.class)){
            for(CrusherRecipe recipe : ActuallyAdditionsAPI.crusherRecipes){
                arecipes.add(new CachedCrush(recipe.getRecipeInputs(), recipe.getRecipeOutputOnes(), recipe.getRecipeOutputTwos(), recipe.outputTwoChance, this));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        for(CrusherRecipe recipe : ActuallyAdditionsAPI.crusherRecipes){
            if(ItemUtil.contains(recipe.getRecipeOutputOnes(), result, true) || ItemUtil.contains(recipe.getRecipeOutputTwos(), result, true)){
                arecipes.add(new CachedCrush(recipe.getRecipeInputs(), recipe.getRecipeOutputOnes(), recipe.getRecipeOutputTwos(), recipe.outputTwoChance, this));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        for(CrusherRecipe recipe : ActuallyAdditionsAPI.crusherRecipes){
            if(ItemUtil.contains(recipe.getRecipeInputs(), ingredient, true)){
                CachedCrush theRecipe = new CachedCrush(recipe.getRecipeInputs(), recipe.getRecipeOutputOnes(), recipe.getRecipeOutputTwos(), recipe.outputTwoChance, this);
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.ingredient), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID_LOWER+":textures/gui/guiGrinder.png";
    }

    @Override
    public String getOverlayIdentifier(){
        return this.getBaseName();
    }

    @Override
    public void drawExtras(int recipe){
        drawProgressBar(80, 40, 176, 0, 24, 23, 48, 1);
        this.drawChanceString(118, 73, recipe);
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiGrinder.class;
    }

    @Override
    public void drawBackground(int recipeIndex){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(60, 13, 60, 13, 56, 79);
    }

    @Override
    public int recipiesPerPage(){
        return 1;
    }

    protected void drawChanceString(int x, int y, int recipe){
        CachedCrush crush = (CachedCrush)this.arecipes.get(recipe);
        if(crush.resultTwo != null){
            int secondChance = crush.secondChance;
            String secondString = secondChance+"%";
            GuiDraw.drawString(secondString, x, y, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
    }

    protected String getBaseName(){
        return "actuallyadditions."+(this instanceof Double ? "crushingDouble" : "crushing");
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+this.getBaseName()+".name");
    }

    public static class Double extends NEICrusherRecipe{

        @Override
        public BookletPage getPageForInfo(int page){
            return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockGrinderDouble));
        }

        @Override
        public void loadTransferRects(){
            transferRects.add(new RecipeTransferRect(new Rectangle(51, 40, 24, 22), this.getBaseName()));
            transferRects.add(new RecipeTransferRect(new Rectangle(101, 40, 24, 22), this.getBaseName()));
        }

        @Override
        public String getGuiTexture(){
            return ModUtil.MOD_ID_LOWER+":textures/gui/guiGrinderDouble.png";
        }

        @Override
        public void drawExtras(int recipe){
            drawProgressBar(51, 40, 176, 0, 24, 23, 48, 1);
            this.drawChanceString(66, 93, recipe);
        }

        @Override
        public Class<? extends GuiContainer> getGuiClass(){
            return GuiGrinder.GuiGrinderDouble.class;
        }

        @Override
        public void drawBackground(int recipeIndex){
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GuiDraw.changeTexture(getGuiTexture());
            GuiDraw.drawTexturedModalRect(33, 20, 33, 20, 110, 70);
        }
    }

    public class CachedCrush extends CachedRecipe{

        public PositionedStack ingredient;
        public PositionedStack resultOne;
        public PositionedStack resultTwo;
        public int secondChance;

        public CachedCrush(List<ItemStack> in, List<ItemStack> outOne, List<ItemStack> outTwo, int secondChance, NEICrusherRecipe handler){
            boolean isDouble = handler instanceof Double;
            this.ingredient = new PositionedStack(in, isDouble ? 51 : 80, 21);
            this.resultOne = new PositionedStack(outOne, isDouble ? 38 : 66, 69);
            if(outTwo != null && !outTwo.isEmpty()){
                this.resultTwo = new PositionedStack(outTwo, isDouble ? 63 : 94, 69);
            }
            this.secondChance = secondChance;
        }

        @Override
        public PositionedStack getResult(){
            return null;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.ingredient));
        }

        @Override
        public List<PositionedStack> getOtherStacks(){
            ArrayList<PositionedStack> list = new ArrayList<PositionedStack>();
            list.addAll(this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.resultOne)));
            if(this.resultTwo != null){
                list.addAll(this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.resultTwo)));
            }
            return list;
        }
    }
}