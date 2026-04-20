package satespace.dev.progiple.sateutils.code;

import org.novasparkle.lunaspring.API.util.utilities.LunaMath;

@FunctionalInterface
public interface CodeMask<T> {
    T generate(int length, int index);

    static CodeMask<Integer> getIntMask() {
        return (l, i) -> LunaMath.getRandomInt(0, l);
    }
}
