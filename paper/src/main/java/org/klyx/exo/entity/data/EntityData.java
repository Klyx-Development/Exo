package org.klyx.exo.entity.data;

import org.bukkit.entity.EntityType;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.data.object.AbstractObjectData;
import org.klyx.exo.entity.meta.impl.AbstractEntityMeta;
import org.klyx.exo.util.Buildable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EntityData {

    private final EntityType type;
    private final List<EntityComponent> components;
    private final @Nullable AbstractEntityMeta meta;
    private final @Nullable AbstractObjectData objectData;

    private EntityData(Builder builder) {
        this.type = builder.type;
        this.components = List.copyOf(builder.components);
        this.meta = builder.resolveMeta();
        this.objectData = validateObjectData(builder.objectData, type);
    }

    public static EntityData.Builder builder() {
        return new Builder();
    }

    public EntityType getType() {
        return type;
    }

    public List<EntityComponent> getComponents() {
        return components;
    }

    public @Nullable AbstractEntityMeta getMeta() {
        return meta;
    }

    public <M extends AbstractEntityMeta> @Nullable M getMeta(Class<M> expected) {
        if (meta == null) {
            throw new IllegalStateException("No meta defined for this entity");
        }
        if (!expected.isInstance(meta)) {
            throw new IllegalStateException(
                    "Requested meta type " + expected.getSimpleName()
                            + " is not compatible with actual meta type " + meta.getClass().getSimpleName());
        }
        return expected.cast(meta);
    }

    private static @Nullable AbstractObjectData validateObjectData(@Nullable AbstractObjectData data, EntityType type) {
        if (data == null) return null;
        if (data.boundType() != type) {
            throw new IllegalStateException(
                    "Object data bound to " + data.boundType() + " is not compatible with entity type " + type);
        }
        return data;
    }

    public @Nullable AbstractObjectData getObjectData() {
        return objectData;
    }

    public int getObjectDataValue() {
        return objectData != null ? objectData.value() : 0;
    }

    public static final class Builder implements Buildable<EntityData> {

        private EntityType type;
        private List<EntityComponent> components = new ArrayList<>();
        private final List<MetaOp<?>> metaOps = new ArrayList<>();
        private @Nullable AbstractObjectData objectData;

        private record MetaOp<M extends AbstractEntityMeta>(Class<M> metaClass, Consumer<M> consumer) {}

        public Builder entityType(EntityType type) {
            this.type = type;
            return this;
        }

        public Builder component(EntityComponent component) {
            this.components.add(component);
            return this;
        }

        public Builder components(EntityComponent... components) {
            this.components.addAll(Arrays.asList(components));
            return this;
        }

        public Builder components(List<EntityComponent> components) {
            this.components = components;
            return this;
        }

        public <M extends AbstractEntityMeta> Builder meta(Class<M> metaClass, Consumer<M> consumer) {
            metaOps.add(new MetaOp<>(metaClass, consumer));
            return this;
        }

        private @Nullable AbstractEntityMeta resolveMeta() {
            if (metaOps.isEmpty()) return null;

            Class<? extends AbstractEntityMeta> concreteClass = metaOps.getFirst().metaClass();
            for (MetaOp<?> op : metaOps) {
                Class<?> candidate = op.metaClass();
                if (concreteClass.isAssignableFrom(candidate)) {
                    concreteClass = candidate.asSubclass(AbstractEntityMeta.class);
                } else if (!candidate.isAssignableFrom(concreteClass)) {
                    throw new IllegalStateException(
                            "Incompatible meta classes in same hierarchy: "
                                    + concreteClass.getSimpleName() + " and " + candidate.getSimpleName());
                }
            }

            AbstractEntityMeta instance = instantiate(concreteClass);
            for (MetaOp<?> op : metaOps) {
                apply(op, instance);
            }
            return instance;
        }

        private <M extends AbstractEntityMeta> void apply(MetaOp<M> op, AbstractEntityMeta instance) {
            op.consumer().accept((M) instance);
        }

        private <M extends AbstractEntityMeta> M instantiate(Class<M> clazz) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(
                        "Meta class " + clazz.getSimpleName() + " needs a no-arg constructor", e);
            }
        }

        public Builder objectData(AbstractObjectData objectData) {
            this.objectData = objectData;
            return this;
        }

        @Override
        public EntityData build() {
            return new EntityData(this);
        }
    }
}