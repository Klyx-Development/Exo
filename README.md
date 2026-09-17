## Exo

Exo is a platform-agnostic API for spawning and working with client-sided entities using raw packets.
Exo is heavily based on 3add's [PacketEntities](https://github.com/3add/PacketEntities)

The library is split into a `common` module, which contains the entity/component API, and platform modules that implement that logic for a specific server software:

- **Paper** (`platform-paper`) - Note that this requires you to use Paperweight Userdev for the best experience. Proper Bukkit compatibility (without Paperweight Userdev) will be added in the future.
- **Minestom** (`platform-minestom`)

### Requirements
- Java 25+
- One of the supported platforms:
  - Paper 1.21.11-26.2 (with Paperweight Userdev)
  - Minestom

## Installation

## Setup

### Paper

In your plugin's `onEnable()` method, call `ExoPaper#init(JavaPlugin)` to initialize the library, and call `ExoPaper#destroy()` in `onDisable()`.

```java
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.paper.ExoPaper;

public class MyPlugin extends JavaPlugin {

    public void onEnable() {
        ExoPaper.init(this);
    }

    public void onDisable() {
        ExoPaper.destroy();
    }
}
```

### Minestom

Call `ExoMinestom#init()` after `MinecraftServer.init()`, and call `ExoMinestom#destroy()` when your server shuts down.

```java
import net.minestom.server.MinecraftServer;
import org.klyx.exo.minestom.ExoMinestom;

MinecraftServer server = MinecraftServer.init();
ExoMinestom.init();
```

## Gradle (Kotlin DSL)

```kotlin
repositories {
    maven("https://repo.klyx.org/releases")
}

dependencies {
    // Common (required)
    implementation("org.klyx.exo:common:3.1.0")
    
    // Paper
    implementation("org.klyx.exo:paper:3.1.0")

    // Minestom
    implementation("org.klyx.exo:minestom:3.1.0")
}
```

## Maven
```xml
<repository>
    <id>klyx-exo</id>
    <url>https://repo.klyx.org/releases</url>
</repository>

<!-- Common (Required) -->
<dependency>
  <groupId>org.klyx.exo</groupId>
  <artifactId>common</artifactId>
  <version>3.1.0</version>
</dependency>

<!-- Paper -->
<dependency>
    <groupId>org.klyx.exo</groupId>
    <artifactId>paper</artifactId>
    <version>3.1.0</version>
</dependency>

<!-- Minestom -->
<dependency>
    <groupId>org.klyx.exo</groupId>
    <artifactId>minestom</artifactId>
    <version>3.1.0</version>
</dependency>
```

## Usage

Every packet entity is defined by extending `ExoEntity` and implementing `define()`, which describes the entity's data.

Examples for each platform can be found in the `demo` module (`demo/paper`, `demo/minestom`).

### A simple mannequin (Paper)

```java
public class TestMannequin extends ExoEntity {

    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(PaperEntityTypes.toExo(EntityType.MANNEQUIN))
                .components(
                        new LookAtComponent(),
                        new AttackComponent(event ->
                                ((ExoPaperPlayer) event.attacker()).bukkit().sendMessage(Component.text("How could you?")))
                )
                .meta(MannequinMeta.class, meta -> {
                    meta.setImmovable(true);
                });
    }
}
```

### A glowing baby zombie with a passenger (Paper)

```java
public class TestZombie extends ExoEntity {

    @Override
    public EntityData.@NonNull Builder define() {
        return EntityData.builder()
                .entityType(PaperEntityTypes.toExo(EntityType.ZOMBIE))
                .components(new TickComponent(), new PassengerComponent())
                .meta(ZombieMeta.class, meta -> {
                    meta.setGlowing(true);
                    meta.setBaby(true);
                });
    }
}
```

Spawning and interacting with an entity works the same way on every platform, however, you will need to use your platform's respective conversion utilities to actually spawn and interact with these entities.
Example from the Paper demo:

```java
ExoWorld world = PaperLocUtil.toExoWorld(player.getLocation().getWorld());
ExoPos pos = PaperLocUtil.toExoPos(player.getLocation());

TestMannequin mannequin = new TestMannequin();
mannequin.spawn(world, pos);
mannequin.addViewer(ExoPaperPlayer.of(player));
```
