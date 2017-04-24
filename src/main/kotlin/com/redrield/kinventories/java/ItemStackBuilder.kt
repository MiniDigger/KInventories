package com.redrield.kinventories.java

import com.redrield.kinventories.kotlin.itemMeta
import com.redrield.kinventories.kotlin.onClick
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.function.Consumer

class ItemStackBuilder(material: Material, amount: Int) {
    val inner = ItemStack(material, amount)

    fun withMeta(block: Consumer<ItemMeta>): ItemStackBuilder {
        this.inner.itemMeta { block::accept }
        return this
    }

    fun onClick(block: Consumer<Player>): ItemStackBuilder {
        this.inner.onClick(block::accept)
        return this
    }

    fun build(): ItemStack = inner
}
