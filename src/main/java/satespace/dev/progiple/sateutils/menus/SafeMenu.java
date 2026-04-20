package satespace.dev.progiple.sateutils.menus;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.menus.AMenu;
import org.novasparkle.lunaspring.API.menus.items.Item;
import satespace.dev.progiple.sateutils.code.ICode;
import satespace.dev.progiple.sateutils.menus.items.ICodeItem;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class SafeMenu<T> extends AMenu implements ICodeMenu<T> {
    private final ICode<T> code;
    private final List<T> picked = new ArrayList<>();
    public SafeMenu(@NotNull Player player, String title, @Range(from = 9L, to = 54L) byte size, ICode<T> code) {
        super(player, title, size);
        this.code = code;
    }

    public SafeMenu(@NotNull Player player, String title, InventoryType type, ICode<T> code) {
        super(player, title, type);
        this.code = code;
    }

    public SafeMenu(@NotNull Player player, ConfigurationSection menuSection, ICode<T> code) {
        super(player, menuSection);
        this.code = code;
    }

    public SafeMenu(@NotNull Player player, String title, @Range(from = 9L, to = 54L) byte size, ConfigurationSection decorSection, ICode<T> code) {
        super(player, title, size, decorSection);
        this.code = code;
    }

    public SafeMenu(@NotNull Player player, String title, InventoryType type, ConfigurationSection decorSection, ICode<T> code) {
        super(player, title, type, decorSection);
        this.code = code;
    }

    public SafeMenu(@NotNull Player player, ICode<T> code) {
        super(player);
        this.code = code;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        super.onClick(e);
    }

    @Override
    public void reset() {
        this.picked.clear();
        for (Item item : this.getItemList()) {
            if (item instanceof ICodeItem<?> ict)
                ict.setPicked(false);
        }
    }
}
