package com.meteor.extrabotany.common.crafting.recipe;

import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.brew.ItemBrewBase;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import vazkii.botania.api.brew.IBrewItem;

public class InfiniteWineRecipe extends SpecialRecipe {

    public static final SpecialRecipeSerializer<InfiniteWineRecipe> SERIALIZER = new SpecialRecipeSerializer<>(InfiniteWineRecipe::new);

    public InfiniteWineRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean foundBrew = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.cocktail && !foundBrew) {
                    foundBrew = true;
                } else if (!foundItem) {
                    if (stack.getItem() == ModItems.heromedal) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundBrew && foundItem;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack brewstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.cocktail && brewstack.isEmpty()) {
                    brewstack = stack;
                }
            }
        }

        IBrewItem brew = (IBrewItem) brewstack.getItem();
        ItemStack wine = new ItemStack(ModItems.infinitewine);
        ItemBrewBase.setBrew(wine, brew.getBrew(brewstack));

        return wine;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width > 1 || height > 1;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
