package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;

/**
 * Reserved tag range.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class Range extends AbstractElement {

    private final Message parent;
    private final int from;
    private final int to;

    /**
     * Create new range node.
     *
     * @param parent message that contains this element
     * @param from range start, inclusive
     * @param to range end, inclusive
     */
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Range range = (Range) o;
        return from == range.from
                && to == range.to
                && Objects.equals(parent, range.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, from, to);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("from", from)
                .add("to", to)
                .toString();
    }
}
