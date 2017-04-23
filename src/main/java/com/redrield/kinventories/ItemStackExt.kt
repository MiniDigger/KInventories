package com.redrield.kinventories

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta


fun ItemStack.onClick(callback: (Player) -> Unit) = KInventories.ITEMSTACK_ACTIONS.put(this, callback)

inline fun ItemStack.itemMeta(block: ItemMeta.() -> Unit) {
    this.itemMeta = this.itemMeta.apply(block)
}

