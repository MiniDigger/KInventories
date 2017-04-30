package com.redrield.kinventories.kotlin

import com.redrield.kinventories.KInventories
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Function to create a named Inventory
 * @param name Name of the inventory
 * @param size Size of the inventory. Must be a multiple of 9
 * @param block The block to be applied to the Inventory
 * @return The created inventory
 */
@JvmSynthetic
inline fun inventory(name: String, size: Int, block: Inventory.() -> Unit): Inventory {
    val inv = Bukkit.createInventory(null, size, name).apply(block)
    KInventories.REGISTERED_INVENTORIES.add(inv)
    return inv
}

/**
 * Function to create an unnamed Inventory
 * @parma size Size of the Inventory. Must be a multiple of 9
 * @param block The block to be applied to the Inventory
 * @return the created Inventory
 */
@JvmSynthetic
inline fun inventory(size: Int, block: Inventory.() -> Unit): Inventory {
    val inv = Bukkit.createInventory(null, size).apply(block)
    KInventories.REGISTERED_INVENTORIES.add(inv)
    return inv
}

/**
 * Function to add an [org.bukkit.event.inventory.InventoryOpenEvent] callback to an inventory
 * @param block The callback to be executed when the event is called
 */
@JvmSynthetic
fun Inventory.onOpen(block: (Player) -> Unit) {
    KInventories.INVENTORY_OPEN_ACTIONS.put(this, block)
}

/**
 * Function to add an [org.bukkit.event.inventory.InventoryCloseEvent] callback to an inventory
 * @param block The callback to be executed when the event is called
 */
@JvmSynthetic
fun Inventory.onClose(block: (Player) -> Unit) {
    KInventories.INVENTORY_CLOSE_ACTIONS.put(this, block)
}

/**
 * Function to add an [ItemStack] to the inventory
 * @param material The [org.bukkit.Material] of the [ItemStack]
 * @param amount The amount of items in the [ItemStack]. Defaults to 1
 * @param block The block to be applied to the [ItemStack]
 * @return The [ItemStack]
 */
@JvmSynthetic
inline fun Inventory.itemStack(material: Material, amount: Int = 1, block: ItemStack.() -> Unit): ItemStack {
    val stack = ItemStack(material, amount).apply(block)
    this.addItem(stack)
    return stack
}

/**
 * Function to add an [ItemStack] to the inventory at a specific slot
 * @param material the [Material] of the [ItemStack]
 * @param amount The amount of items in the [ItemStack]. Defaults to 1
 * @param slot the slot to put the item in.
 * @param block The block to apply to the [ItemStack]
 * @return the [ItemStack]
 */
@JvmSynthetic
inline fun Inventory.itemStack(material: Material, amount: Int = 1, slot: Int, block: ItemStack.() -> Unit): ItemStack {
    val stack = ItemStack(material, amount).apply(block)
    this.setItem(slot, stack)
    return stack
}
