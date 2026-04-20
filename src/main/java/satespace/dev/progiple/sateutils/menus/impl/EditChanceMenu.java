package satespace.dev.progiple.sateutils.menus.impl;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.configuration.Configuration;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.menus.items.NonMenuItem;
import org.novasparkle.lunaspring.API.util.utilities.rarities.loot.Loot;
import satespace.dev.progiple.sateutils.menus.items.impl.EditChanceItem;

public class EditChanceMenu extends EditLootMenu {
    public EditChanceMenu(@NotNull Player player, String title, @Range(from = 9L, to = 54L) byte size, Configuration lootConfig) {
        super(player, title, size, lootConfig);
    }

    public EditChanceMenu(@NotNull Player player, String title, InventoryType type, Configuration lootConfig) {
        super(player, title, type, lootConfig);
    }

    public EditChanceMenu(@NotNull Player player, ConfigurationSection menuSection, Configuration lootConfig) {
        super(player, menuSection, lootConfig);
    }

    public EditChanceMenu(@NotNull Player player, String title, @Range(from = 9L, to = 54L) byte size, ConfigurationSection decorSection, Configuration lootConfig) {
        super(player, title, size, decorSection, lootConfig);
    }

    public EditChanceMenu(@NotNull Player player, String title, InventoryType type, ConfigurationSection decorSection, Configuration lootConfig) {
        super(player, title, type, decorSection, lootConfig);
    }

    public EditChanceMenu(@NotNull Player player, Configuration lootConfig) {
        super(player, lootConfig);
    }

    @Override
    protected void scanSection(int slot, ConfigurationSection subSection) {
        ItemStack itemStack = Loot.getStackFromSection(subSection);
        if (itemStack == null) return;

        var item = new EditChanceItem(NonMenuItem.fromItemStack(itemStack), (byte) slot);
        item.setChance(subSection.getDouble("chance"));
        itemOperation(item);

        this.addItems(true, item);
    }

    protected void itemOperation(EditChanceItem item) {
        item.updateLore();
    }

    @Override
    public void clickToItemStack(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        for (Item iu : this.findItems(EditChanceItem.class)) {
            var item = (EditChanceItem) iu;

            String path = this.getLootPath() + "." + (item.getSlot() * this.getPage()) + ".chance";
            this.getLootConfig().setDouble(path, item.getChance());
        }

        this.getLootConfig().save();
    }
}
