package satespace.dev.progiple.sateutils.menus.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.menus.items.NonMenuItem;
import org.novasparkle.lunaspring.API.util.utilities.LunaMath;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class EditChanceItem extends Item {
    private double chance;

    public EditChanceItem(Material material, String displayName, List<String> lore, int amount, @Range(from = 0L, to = 53L) byte slot) {
        super(material, displayName, lore, amount, slot);
    }

    public EditChanceItem(Material material, int amount) {
        super(material, amount);
    }

    public EditChanceItem(NonMenuItem nonMenuItem, @Range(from = 0L, to = 53L) byte slot) {
        super(nonMenuItem, slot);
    }

    public EditChanceItem() {
    }

    public EditChanceItem(Material material) {
        super(material);
    }

    public EditChanceItem(Material material, @Range(from = 0L, to = 53L) byte slot) {
        super(material, slot);
    }

    public EditChanceItem(@NotNull ConfigurationSection section, @Range(from = 0L, to = 53L) int slot) {
        super(section, slot);
    }

    public EditChanceItem(@NotNull ConfigurationSection section) {
        super(section);
    }

    public EditChanceItem(@NotNull ConfigurationSection section, boolean rowCol) {
        super(section, rowCol);
    }

    @Override
    public Item onClick(InventoryClickEvent e) {
        this.chance = chance + rpcChance(e);
        this.chance = LunaMath.round(Math.max(0, Math.min(chance, 100.0)), 1);

        updateLore();
        insert();

        e.setCancelled(true);
        return super.onClick(e);
    }

    protected double rpcChance(InventoryClickEvent e) {
        return switch (e.getClick()) {
            case LEFT -> 1;
            case RIGHT -> -1;
            case SHIFT_LEFT -> 10;
            case SHIFT_RIGHT -> -10;
            case DROP -> -0.1;
            case CONTROL_DROP -> 0.1;
            case NUMBER_KEY -> {
                int key = e.getHotbarButton();
                yield Math.max(key + 1, 0);
            }
            case MIDDLE -> 50;
            case SWAP_OFFHAND -> -50;
            default -> 0;
        };
    }

    public void updateLore() {
        var lore = new ArrayList<>(this.getDefaultLore());
        this.setLore(lore, "chance-%-" + this.chance);
    }
}
