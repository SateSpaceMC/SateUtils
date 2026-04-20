package satespace.dev.progiple.sateutils.code;

import java.util.Collection;
import java.util.List;

public interface ICode<T> {
    int getLength();
    List<T> getCombination();
    boolean check(T... vals);
    boolean check(List<T> vals);
    boolean simpleCheck(Collection<T> vals);
}
