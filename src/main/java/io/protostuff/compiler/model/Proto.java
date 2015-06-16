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
    protected List<String> imports;
    protected List<String> publicImports;
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

    public List<String> getPublicImports() {
        if (publicImports == null) {
            return Collections.emptyList();
        }
        return publicImports;
    }

    public void setPublicImports(List<String> publicImports) {
        this.publicImports = publicImports;
    }

    public void addPublicImport(String file) {
        if (publicImports == null) {
            publicImports = new ArrayList<>();
        }
        publicImports.add(file);
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
    public String getNamespace() {
        if (packageName == null) {
            return ".";
        }
        return "." + packageName + ".";
    }
}
