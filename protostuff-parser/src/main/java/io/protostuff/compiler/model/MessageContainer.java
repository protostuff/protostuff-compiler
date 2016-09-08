package io.protostuff.compiler.model;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface MessageContainer {

    List<Message> getMessages();

    /**
     * Get message that is declared under this container.
     *
     * @param name the message's short name
     * @return message instance or null if message with given name is not declared under
     * this container
     */
    @Nullable
    Message getMessage(String name);

    void addMessage(Message message);

}
