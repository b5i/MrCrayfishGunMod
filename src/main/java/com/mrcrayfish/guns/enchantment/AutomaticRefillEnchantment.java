package com.mrcrayfish.guns.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

/**
 * Author: Antoine Bollengier
 */
public class AutomaticRefillEnchantment extends GunEnchantment
{
    public AutomaticRefillEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTypes.GUN, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, Type.PROJECTILE);
    }

    @Override
    public int getMinCost(int level)
    {
        return 15;
    }

    @Override
    public int getMaxCost(int level)
    {
        return this.getMinCost(level) + 5;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}