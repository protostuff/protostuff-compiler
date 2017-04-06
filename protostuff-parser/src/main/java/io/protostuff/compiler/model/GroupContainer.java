package io.protostuff.compiler.model;

import java.util.List;

/**
 * Container for group nodes.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface GroupContainer {

    List<Group> getGroups();

    void setGroups(List<Group> groups);

    void addGroup(Group group);
}
