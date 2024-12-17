package team.unnamed.hephaestus.bukkit

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob

object MobUtils {
    fun createEmptyBaseEntity(
        type: EntityType,
        location: Location,
        hasAI: Boolean,
        canTakeDamage: Boolean
    ): Entity {
        // create the entity
        val entity = location.world.spawnEntity(location, type)
        entity.isPersistent = false
        entity.isInvisible = true
        entity.isSilent = true

        // living entity extras
        if (entity is LivingEntity) {
            if (!canTakeDamage) entity.noDamageTicks = Int.MAX_VALUE
            entity.setAI(hasAI)
            entity.isCollidable = false
        }

        // mob extras
        if (entity is Mob) Bukkit.getMobGoals().removeAllGoals(entity)

        return entity
    }
}