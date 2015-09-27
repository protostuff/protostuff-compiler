package io.protostuff.generator;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class GeneratorInfo {

    private final String name;
    private final String date;
    private final String version;

    private GeneratorInfo(Builder builder) {
        name = builder.name;
        date = builder.date;
        version = builder.version;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getVersion() {
        return version;
    }

    public static final class Builder {
        private String name;
        private String date;
        private String version;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public GeneratorInfo build() {
            return new GeneratorInfo(this);
        }
    }
}
