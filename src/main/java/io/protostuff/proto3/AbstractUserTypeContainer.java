package io.protostuff.proto3;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractUserTypeContainer extends AbstractDescriptor {

    protected List<Message> messages;
    protected List<Enum> enums;

    public List<Message> getMessages() {
        return messages;
    }

    public List<Enum> getEnums() {
        return enums;
    }

    public void addMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

    public void addEnum(Enum e) {
        if (enums == null) {
            enums = new ArrayList<>();
        }
        enums.add(e);
    }

}
