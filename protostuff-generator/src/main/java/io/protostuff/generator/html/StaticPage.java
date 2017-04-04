package io.protostuff.generator.html;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.File;

public class StaticPage {

    private String name;
    private File file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StaticPage page = (StaticPage) o;
        return Objects.equal(name, page.name)
                && Objects.equal(file, page.file);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, file);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("file", file)
                .toString();
    }
}
