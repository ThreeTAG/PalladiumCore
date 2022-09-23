package net.threetag.palladiumcore.permission;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class PermissionType<T> {

    private static final Map<String, PermissionType<?>> TYPES_BY_NAME = new HashMap<>();
    public static final PermissionType<Boolean> BOOLEAN = new PermissionType<>(Boolean.class, "boolean", Boolean::getBoolean, Object::toString);
    public static final PermissionType<Integer> INTEGER = new PermissionType<>(Integer.class, "integer", Integer::parseInt, Object::toString);
    public static final PermissionType<String> STRING = new PermissionType<>(String.class, "string", s -> s, s -> s);
    public static final PermissionType<Component> COMPONENT = new PermissionType<>(Component.class, "component", Component.Serializer::fromJson, Component.Serializer::toJson);

    @Nullable
    public static PermissionType<?> getByName(String name) {
        return TYPES_BY_NAME.get(name);
    }

    private final Class<T> typeClass;
    private final String name;
    private final Function<String, T> parser;
    private final Function<T, String> serializer;

    private PermissionType(Class<T> typeClass, String name, Function<String, T> parser, Function<T, String> serializer) {
        this.typeClass = typeClass;
        this.name = name;
        this.parser = parser;
        this.serializer = serializer;
        TYPES_BY_NAME.put(name, this);
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }

    public String getName() {
        return name;
    }

    public Function<String, T> getParser() {
        return parser;
    }

    public Function<T, String> getSerializer() {
        return serializer;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof PermissionType otherType)) return false;
        return Objects.equals(this.typeClass, otherType.typeClass) &&
                Objects.equals(this.name, otherType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeClass, name);
    }

    @Override
    public String toString() {
        return "PermissionType{" +
                "typeClass=" + typeClass +
                ", name='" + name + '\'' +
                '}';
    }
}
