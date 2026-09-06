package org.klyx.exo.paper.util;

import org.bukkit.NamespacedKey;
import org.klyx.exo.util.Key;

public class PaperKey extends Key {

    protected PaperKey(String namespace, String value) {
        super(namespace, value);
    }

    public static PaperKey from(NamespacedKey namespacedKey) {
        return new PaperKey(namespacedKey.getNamespace(), namespacedKey.getKey());
    }

    public static PaperKey from(net.kyori.adventure.key.Key key) {
        return new PaperKey(key.namespace(), key.value());
    }

    public static PaperKey from(Key key) {
        return new PaperKey(key.namespace(), key.value());
    }

    public NamespacedKey namespacedKey() {
        return new NamespacedKey(namespace(), value());
    }

}
