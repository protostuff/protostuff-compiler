package io.protostuff.compiler.model;

/**
 * Groups tree node.
 * Groups are deprecated as of proto2 and are not supported by proto3.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class Group extends Message {

    public Group(UserTypeContainer parent) {
        super(parent);
    }

    @Override
    public DescriptorType getDescriptorType() {
        return DescriptorType.GROUP;
    }
}
