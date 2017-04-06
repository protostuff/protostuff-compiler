package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import io.protostuff.compiler.parser.ProtoContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Proto node - represents a parse result for a proto file.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class Proto extends AbstractUserTypeContainer implements UserTypeContainer {

    protected Module module;
    protected ProtoContext context;
    protected String filename;
    protected Syntax syntax = Syntax.DEFAULT;
    protected Package pkg = Package.DEFAULT;
    protected List<Import> imports = new ArrayList<>();
    protected List<Service> services = new ArrayList<>();

    public Proto() {
        // proto does not have parent
        super(null);
    }

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
     * Full filename (including path, relative to root of source tree).
     */
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Syntax getSyntax() {
        return syntax;
    }

    public void setSyntax(Syntax syntax) {
        this.syntax = syntax;
    }

    public Package getPackage() {
        return pkg;
    }

    public void setPackage(Package pkg) {
        this.pkg = pkg;
    }

    public List<Import> getImports() {
        return imports;
    }

    public void setImports(List<Import> imports) {
        this.imports = imports;
    }

    /**
     * Returns all public imports in this proto file.
     */
    public List<Import> getPublicImports() {
        return getImports()
                .stream()
                .filter(Import::isPublic)
                .collect(Collectors.toList());
    }

    public void addImport(Import anImport) {
        imports.add(anImport);
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
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
        if (pkg == null) {
            return ".";
        }
        return "." + pkg.getValue() + ".";
    }

    /**
     * Get canonical name of this proto file.
     * Canonical name is composed as package and file name,
     * separated by dot.
     */
    public String getCanonicalName() {
        if (pkg == null) {
            return name;
        }
        return pkg + "." + name;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
