package theblockbox.aswampscurse.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theblockbox.aswampscurse.Main;

public class NecroticGhoulEntity extends ZombieEntity {
    public NecroticGhoulEntity(EntityType<NecroticGhoulEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5F);
        this.getAttribute(ZombieEntity.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        boolean toReturn = super.attackEntityAsMob(entity);
        if (toReturn && (entity instanceof LivingEntity) && (this.rand.nextInt(4) != 0)) {
            // infect entity
            entity.getCapability(Main.CAPABILITY_NECROTIC_VIRUS).ifPresent(necroticVirus -> necroticVirus.infectIfPossible(entity));
        }
        return toReturn;
    }

    @Override
    public void onKillEntity(LivingEntity p_70074_1_) {
    }

    @Override
    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }
}
