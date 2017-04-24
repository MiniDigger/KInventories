package com.redrield.kinventories.java

import com.redrield.kinventories.KInventories
import com.redrield.kinventories.kotlin.onClose
import com.redrield.kinventories.kotlin.onOpen
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.util.function.Consumer

class InventoryBuilder private constructor(val inventory: Inventory) {

    companion object {
        @JvmStatic fun create(name: String, size: Int) = InventoryBuilder(Bukkit.createInventory(null, size, name))
    }

    fun addItem(material: Material, amount: Int, block: Consumer<ItemStackBuilder>): InventoryBuilder {
        val item = ItemStackBuilder(material, amount)
        block.accept(item)
        this.inventory.addItem(item.build())
        return this
    }

    fun addItem(material: Material, amount: Int, slot: Int, block: Consumer<ItemStackBuilder>): InventoryBuilder {
        val item = ItemStackBuilder(material, amount)
        block.accept(item)
        this.inventory.setItem(slot, item.build())
        return this
    }

    fun setOnOpen(block: Consumer<Player>) {
        this.inventory.onOpen(block::accept)
    }

    fun setOnClose(block: Consumer<Player>) {
        this.inventory.onClose(block::accept)
    }

    fun build(): Inventory {
        KInventories.REGISTERED_INVENTORIES.add(inventory)
        return inventory
    }
}
