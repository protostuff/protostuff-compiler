package io.protostuff.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractUserTypeContainer extends AbstractDescriptor implements UserTypeContainer {

    protected List<Message> messages;
    protected List<Enum> enums;

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

}
