package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Proto extends AbstractUserTypeContainer implements UserTypeContainer {

    protected String syntax;
    protected String packageName;
    protected List<String> imports;

    /**
     * file name, relative to root of source tree
     */
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Nullable
    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    @Nullable
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getImports() {
        if (imports == null) {
            return Collections.emptyList();
        }
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public void addImport(String file) {
        if (imports == null) {
            imports = new ArrayList<>();
        }
        imports.add(file);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", getName())
                .add("syntax", syntax)
                .add("package", packageName)
                .add("imports", imports)
                .add("messages", getMessages())
                .add("enums", getEnums())
                .add("options", getOptions())
                .toString();
    }

    @Override
    public String getNamespacePrefix() {
        if (packageName == null) {
            return ".";
        }
        return "." + packageName + ".";
    }
}
