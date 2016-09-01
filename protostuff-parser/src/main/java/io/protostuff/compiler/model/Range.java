package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

/**
 * Reserved tag range
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class Range extends AbstractElement {

    private final Message parent;
    private final int from;
    private final int to;

    public Range(Message parent, int from, int to) {
        this.parent = parent;
        this.from = from;
        this.to = to;
    }

    @Override
    public Message getParent() {
        return parent;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean contains(int tag) {
        return tag >= from && tag <= to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range)) return false;

        Range range = (Range) o;

        if (from != range.from) return false;
        if (to != range.to) return false;
        return parent != null ? parent.equals(range.parent) : range.parent == null;

    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + from;
        result = 31 * result + to;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("from", from)
                .add("to", to)
                .toString();
    }
}
