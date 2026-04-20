package satespace.dev.progiple.sateutils.menus.items;

public interface ICodeItem<T> {
    T getCodeElement();
    boolean isPicked();
    void setPicked(boolean val);
}
