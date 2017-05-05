package com.redrield.kinventories.java

import com.redrield.kinventories.kotlin.itemMeta
import com.redrield.kinventories.kotlin.onClick
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.function.BiFunction
import java.util.function.Consumer

/**
 * Class representing an [ItemStack] that is being constructed for a [InventoryBuilder]
 */
class ItemStackBuilder internal constructor(material: Material, amount: Int) {
    /**
     * The internal [ItemStack] of this object
     */
    var inner = ItemStack(material, amount)

    /**
     * Callback with ItemMeta to add meta properties to the internal [ItemStack]
     * @param block The block to be executed on the meta of [inner]
     * @return instance, for chaining
     */
    fun withMeta(block: Consumer<ItemMeta>): ItemStackBuilder {
        this.inner.itemMeta(block::accept)
        return this
    }

    /**
     * Callback to be executed when the item is clicked
     * @param block The callback to be executed
     * @return instance, for chaining
     */
    fun onClick(block: Consumer<Player>): ItemStackBuilder {
        this.inner.onClick {
            block.accept(it)
        }
        return this
    }

    internal fun build(): ItemStack = inner
}
