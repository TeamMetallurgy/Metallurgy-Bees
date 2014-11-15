package elcon.mods.metallurgybees.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import elcon.mods.metallurgybees.blocks.BlockBeehive;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class ForgeEventHandler {

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.block != null) {
            if (event.block instanceof BlockBeehive) {
                event.setCanceled(true);
                ItemStack stack = event.getPlayer().getCurrentEquippedItem();
                if (stack != null && stack.getItem().onBlockStartBreak(stack, event.x, event.y, event.z, event.getPlayer())) {
                    return;
                }
                Block block = event.world.getBlock(event.x, event.y, event.z);
                event.world.playAuxSFXAtEntity(event.getPlayer(), 2001, event.x, event.y, event.z, Block.getIdFromBlock(block));
                boolean flag;
                if (event.getPlayer().capabilities.isCreativeMode) {
                    flag = removeBlock(event.world, event.x, event.y, event.z, 0, event.getPlayer(), true);
                } else {
                    ItemStack itemstack = event.getPlayer().getCurrentEquippedItem();
                    boolean flag1 = false;
                    if (block != null) {
                        flag1 = block.canHarvestBlock(event.getPlayer(), 0);
                    }
                    if (itemstack != null) {
                        itemstack.func_150999_a(event.world, block, event.x, event.y, event.z, event.getPlayer());
                        if (itemstack.stackSize == 0) {
                            event.getPlayer().destroyCurrentEquippedItem();
                        }
                    }
                    flag = removeBlock(event.world, event.x, event.y, event.z, 0, event.getPlayer(), flag1);
                    if (flag && flag1) {
                        block.harvestBlock(event.world, event.getPlayer(), event.x, event.y, event.z, 0);
                    }
                }
                if (!event.getPlayer().capabilities.isCreativeMode && flag && event != null) {
                    block.dropXpOnBlockBreak(event.world, event.x, event.y, event.z, event.getExpToDrop());
                }
            }
        }
    }

    private boolean removeBlock(World world, int x, int y, int z, int meta, EntityPlayer player, boolean drop) {
        Block block = world.getBlock(x, y, z);
        if (block != null) {
            block.onBlockHarvested(world, x, y, z, meta, player);
        }
        if (drop) {
            boolean flag = (block != null && block.removedByPlayer(world, player, x, y, z, true));
            if (block != null && flag) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            }
            return flag;
        } else {
            boolean flag = (block != null && world.setBlockToAir(x, y, z));
            if (block != null && flag) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            }
            return flag;
        }
    }
}
