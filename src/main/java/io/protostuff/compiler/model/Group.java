package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
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
