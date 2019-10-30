package theblockbox.aswampscurse;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public interface INecroticVirus {
    public long getTimeInfected();

    public void setTimeInfected(long timeInfected);

    public default void infectIfPossible(Entity entity) {
        if (!this.isInfected(entity.world)) {
            this.setTimeInfected(entity.world.getGameTime());
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 30));
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 65, 2));
            }
        }
    }

    public default boolean isInfected(World world) {
        long timeInfected = this.getTimeInfected();
        return (timeInfected != 1) && ((world.getGameTime() - timeInfected) <= 19200);
    }

    public static class NecroticVirus implements INecroticVirus {
        private long timeInfected = -1;

        @Override
        public long getTimeInfected() {
            return this.timeInfected;
        }

        @Override
        public void setTimeInfected(long timeInfected) {
            this.timeInfected = timeInfected;
        }
    }

    public static class NecroticVirusStorage implements Capability.IStorage<INecroticVirus> {
        public static final String TIME_INFECTED = "time_infected";

        @Nullable
        @Override
        public INBT writeNBT(Capability<INecroticVirus> capability, INecroticVirus instance, Direction side) {
            CompoundNBT compound = new CompoundNBT();
            compound.putLong(INecroticVirus.NecroticVirusStorage.TIME_INFECTED, instance.getTimeInfected());
            return compound;
        }

        @Override
        public void readNBT(Capability<INecroticVirus> capability, INecroticVirus instance, Direction side, INBT nbt) {
            CompoundNBT compound = (CompoundNBT) nbt;
            instance.setTimeInfected(compound.getLong(INecroticVirus.NecroticVirusStorage.TIME_INFECTED));
        }
    }
}
