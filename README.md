## Exo

Exo is a Paper-exclusive API for spawning and working with client-sided entities using raw packets and NMS;
Exo was heavily based on 3add's [PacketEntities](https://github.com/3add/PacketEntities), rather than depending on PacketEvents to function, you will need to use Paperweight Userdev to have the best experience using this library.
Proper Bukkit compatability will be added in the future to ensure that you do not need to use Paperweight Userdev.

### Requirements
- Java 25+
- Paperweight Userdev
- Paper 1.21.11-26.2
# Installation

## Setup

In your plugin's onEnable() method, you will need to call Exo#init(JavaPlugin) to initialize the library, and you can optionally call the destroy method in your onDisable() method. 

```java
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.Exo;

public class MyPlugin extends JavaPlugin {

    public void onEnable() {
        Exo.init(this);
    }
    
    public void onDisable() {
        Exo.destroy();
    }
}
```

## Gradle (Kotlin DSL)

```kotlin
repositories {
    maven("https://repo.klyx.org/releases")
}

dependencies {
    compileOnly("org.klyx.exo:exo:2.0.1")
}
```

## Maven
```xml
<repository>
    <id>klyx-exo</id>
    <url>https://repo.klyx.org/releases</url>
</repository>

<dependency>
    <groupId>org.klyx.exo</groupId>
    <artifactId>exo</artifactId>
    <version>VERSION</version>
</dependency>
```


## Usage

Every packet entity is defined by extending `ExoEntity` and implementing `define()`, which describes the entity's data.
Examples can be found in the `plugin` package.

### A simple mannequin 

```java
public class TestMannequin extends ExoEntity {
 
    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(EntityType.MANNEQUIN)
                .components(
                        new LookAtComponent(),
                        new AttackComponent(event ->
                                event.attacker().sendMessage(Component.text("How could you?")))
                )
                .meta(MannequinMeta.class, meta -> {
                    meta.setImmovable(true);
                });
    }
}
```

### A glowing baby zombie with a passenger

```java
public class TestZombie extends ExoEntity {
 
    @Override
    public EntityData.@NonNull Builder define() {
        return EntityData.builder()
                .entityType(EntityType.ZOMBIE)
                .components(new TickComponent(), new PassengerComponent())
                .meta(ZombieMeta.class, meta -> {
                    meta.setGlowing(true);
                    meta.setBaby(true);
                });
    }
}
```

