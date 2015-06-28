package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import io.protostuff.compiler.parser.ProtoContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class Proto extends AbstractUserTypeContainer implements UserTypeContainer {

    protected ProtoContext context;
    protected String filename;
    protected Syntax syntax;
    protected Package aPackage;
    protected List<Import> imports;
    protected List<Service> services;

    public ProtoContext getContext() {
        return context;
    }

    public void setContext(ProtoContext context) {
        this.context = context;
    }

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.PROTO;
    }

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

    public Syntax getSyntax() {
        if (syntax == null) {
            return Syntax.DEFAULT;
        }
        return syntax;
    }

    public void setSyntax(Syntax syntax) {
        this.syntax = syntax;
    }

    public boolean isSyntaxSet() {
        return syntax != null;
    }

    public Package getPackage() {
        if (aPackage == null) {
            return Package.DEFAULT;
        }
        return aPackage;
    }

    public void setPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public boolean isPackageSet() {
        return aPackage != null;
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

    public List<Import> getPublicImports() {
        return getImports()
                .stream()
                .filter(Import::isPublic)
                .collect(Collectors.toList());
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
        if (aPackage == null) {
            return ".";
        }
        return "." + aPackage.getValue() + ".";
    }
}
