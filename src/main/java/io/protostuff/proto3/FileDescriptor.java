package io.protostuff.proto3;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
@Immutable
public class FileDescriptor extends AbstractUserTypeContainer {

    protected String syntax;
    protected String packageName;
    protected List<String> imports;

    @Nullable
    public String getSyntax() {
        return syntax;
    }

    @Nullable
    public String getPackageName() {
        return packageName;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public List<String> getImports() {
        if (imports == null) {
            return Collections.emptyList();
        }
        return imports;
    }

    public void addImport(String file) {
        if (imports == null) {
            imports = new ArrayList<>();
        }
        imports.add(file);
    }

}
