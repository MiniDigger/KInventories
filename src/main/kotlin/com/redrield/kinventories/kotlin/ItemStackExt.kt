package com.redrield.kinventories.kotlin

import com.redrield.kinventories.KInventories
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Function to add an [org.bukkit.event.inventory.InventoryClickEvent] callback to the receiver
 * @param callback The block to be called in the event
 */
@JvmSynthetic
fun ItemStack.onClick(callback: ItemStack.(Player) -> Unit) = KInventories.ITEMSTACK_ACTIONS.put(this, callback)

/**
 * Function to add ItemMeta to an [ItemStack]
 * @param block The block to be applied to the stack's [ItemMeta]
 */
@JvmSynthetic
inline fun ItemStack.itemMeta(block: ItemMeta.() -> Unit) {
    this.itemMeta = this.itemMeta.apply(block)
}

