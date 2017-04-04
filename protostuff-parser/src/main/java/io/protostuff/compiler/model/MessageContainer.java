package io.protostuff.compiler.model;

import java.util.List;
import javax.annotation.Nullable;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface MessageContainer {

    List<Message> getMessages();

    /**
     * Get message that is declared under this container.
     *
     * @param name the message's short name
     *
     * @return message instance or null if message with given name is not declared under
     * this container
     */
    @Nullable
    default Message getMessage(String name) {
        for (Message message : getMessages()) {
            if (name.equals(message.getName())) {
                return message;
            }
        }
        return null;
    }

    void addMessage(Message message);

}
