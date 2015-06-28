package io.protostuff.compiler.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.protostuff.compiler.parser.ParserException;
import io.protostuff.compiler.parser.Util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class DynamicMessage implements Map<String, DynamicMessage.Value>{

    public static final char LPAREN = '(';
    public static final char RPAREN = ')';
    public static final char DOT = '.';

    private final Map<Key, Value> fields;
    private Message type;

    public DynamicMessage() {
        this.fields = new HashMap<>();
    }

    /**
     * Get option value by given option name.
     */
    public Value get(String name) {
        if (name.length() > 1) {
            int dot;
            if (name.charAt(0) == LPAREN) {
                int start = name.indexOf(RPAREN);
                dot = name.indexOf(DOT, start);
            } else {
                dot = name.indexOf(DOT);
            }
            if (dot > 0) {
                String fieldName = name.substring(0, dot);
                String rest = name.substring(dot + 1);
                Key key = createKey(fieldName);
                Value value = fields.get(key);
                if (!value.isMessageType()) {
                    throw new ParserException("Invalid option name: %s", name);
                }
                DynamicMessage msg = value.getMessage();
                return msg.get(rest);
            } else {
                Key key = createKey(name);
                return fields.get(key);
            }
        } else {
            Key key = Key.field(name);
            return fields.get(key);
        }
    }

    public void set(String name, Value value) {
        if (name.length() > 1) {
            int dot;
            if (name.charAt(0) == LPAREN) {
                int start = name.indexOf(RPAREN);
                dot = name.indexOf(DOT, start);
            } else {
                dot = name.indexOf(DOT);
            }
            if (dot > 0) {
                String fieldName = name.substring(0, dot);
                String rest = name.substring(dot + 1);
                Key key = createKey(fieldName);
                DynamicMessage msg;
                if (fields.containsKey(key)) {
                    Value val = fields.get(key);
                    if (!val.isMessageType()) {
                        throw new ParserException("Can not assign option value: type error");
                    }
                    msg = val.getMessage();
                } else {
                    msg = new DynamicMessage();
                    fields.put(key, Value.createMessage(msg));
                }
                msg.set(rest, value);
            } else {
                Key key = createKey(name);
                set(key, value);
            }
        } else {
            Key key = Key.field(name);
            set(key, value);
        }
    }

    private void set(Key key, Value value) {
        if (fields.containsKey(key) && value.isMessageType()) {
            // merge
            Value prevValue = fields.get(key);
            if (!prevValue.isMessageType()) {
                throw new ParserException("Can not set option: %s", key);
            }
            DynamicMessage prevMessage = prevValue.getMessage();
            DynamicMessage message = value.getMessage();
            prevMessage.merge(message);
        } else {
            // create new or override previous value
            fields.put(key, value);
        }
    }

    private void merge(DynamicMessage message) {
        for (Map.Entry<Key, Value> entry : message.fields.entrySet()) {
            Key key = entry.getKey();
            Value value = entry.getValue();
            set(key, value);
        }
    }

    private Key createKey(String fieldName) {
        Key key;
        if (fieldName.startsWith("(")) {
            String name = Util.removeFirstAndLastChar(fieldName);
            key = Key.extension(name);
        } else {
            key = Key.field(fieldName);
        }
        return key;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("fields", fields)
                .toString();
    }

    @Override
    public int size() {
        return fields.size();
    }

    @Override
    public boolean isEmpty() {
        return fields.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (key instanceof String) {
            String name = (String) key;
            return get(name) != null;
        }
        return fields.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return fields.containsValue(value);
    }

    @Override
    public Value get(Object key) {
        if (key instanceof String) {
            String name = (String) key;
            return get(name);
        }
        return fields.get(key);
    }

    @Override
    public Value put(String key, Value value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Value remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(@Nonnull Map<? extends String, ? extends Value> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nonnull
    public Set<String> keySet() {
        Set<Key> keys = fields.keySet();
        return keys.stream()
                .map(Key::toString)
                .collect(Collectors.toSet());
    }

    @Override
    @Nonnull
    public Collection<Value> values() {
        return fields.values();
    }

    @Override
    @Nonnull
    public Set<Entry<String, Value>> entrySet() {
        Map<String, Value> map = new HashMap<>();
        for (Entry<Key, Value> entry : fields.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue());
        }
        return map.entrySet();
    }

    public Set<Entry<Key, Value>> getFields() {
        return fields.entrySet();
    }

    public static class Key {
        private final String name;
        private final boolean extension;

        public Key(String name, boolean extension) {
            this.name = name;
            this.extension = extension;
        }

        public static Key field(String name) {
            return new Key(name, false);
        }

        public static Key extension(String name) {
            return new Key(name, true);
        }

        public String getName() {
            return name;
        }

        public boolean isExtension() {
            return extension;
        }

        @Override
        @SuppressWarnings("SimplifiableIfStatement")
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;

            Key key = (Key) o;

            if (extension != key.extension) return false;
            return !(name != null ? !name.equals(key.name) : key.name != null);

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (extension ? 1 : 0);
            return result;
        }

        @Override
        public String toString() {
            if (extension) {
                return LPAREN + name + RPAREN;
            }
            return name;
        }
    }

    public static class Value {

        private final Type type;
        private final boolean bool;
        private final long number;
        private final double floatNumber;
        private final String string;
        private final String enumName;
        private final DynamicMessage message;

        private FieldType fieldType;

        private Value(Type type, Object value) {
            this.type = type;
            boolean b = false;
            double f = 0.;
            long l = 0;
            String e = null;
            String s = null;
            DynamicMessage m = null;
            switch (type) {
                case BOOLEAN:
                    b = (Boolean) value;
                    break;
                case INTEGER:
                    l = (Long) value;
                    break;
                case FLOAT:
                    f = (Double) value;
                    break;
                case STRING:
                    s = (String) value;
                    break;
                case ENUM:
                    e = (String) value;
                    break;
                case MESSAGE:
                    m = (DynamicMessage) value;
                    break;
                default:
                    throw new IllegalStateException(String.valueOf(type));
            }
            this.bool = b;
            this.number = l;
            this.floatNumber = f;
            this.string = s;
            this.enumName = e;
            this.message = m;
        }

        public Type getType() {
            return type;
        }

        public static Value createString(String value) {
            return new Value(Type.STRING, value);
        }

        public static Value createBoolean(boolean value) {
            return new Value(Type.BOOLEAN, value);
        }

        public static Value createInteger(long value) {
            return new Value(Type.INTEGER, value);
        }

        public static Value createMessage(DynamicMessage value) {
            return new Value(Type.MESSAGE, value);
        }

        public static Value createFloat(double value) {
            return new Value(Type.FLOAT, value);
        }

        public static Value createEnum(String value) {
            return new Value(Type.ENUM, value);
        }

        @Override
        public String toString() {
            switch (type) {
                case BOOLEAN:
                    return String.valueOf(bool);
                case INTEGER:
                    return String.valueOf(number);
                case FLOAT:
                    return String.valueOf(floatNumber);
                case STRING:
                    return String.valueOf(string);
                case ENUM:
                    return String.valueOf(enumName);
                case MESSAGE:
                    return message.toString();
                default:
                    throw new IllegalStateException(String.valueOf(type));
            }
        }


        public boolean getBoolean() {
            Preconditions.checkState(isBooleanType(), "%s is not a boolean", this);
            return bool;
        }

        public boolean isBooleanType() {
            return type == Type.BOOLEAN;
        }

        public long getInt64() {
            Preconditions.checkState(isIntegerType(), "%s is not a number", this);
            return number;
        }

        public int getInt32() {
            Preconditions.checkState(isIntegerType(), "%s is not a number", this);
            Preconditions.checkState(number <= Integer.MAX_VALUE, "%d does not fit into int32", number);
            Preconditions.checkState(number >= Integer.MIN_VALUE, "%d does not fit into int32", number);
            return (int) number;
        }

        public double getDouble() {
            return floatNumber;
        }

        public boolean isIntegerType() {
            return type == Type.INTEGER;
        }

        public String getString() {
            Preconditions.checkState(isStringType(), "%s is not a string", this);
            return string;
        }

        public boolean isStringType() {
            return type == Type.STRING;
        }

        public String getEnumName() {
            Preconditions.checkState(isEnumType(), "%s is not a enum", this);
            return enumName;
        }

        public boolean isEnumType() {
            return type == Type.ENUM;
        }



        public DynamicMessage getMessage() {
            Preconditions.checkState(isMessageType(), "%s is not a message", this);
            return message;
        }

        public boolean isMessageType() {
            return type == Type.MESSAGE;
        }

        public enum Type {
            BOOLEAN,
            INTEGER,
            FLOAT,
            STRING,
            ENUM,
            MESSAGE
        }
    }
}
