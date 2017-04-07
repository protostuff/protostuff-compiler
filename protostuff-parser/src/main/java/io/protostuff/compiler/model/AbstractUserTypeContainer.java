package io.protostuff.compiler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Container for user types. Protocol buffers have two main elements that can contain
 * children - proto file node and message.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractUserTypeContainer extends AbstractDescriptor implements UserTypeContainer {

    protected final UserTypeContainer parent;
    protected List<Message> messages;
    protected List<Enum> enums;
    protected List<Extension> declaredExtensions;

    public AbstractUserTypeContainer(UserTypeContainer parent) {
        this.parent = parent;
    }

    @Override
    public UserTypeContainer getParent() {
        return parent;
    }

    @Override
    public List<Message> getMessages() {
        if (messages == null) {
            return Collections.emptyList();
        }
        return messages;
    }

    @Override
    public void addMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

    @Override
    public List<Enum> getEnums() {
        if (enums == null) {
            return Collections.emptyList();
        }
        return enums;
    }

    public void setEnums(List<Enum> enums) {
        this.enums = enums;
    }

    @Override
    public void addEnum(Enum e) {
        if (enums == null) {
            enums = new ArrayList<>();
        }
        enums.add(e);
    }

    @Override
    public List<Extension> getDeclaredExtensions() {
        if (declaredExtensions == null) {
            return Collections.emptyList();
        }
        return declaredExtensions;
    }

    @Override
    public void addDeclaredExtension(Extension extension) {
        if (declaredExtensions == null) {
            declaredExtensions = new ArrayList<>();
        }
        declaredExtensions.add(extension);
    }
}
