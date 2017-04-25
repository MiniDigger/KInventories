# KINventories


##Documentation
There aren't compiled javadocs as of yet, but the code does have comments to assist you.

## Basic usage (Kotlin)
KInventories works on a series of inline functions and extension functions. Declaring a basic unnamed inventory looks like this:
```kotlin
inventory(9) {
    
}
```
This declares an inventory with size 9. Everything in the following block is applied to the inventory. This is how you would add items to the inventories.

```kotlin
inventory(9) {
    itemStack(Material.ANVIL) {
        
    }
}
```
This declares an inventory with an anvil in the first slot. Beyond this, you can add properties to the item in its block. To access the meta of the item, you can use `itemMeta { }` 

```kotlin
inventory(9) {
    itemStack(Material.ANVIL) {
        itemMeta {
            displayName = "Hello World!"
        }
    }
}
```
This would create an inventory with an item named "Hello World!". The final thing that you'll need to know is how to register callbacks. KInventories provides you with 3 possible callbacks to register: Item click callbacks (`onClick { }`), inventory open callbacks (`onOpen { }`), and inventory close callbacks (`onClose { }`).
All available methods will be available and documented ASAP

##Getting started (Java)
The Java portion of the API is significantly less graceful than the Kotlin portion, because of limitations in what I can do with the language. Declaring an inventory in Java looks like this
```java
InventoryBuilder.create(9).build();
```
This creates an InventoryBuilder that contains an Inventory of size 9, then immediately builds it. The resulting inventory is of course empty. To add items to it, you would use `addItem()` before calling build
```java
InventoryBuilder.create(9)
                .addItem(Material.ANVIL, 1, item -> {
                    
                }).build();
```
This adds 1 anvil to the first slot of the inventory. the Consumer that is provided can be used to add data to the item before adding it to the inventory, like so:

```java
InventoryBuilder.create(9)
                .addItem(Material.ANVIL, 1, item -> {
                    item.withMeta(meta -> {
                        meta.setDisplayName("Hello World");
                    });
                }).build();
```
Like I said, it's not graceful. This would add an item with the meta you add to it. Beyond that, you can add anything to the ItemStack by manipulating the `inner` field in ItemStackBuilder that is made available to you.

