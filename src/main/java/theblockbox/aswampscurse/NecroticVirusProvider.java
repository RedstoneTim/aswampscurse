package theblockbox.aswampscurse;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NecroticVirusProvider implements ICapabilitySerializable<CompoundNBT> {
    public final INecroticVirus instance;

    public NecroticVirusProvider() {
        this.instance = Main.CAPABILITY_NECROTIC_VIRUS.getDefaultInstance();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return (cap == Main.CAPABILITY_NECROTIC_VIRUS) ? LazyOptional.of(() -> (T) this.instance) : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) Main.CAPABILITY_NECROTIC_VIRUS.getStorage().writeNBT(Main.CAPABILITY_NECROTIC_VIRUS, this.instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        Main.CAPABILITY_NECROTIC_VIRUS.getStorage().readNBT(Main.CAPABILITY_NECROTIC_VIRUS, this.instance, null, nbt);
    }
}
