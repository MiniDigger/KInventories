package com.redrield.kinventories

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class KInventories {

    companion object {
        val REGISTERED_INVENTORIES = ArrayList<Inventory>()
        val ITEMSTACK_ACTIONS = HashMap<ItemStack, ItemStack.(Player) -> Unit>()
        val INVENTORY_OPEN_ACTIONS = HashMap<Inventory, (Player) -> Unit>()
        val INVENTORY_CLOSE_ACTIONS = HashMap<Inventory, (Player) -> Unit>()

        /**
         * Registers KInventories InventoryListener under the provided JavaPlugin
         *
         * @param plugin The plugin to register the listener under
         */
        @JvmStatic
        fun register(plugin: JavaPlugin) {
            plugin.server.pluginManager.registerEvents(InventoryListener(), plugin)
        }
    }

    private class InventoryListener : Listener {

        @EventHandler
        fun onClick(e: InventoryClickEvent) {
            if (REGISTERED_INVENTORIES.contains(e.inventory) && e.whoClicked is Player) {
                e.isCancelled = true
                val clickedStack = e.currentItem
                ITEMSTACK_ACTIONS.forEach { k, _ -> println(k.type) }
                val cb = ITEMSTACK_ACTIONS.remove(clickedStack)
                if(cb != null) {
                    clickedStack.cb(e.whoClicked as Player)
                    ITEMSTACK_ACTIONS.put(clickedStack, cb)
                }
            }
        }

        @EventHandler
        fun onClose(e: InventoryCloseEvent) {
            if (REGISTERED_INVENTORIES.contains(e.inventory) && INVENTORY_CLOSE_ACTIONS.containsKey(e.inventory)) {
                when (e.player) {
                    is Player -> {
                        INVENTORY_CLOSE_ACTIONS[e.inventory]?.let {
                            it(e.player as Player)
                        }
                    }
                }
            }
        }

        @EventHandler
        fun onOpen(e: InventoryOpenEvent) {
            if (REGISTERED_INVENTORIES.contains(e.inventory) && INVENTORY_OPEN_ACTIONS.containsKey(e.inventory)) {
                when (e.player) {
                    is Player -> {
                        INVENTORY_OPEN_ACTIONS[e.inventory]?.let {
                            it(e.player as Player)
                        }
                    }
                }
            }
        }
    }
}
