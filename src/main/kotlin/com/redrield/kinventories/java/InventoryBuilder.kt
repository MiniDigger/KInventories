package com.redrield.kinventories.java

import com.redrield.kinventories.KInventories
import com.redrield.kinventories.kotlin.onClose
import com.redrield.kinventories.kotlin.onOpen
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.util.function.Consumer

/**
 * Main class for java interop code for KInventories.
 * Represents an inventory that is in the process of being created. Can retrieve the underlying inventory with [build]
 */
class InventoryBuilder private constructor(val inventory: Inventory) {

    companion object {
        /**
         * Function to create an empty inventory builder
         * @param name the name of the inventory
         * @param size The size of the inventory. Must be a multiple of 9
         * @return a new InventoryBuilder with the set name and size
         */
        @JvmStatic fun create(name: String, size: Int) = InventoryBuilder(Bukkit.createInventory(null, size, name))
        @JvmStatic fun create(size: Int) = InventoryBuilder(Bukkit.createInventory(null, size))
    }

    /**
     * Function to add an item to the current builder
     * @param material The material of the [ItemStack](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/ItemStack.html) to be created
     * @param amount The amount of items in the [ItemStack](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/ItemStack.html) to be created
     * @param block The callback to add data to the [ItemStack](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/ItemStack.html)
     * @return instance, for chaining
     * @see java.util.function.Consumer
     * @see org.bukkit.inventory.ItemStack
     */
    fun addItem(material: Material, amount: Int, block: Consumer<ItemStackBuilder>): InventoryBuilder {
        val item = ItemStackBuilder(material, amount)
        block.accept(item)
        this.inventory.addItem(item.build())
        return this
    }

    /**
     * Function to add an item to the current builder in a particular slot
     * @param material The material of the [org.bukkit.inventory.ItemStack] to be created
     * @param amount The amount of items in the [org.bukkit.inventory.ItemStack] to be created
     * @param slot The slot in which to add the [org.bukkit.inventory.ItemStack] in the internal inventory
     * @param block The callback to add data to the [org.bukkit.inventory.ItemStack]
     * @return instance, for chaining
     */
    fun addItem(material: Material, amount: Int, slot: Int, block: Consumer<ItemStackBuilder>): InventoryBuilder {
        val item = ItemStackBuilder(material, amount)
        block.accept(item)
        this.inventory.setItem(slot, item.build())
        return this
    }

    /**
     * Function to set the callback that is called when the inventory is opened
     * @param block The callback for [org.bukkit.event.inventory.InventoryOpenEvent] triggered with this Inventory
     * @return instance, for chaining
     */
    fun setOnOpen(block: Consumer<Player>): InventoryBuilder {
        this.inventory.onOpen(block::accept)
        return this
    }

    /**
     * Function to set the callback that is called when the inventory is closed
     * @param block The callback for [org.bukkit.event.inventory.InventoryCloseEvent] triggered with this Inventory
     */
    fun setOnClose(block: Consumer<Player>): InventoryBuilder {
        this.inventory.onClose(block::accept)
        return this
    }

    /**
     * Registers this inventory and constructs it based on the given properties
     * @return The internal inventory.
     */
    fun build(): Inventory {
        KInventories.REGISTERED_INVENTORIES.add(inventory)
        return inventory
    }
}
