package com.redrield.kinventories.kotlin

import com.redrield.kinventories.KInventories
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

@JvmSynthetic
fun ItemStack.onClick(callback: (Player) -> Unit) = KInventories.ITEMSTACK_ACTIONS.put(this, callback)

@JvmSynthetic
inline fun ItemStack.itemMeta(block: ItemMeta.() -> Unit) {
    this.itemMeta = this.itemMeta.apply(block)
}

