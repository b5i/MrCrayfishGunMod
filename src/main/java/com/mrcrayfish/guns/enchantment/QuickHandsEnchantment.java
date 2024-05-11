package com.mrcrayfish.guns.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

/**
 * Author: MrCrayfish
 */
public class QuickHandsEnchantment extends GunEnchantment
{
    public QuickHandsEnchantment()
    {
        super(Rarity.RARE, EnchantmentTypes.GUN, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, Type.AMMO);
    }

    @Override
    public int getMaxLevel()
    {
        return 2;
    }

    @Override
    public int getMinCost(int level)
    {
        return 5 + (level - 1) * 3;
    }

    @Override
    public int getMaxCost(int level)
    {
        return super.getMinCost(level) + 10;
    }
}
