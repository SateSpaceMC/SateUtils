package satespace.dev.progiple.sateutils.menus.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.configuration.Configuration;
import org.novasparkle.lunaspring.API.menus.AMenu;
import org.novasparkle.lunaspring.API.util.utilities.rarities.loot.Loot;

@Getter @Setter
public class EditLootMenu extends AMenu {
    private Configuration lootConfig;
    private int page = 1;
    private String lootPath = "items";

    public EditLootMenu(@NotNull Player player, String title, @Range(from = 9L, to = 54L) byte size, Configuration lootConfig) {
        super(player, title, size);
        this.lootConfig = lootConfig;
    }

    public EditLootMenu(@NotNull Player player, String title, InventoryType type, Configuration lootConfig) {
        super(player, title, type);
        this.lootConfig = lootConfig;
    }

    public EditLootMenu(@NotNull Player player, ConfigurationSection menuSection, Configuration lootConfig) {
        super(player, menuSection);
        this.lootConfig = lootConfig;
    }

    public EditLootMenu(@NotNull Player player, String title, @Range(from = 9L, to = 54L) byte size, ConfigurationSection decorSection, Configuration lootConfig) {
        super(player, title, size, decorSection);
        this.lootConfig = lootConfig;
    }

    public EditLootMenu(@NotNull Player player, String title, InventoryType type, ConfigurationSection decorSection, Configuration lootConfig) {
        super(player, title, type, decorSection);
        this.lootConfig = lootConfig;
    }

    public EditLootMenu(@NotNull Player player, Configuration lootConfig) {
        super(player);
        this.lootConfig = lootConfig;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        var decor = this.getDecoration();
        if (decor != null && decor.checkSlot((byte) event.getSlot())) {
            event.setCancelled(true);
            return;
        }

        if (this.itemClick(event)) {
            event.setCancelled(true);
            return;
        }

        clickToItemStack(event);
    }

    public void clickToItemStack(InventoryClickEvent e) {}

    @Override
    public void onOpen(InventoryOpenEvent e) {
        ConfigurationSection section = this.lootConfig.getSection(lootPath);
        if (section == null) return;

        int size = this.getSize();
        for (int slot = 0; slot < size; slot++) {
            String key = String.valueOf(slot * page);

            ConfigurationSection subSection = section.getConfigurationSection(key);
            if (subSection == null) continue;

            scanSection(slot, subSection);
        }
    }

    protected void scanSection(int slot, ConfigurationSection subSection) {
        ItemStack itemStack = Loot.getStackFromSection(subSection);

        if (itemStack == null) return;
        this.getInventory().setItem(slot, itemStack);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        int size = this.getSize();
        for (int slot = 0; slot < size; slot++) {
            String path = lootPath + "." + (slot * page) + ".item";
            this.lootConfig.setItemStack(path, this.getInventory().getItem(slot));
        }

        this.lootConfig.save();
        super.onClose(event);
    }
}
