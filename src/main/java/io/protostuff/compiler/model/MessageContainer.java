package io.protostuff.compiler.model;

import java.util.List;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public interface MessageContainer {

    List<Message> getMessages();

    void addMessage(Message message);

}
