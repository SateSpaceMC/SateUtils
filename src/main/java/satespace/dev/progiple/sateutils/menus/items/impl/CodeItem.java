package satespace.dev.progiple.sateutils.menus.items.impl;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.menus.items.NonMenuItem;
import satespace.dev.progiple.sateutils.code.ICode;
import satespace.dev.progiple.sateutils.menus.ICodeMenu;
import satespace.dev.progiple.sateutils.menus.items.ICodeItem;

import java.util.List;

@Getter
public class CodeItem<T> extends Item implements ICodeItem<T> {
    private final T codeElement;
    private boolean picked = false;
    public CodeItem(Material material, String displayName, List<String> lore, int amount, @Range(from = 0L, to = 53L) byte slot, T codeElement) {
        super(material, displayName, lore, amount, slot);
        this.codeElement = codeElement;
    }

    public CodeItem(Material material, int amount, T codeElement) {
        super(material, amount);
        this.codeElement = codeElement;
    }

    public CodeItem(NonMenuItem nonMenuItem, @Range(from = 0L, to = 53L) byte slot, T codeElement) {
        super(nonMenuItem, slot);
        this.codeElement = codeElement;
    }

    public CodeItem(T codeElement) {
        this.codeElement = codeElement;
    }

    public CodeItem(Material material, T codeElement) {
        super(material);
        this.codeElement = codeElement;
    }

    public CodeItem(Material material, @Range(from = 0L, to = 53L) byte slot, T codeElement) {
        super(material, slot);
        this.codeElement = codeElement;
    }

    public CodeItem(@NotNull ConfigurationSection section, @Range(from = 0L, to = 53L) int slot, T codeElement) {
        super(section, slot);
        this.codeElement = codeElement;
    }

    public CodeItem(@NotNull ConfigurationSection section, T codeElement) {
        super(section);
        this.codeElement = codeElement;
    }

    public CodeItem(@NotNull ConfigurationSection section, boolean rowCol, T codeElement) {
        super(section, rowCol);
        this.codeElement = codeElement;
    }

    @Override @SuppressWarnings("unchecked")
    public Item onClick(InventoryClickEvent e) {
        if (this.getMenu() instanceof ICodeMenu<?>) {
            ICodeMenu<T> icm = (ICodeMenu<T>) this.getMenu();
            if (icm.getPicked().contains(this.codeElement)) return this;

            icm.getPicked().add(this.codeElement);

            ICode<T> code = icm.getCode();
            if (code.check(icm.getPicked())) {
                icm.solve();
            }
            else if (code.getLength() <= icm.getPicked().size()) {
                icm.reset();
            }
            else {
                this.setPicked(true);
            }
        }

        return this;
    }

    @Override
    public void setPicked(boolean picked) {
        this.picked = picked;
        this.setGlowing(picked);
        insert();
    }
}
