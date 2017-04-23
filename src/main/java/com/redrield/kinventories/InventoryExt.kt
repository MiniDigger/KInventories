package com.redrield.kinventories

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun inventory(name: String, size: Int, block: Inventory.() -> Unit): Inventory {
    val inv = Bukkit.createInventory(null, size, name).apply(block)
    return inv
}

fun Inventory.onOpen(block: (Player) -> Unit) {
    KInventories.INVENTORY_OPEN_ACTIONS.put(this, block)
}

fun Inventory.onClose(block: (Player) -> Unit) {
    KInventories.INVENTORY_CLOSE_ACTIONS.put(this, block)
}

inline fun Inventory.itemStack(material: Material, amount: Int = 1, block: ItemStack.() -> Unit): ItemStack {
    val stack = ItemStack(material, amount).apply(block)
    this.addItem(stack)
    return stack
}

inline fun Inventory.itemStack(material: Material, amount: Int = 1, slot: Int, block: ItemStack.() -> Unit): ItemStack {
    val stack = ItemStack(material, amount).apply(block)
    this.setItem(slot, stack)
    return stack
}
