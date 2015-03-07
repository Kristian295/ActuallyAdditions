package ellpeck.someprettyrandomstuff.blocks.render;

import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderItems implements IItemRenderer{

    ModelBaseSPRS theModel;
    ResourceLocation theTexture;

    public RenderItems(ModelBaseSPRS model){
        this.theModel = model;
        this.theTexture = new ResourceLocation(Util.MOD_ID_LOWER, "textures/blocks/models/" + this.theModel.getName() + ".png");
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type){
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper){
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data){
        switch(type){
            case INVENTORY:
                GL11.glPushMatrix();
                GL11.glTranslatef(0.5F,  0.5F, 0.5F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-0.5F, -1.27F, 0.5F);
                Minecraft.getMinecraft().renderEngine.bindTexture(theTexture);
                theModel.render(0.0625F);
                GL11.glPopMatrix();
                break;

            case EQUIPPED:
                GL11.glPushMatrix();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(0.6F, -1.2F, -0.0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(theTexture);
                theModel.render(0.0625F);
                GL11.glPopMatrix();
                break;

            case EQUIPPED_FIRST_PERSON:
                GL11.glPushMatrix();
                GL11.glScalef(1.2F, 1.2F, 1.2F);
                GL11.glRotatef(180, 2F, -0F, 0.1F);
                GL11.glTranslatef(1.5F, -1.2F, -0.3F);
                Minecraft.getMinecraft().renderEngine.bindTexture(theTexture);
                theModel.render(0.0625F);
                GL11.glPopMatrix();
                break;

            default:
                GL11.glPushMatrix();
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(0.0F, -1.27F, 0.0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(theTexture);
                theModel.render(0.0625F);
                GL11.glPopMatrix();
                break;
        }
    }

}