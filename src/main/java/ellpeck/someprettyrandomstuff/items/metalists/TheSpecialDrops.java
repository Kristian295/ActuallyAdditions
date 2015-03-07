package ellpeck.someprettyrandomstuff.items.metalists;

import ellpeck.someprettyrandomstuff.config.ConfigValues;
import ellpeck.someprettyrandomstuff.util.IName;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.EnumRarity;

public enum TheSpecialDrops implements IName{

    SOLIDIFIED_EXPERIENCE("SolidifiedExperience", 70, 3, EntityCreature.class, EnumRarity.uncommon, ConfigValues.enableExperienceDrop),
    BLOOD_FRAGMENT("BloodFragment", 30, 1, EntityCreature.class, EnumRarity.uncommon, ConfigValues.enableBloodDrop),
    //Change this into a Heart Fragment that is later crafted into a heart used to revive Villagers
    HEART_PART("HeartPart", 10, 1, EntityCreature.class, EnumRarity.rare, ConfigValues.enableHeartDrop),
    UNKNOWN_SUBSTANCE("UnknownSubstance", 5, 1, EntitySkeleton.class, EnumRarity.epic, ConfigValues.enableSubstanceDrop),
    PEARL_SHARD("PearlShard", 20, 3, EntityEnderman.class, EnumRarity.epic, ConfigValues.enablePearlShardDrop),
    EMERALD_SHARD("EmeraldShard", 15, 1, EntityCreeper.class, EnumRarity.rare, ConfigValues.enableEmeraldShardDrop);

    public final String name;
    public final int chance;
    public final int maxAmount;
    public final Class<? extends EntityCreature> dropFrom;
    public final boolean canDrop;
    public final EnumRarity rarity;

    private TheSpecialDrops(String name, int chance, int maxAmount, Class<? extends EntityCreature> dropFrom, EnumRarity rarity, boolean canDrop){
        this.name = name;
        this.chance = chance;
        this.rarity = rarity;
        this.maxAmount = maxAmount;
        this.dropFrom = dropFrom;
        this.canDrop = canDrop;
    }

    @Override
    public String getName(){
        return this.name;
    }
}