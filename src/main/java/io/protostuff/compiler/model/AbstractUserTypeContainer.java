package io.protostuff.compiler.model;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractUserTypeContainer extends AbstractDescriptor implements UserTypeContainer {

    protected List<Message> messages;
    protected List<Enum> enums;
    protected List<Extension> extensions;

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
    public List<Extension> getExtensions() {
        if (extensions == null) {
            return Collections.emptyList();
        }
        return extensions;
    }

    @Override
    public void addExtension(Extension extension) {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        extensions.add(extension);
    }
}
