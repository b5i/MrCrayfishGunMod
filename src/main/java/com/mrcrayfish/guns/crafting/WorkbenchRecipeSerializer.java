package com.mrcrayfish.guns.crafting;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mrcrayfish.guns.init.ModEnchantments;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class WorkbenchRecipeSerializer implements RecipeSerializer<WorkbenchRecipe>
{
    @Override
    public WorkbenchRecipe fromJson(ResourceLocation recipeId, JsonObject parent)
    {
        ImmutableList.Builder<WorkbenchIngredient> builder = ImmutableList.builder();
        JsonArray input = GsonHelper.getAsJsonArray(parent, "materials");
        for(int i = 0; i < input.size(); i++)
        {
            JsonObject object = input.get(i).getAsJsonObject();
            builder.add(WorkbenchIngredient.fromJson(object));
        }
        if(!parent.has("result"))
        {
            throw new JsonSyntaxException("Missing result item entry");
        }
        JsonObject resultObject = GsonHelper.getAsJsonObject(parent, "result");
        ItemStack resultItem = ShapedRecipe.itemStackFromJson(resultObject);
        if (resultObject.has("enchant"))
        {
            Optional<RegistryObject<Enchantment>> optionalEnchant = ModEnchantments.REGISTER.getEntries().stream().filter(enchantment -> enchantment.get().getDescriptionId().equals(resultObject.get("enchant").getAsString())).findFirst();
            if (optionalEnchant.isPresent())
            {
                resultItem.enchant(optionalEnchant.get().get(), resultObject.get("enchant_lvl").getAsInt()); resultObject.get("enchant");
            }
        }
        return new WorkbenchRecipe(recipeId, resultItem, builder.build());
    }

    @Nullable
    @Override
    public WorkbenchRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
    {
        ItemStack result = buffer.readItem();
        ImmutableList.Builder<WorkbenchIngredient> builder = ImmutableList.builder();
        int size = buffer.readVarInt();
        for(int i = 0; i < size; i++)
        {
            builder.add((WorkbenchIngredient) Ingredient.fromNetwork(buffer));
        }
        return new WorkbenchRecipe(recipeId, result, builder.build());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, WorkbenchRecipe recipe)
    {
        buffer.writeItem(recipe.getItem());
        buffer.writeVarInt(recipe.getMaterials().size());
        for(WorkbenchIngredient ingredient : recipe.getMaterials())
        {
            ingredient.toNetwork(buffer);
        }
    }
}
