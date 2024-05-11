package com.mrcrayfish.guns.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

/**
 * Author: MrCrayfish
 */
public class PuncturingEnchantment extends GunEnchantment
{
    public PuncturingEnchantment()
    {
        super(Rarity.RARE, EnchantmentTypes.GUN, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, Type.PROJECTILE);
    }

    @Override
    public int getMaxLevel()
    {
        return 4;
    }

    @Override
    public int getMinCost(int level)
    {
        return 5 + (level - 1) * 3;
    }

    @Override
    public int getMaxCost(int level)
    {
        return this.getMinCost(level) + 10;
    }
}
