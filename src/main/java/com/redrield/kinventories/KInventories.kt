package com.redrield.kinventories

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin

class KInventories : JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        server.pluginManager.registerEvents(InventoryListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        val inv = inventory("ASOASDA", 18) {
            itemStack(Material.DIAMOND_SWORD) {
                itemMeta {
                    displayName = "HOOEEE"
                }
            }

            onOpen {
                it.sendMessage("Oh yes, open me more")
            }

            onClose {
                it.sendMessage("Where are you going...?")
            }

            itemStack(Material.GOLD_SWORD, amount = 2) {
                itemMeta {
                    displayName  = "HEE HAW"
                }
            }

            itemStack(Material.STONE_SWORD, slot = 5) {
                itemMeta {
                    displayName = "TEET"
                }
                onClick {
                    it.sendMessage("Hello world!")
                    it.closeInventory()
                }
            }

            itemStack(Material.WOOD_SWORD, slot = 6, amount = 3) {
                itemMeta {
                    displayName = "FIAEJGIEOGH"
                }
            }
        }
        e.player.openInventory(inv)
    }

    private inner class InventoryListener : Listener {

        @EventHandler
        fun onClick(e: InventoryClickEvent) {
            if(registeredInventories.contains(e.inventory) && e.whoClicked is Player) {
                e.isCancelled = true
                val clickedStack = e.currentItem
                itemStackActions[clickedStack]?.let {
                    it(e.whoClicked as Player)
                }
            }
        }

        @EventHandler
        fun onClose(e: InventoryCloseEvent) {
            if(registeredInventories.contains(e.inventory) && inventoryCloseActions.containsKey(e.inventory)) {
                when(e.player) {
                    is Player -> {
                        inventoryCloseActions[e.inventory]?.let {
                            it(e.player as Player)
                        }
                    }
                }
            }
        }

        @EventHandler
        fun onOpen(e: InventoryOpenEvent) {
            if(registeredInventories.contains(e.inventory) && inventoryOpenActions.containsKey(e.inventory)) {
                when(e.player) {
                    is Player -> {
                        inventoryOpenActions[e.inventory]?.let {
                            it(e.player as Player)
                        }
                    }
                }
            }
        }


    }
}

val registeredInventories = ArrayList<Inventory>()
val itemStackActions = HashMap<ItemStack, (Player) -> Unit>()
val inventoryOpenActions = HashMap<Inventory, (Player) -> Unit>()
val inventoryCloseActions = HashMap<Inventory, (Player) -> Unit>()


fun Inventory.onOpen(callback: (Player) -> Unit) = inventoryOpenActions.put(this, callback)

fun Inventory.onClose(callback: (Player) -> Unit) = inventoryCloseActions.put(this, callback)

inline fun inventory(name: String, size: Int, body: Inventory.() -> Unit): Inventory {
    val inv = Bukkit.createInventory(null, size, name).apply(body)
    registeredInventories.add(inv)
    return inv
}

inline fun Inventory.itemStack(material: Material, amount: Int = 1, slot: Int, body: ItemStack.() -> Unit): ItemStack {
    val stack = ItemStack(material, amount).apply(body)
    this.setItem(slot, stack)
    return stack
}

fun ItemStack.onClick(cb: (Player) -> Unit) {
    itemStackActions.put(this, cb)
}


inline fun Inventory.itemStack(material: Material, amount: Int = 1, body: ItemStack.() -> Unit): ItemStack {
    val stack = ItemStack(material, amount).apply(body)
    this.addItem(stack)
    return stack
}

inline fun ItemStack.itemMeta(body: ItemMeta.() -> Unit) {
    val meta = this.itemMeta.apply(body)
    this.itemMeta = meta
}
