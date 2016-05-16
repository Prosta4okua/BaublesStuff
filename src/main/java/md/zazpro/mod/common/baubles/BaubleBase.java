/*
 * This class (BaubleBase.java) was created by <zazpro>. It's distributed as
 * part of the Baubles Stuff Mod. Get the Source Code in github:
 * https://github.com/ZAZPRO/BaublesStuff
 *
 * Baubles Stuff is Open Source and distributed under the
 * Baubles Stuff License: https://github.com/ZAZPRO/BaublesStuff/blob/master/LICENSE.MD
 *
 * © 2016 zazpro
 */

package md.zazpro.mod.common.baubles;

import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import md.zazpro.mod.client.CreativeTab;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class BaubleBase extends Item implements IBauble {

    public BaubleBase(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(1);
        setCreativeTab(CreativeTab.tabBaublesStuff);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer, EnumHand hand) {
        if (!world.isRemote) {
            InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(entityPlayer);
            for (int i = 0; i < baubles.getSizeInventory(); i++)
                if (baubles.getStackInSlot(i) == null && baubles.isItemValidForSlot(i, itemStack)) {
                    baubles.setInventorySlotContents(i, itemStack.copy());
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                    }
                    onEquipped(itemStack, entityPlayer);
                    break;
                }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }
}
