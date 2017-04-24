package com.redrield.kinventories.kotlin

import com.redrield.kinventories.KInventories
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

@JvmSynthetic
fun inventory(name: String, size: Int, block: Inventory.() -> Unit): Inventory {
    val inv = Bukkit.createInventory(null, size, name).apply(block)
    KInventories.REGISTERED_INVENTORIES.add(inv)
    return inv
}

@JvmSynthetic
fun Inventory.onOpen(block: (Player) -> Unit) {
    KInventories.INVENTORY_OPEN_ACTIONS.put(this, block)
}

@JvmSynthetic
fun Inventory.onClose(block: (Player) -> Unit) {
    KInventories.INVENTORY_CLOSE_ACTIONS.put(this, block)
}

@JvmSynthetic
inline fun Inventory.itemStack(material: Material, amount: Int = 1, block: ItemStack.() -> Unit): ItemStack {
    val stack = ItemStack(material, amount).apply(block)
    this.addItem(stack)
    return stack
}

@JvmSynthetic
inline fun Inventory.itemStack(material: Material, amount: Int = 1, slot: Int, block: ItemStack.() -> Unit): ItemStack {
    val stack = ItemStack(material, amount).apply(block)
    this.setItem(slot, stack)
    return stack
}
