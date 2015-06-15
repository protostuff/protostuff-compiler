package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * Extension range
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ExtensionRange {
    private final int min;
    private final int max;

    public ExtensionRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("min", min)
                .add("max", max)
                .toString();
    }
}
