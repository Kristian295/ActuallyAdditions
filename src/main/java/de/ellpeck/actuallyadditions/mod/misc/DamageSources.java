/*
 * This file ("DamageSources.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSources extends DamageSource{

    public static final DamageSource DAMAGE_ATOMIC_RECONSTRUCTOR = new DamageSources("atomicReconstructor", 5).setDamageBypassesArmor();

    private int messageCount;

    public DamageSources(String name, int messageCount){
        super(name);
        this.messageCount = messageCount;
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase entity){
        String locTag = "death."+ModUtil.MOD_ID_LOWER+"."+this.damageType+"."+(Util.RANDOM.nextInt(this.messageCount)+1);
        return new ChatComponentText(StringUtil.localizeFormatted(locTag, entity.getCommandSenderName()));
    }
}
