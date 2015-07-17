package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * Extension range
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ExtensionRange extends AbstractElement {

    private final Message parent;
    private final int min;
    private final int max;

    public ExtensionRange(Message parent, int min, int max) {
        this.parent = parent;
        this.min = min;
        this.max = max;
    }

    @Override
    public Message getParent() {
        return parent;
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
