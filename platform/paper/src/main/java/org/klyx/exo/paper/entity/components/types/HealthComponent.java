package org.klyx.exo.paper.entity.components.types;

import org.bukkit.Bukkit;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityAttackEvent;
import org.klyx.exo.entity.events.EntityDamageEvent;
import org.klyx.exo.entity.events.EntityDeathEvent;
import org.klyx.exo.entity.meta.types.entity.living.LivingEntityMeta;
import org.klyx.exo.paper.ExoPaper;
import org.klyx.exo.player.ExoPlayer;

public class HealthComponent implements EntityComponent {

    private static final long DEFAULT_INVULNERABLE_WINDOW_MILLIS = 350L;
    private static final long DEFAULT_DEATH_REMOVAL_DELAY_TICKS = 20L;

    private @Nullable ExoEntity entity;

    private float maxHealth;
    private float health;
    private float lastHurt;
    private long lastHurtTime;
    private boolean dead;

    // 0 disables despawn, in ticks!
    private final long deathRemovalDelay;
    private final long invulnerableWindowMillis;

    public HealthComponent() {
        this(20.0F);
    }

    public HealthComponent(float maxHealth) {
        this(maxHealth, DEFAULT_DEATH_REMOVAL_DELAY_TICKS, DEFAULT_INVULNERABLE_WINDOW_MILLIS);
    }

    public HealthComponent(float maxHealth, long deathRemovalDelayTicks, long invulnerableWindowMillis) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.deathRemovalDelay = deathRemovalDelayTicks;
        this.invulnerableWindowMillis = invulnerableWindowMillis;
    }

    @Override
    public void initialize(ExoEntity entity) {
        this.entity = entity;

        entity.eventBus().on(EntityAttackEvent.class, event -> damage(event.damage(), event.attacker()));
        syncHealth();
    }

    public boolean damage(float amount, @Nullable ExoPlayer attacker) {
        if (entity == null || dead || amount <= 0.0F) return false;

        long now = System.currentTimeMillis();
        boolean withinCooldown = now - lastHurtTime < invulnerableWindowMillis;

        if (withinCooldown && amount <= lastHurt) return false;
        float appliedAmount = withinCooldown ? amount - lastHurt : amount;

        EntityDamageEvent damageEvent = new EntityDamageEvent(attacker, appliedAmount);
        entity.eventBus().post(damageEvent);
        if (damageEvent.isCancelled()) return false;

        lastHurt = amount;
        if (!withinCooldown) {
            lastHurtTime = now;
        }

        setHealth(health - appliedAmount);

        if (health <= 0.0F && !dead) {
            dead = true;
            EntityDeathEvent deathEvent = new EntityDeathEvent(attacker);
            entity.eventBus().post(deathEvent);

            if (deathEvent.isCancelled()) {
                dead = false;
                setHealth(1.0F);
            } else if (deathRemovalDelay > 0) {
                Bukkit.getScheduler().runTaskLater(ExoPaper.plugin(), () -> {
                    if (entity != null) entity.despawn();
                }, deathRemovalDelay);
            }
        }

        return true;
    }

    public float getHealth() {
        return health;
    }

    public HealthComponent setHealth(float health) {
        this.health = Math.clamp(health, 0.0F, maxHealth);
        syncHealth();
        return this;
    }

    public HealthComponent heal(float amount) {
        return setHealth(health + amount);
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public HealthComponent setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
        if (health > maxHealth) setHealth(maxHealth);
        return this;
    }

    public boolean isDead() {
        return dead;
    }

    private void syncHealth() {
        if (entity == null) return;
        entity.editMeta(LivingEntityMeta.class, meta -> meta.setHealth(health));
    }
}
