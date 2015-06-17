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

    protected String filename;
    protected Syntax syntax;
    protected String packageName;
    protected List<Import> imports;
    protected List<Service> services;

    /**
     * Full filename (including path, relative to root of source tree)
     */
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Filename without extension
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
    public Syntax getSyntax() {
        return syntax;
    }

    public void setSyntax(Syntax syntax) {
        this.syntax = syntax;
    }

    @Nullable
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Import> getImports() {
        if (imports == null) {
            return Collections.emptyList();
        }
        return imports;
    }

    public void setImports(List<Import> imports) {
        this.imports = imports;
    }

    public void addImport(Import anImport) {
        if (imports == null) {
            imports = new ArrayList<>();
        }
        imports.add(anImport);
    }

    public List<Service> getServices() {
        if (services == null) {
            return Collections.emptyList();
        }
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        if (services == null) {
            services = new ArrayList<>();
        }
        services.add(service);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("filename", filename)
                .toString();
    }

    @Override
    public String getNamespace() {
        if (packageName == null) {
            return ".";
        }
        return "." + packageName + ".";
    }
}
