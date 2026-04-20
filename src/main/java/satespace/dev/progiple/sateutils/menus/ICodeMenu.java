package satespace.dev.progiple.sateutils.menus;

import org.novasparkle.lunaspring.API.menus.IMenu;
import satespace.dev.progiple.sateutils.code.ICode;

import java.util.List;

public interface ICodeMenu<T> extends IMenu {
    ICode<T> getCode();
    List<T> getPicked();
    void solve();
    void reset();
}
