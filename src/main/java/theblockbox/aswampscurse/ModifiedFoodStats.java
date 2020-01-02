package theblockbox.aswampscurse;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.FoodStats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModifiedFoodStats extends FoodStats {
    private final FoodStats foodStats;

    public ModifiedFoodStats(FoodStats foodStats) {
        this.foodStats = foodStats;
    }

    @Override
    public void addStats(int p_75122_1_, float p_75122_2_) {
        this.foodStats.addStats(p_75122_1_, p_75122_2_);
    }

    @Override
    public void consume(Item item, ItemStack stack) {
        Food food = item.getFood();
        if ((food != null) && food.isMeat()) {
            this.foodStats.consume(item, stack);
        }
    }

    @Override
    public void tick(PlayerEntity p_75118_1_) {
        this.foodStats.tick(p_75118_1_);
    }

    @Override
    public void read(CompoundNBT p_75112_1_) {
        this.foodStats.read(p_75112_1_);
    }

    @Override
    public void write(CompoundNBT p_75117_1_) {
        this.foodStats.write(p_75117_1_);
    }

    @Override
    public int getFoodLevel() {
        return this.foodStats.getFoodLevel();
    }

    @Override
    public boolean needFood() {
        return this.foodStats.needFood();
    }

    @Override
    public void addExhaustion(float p_75113_1_) {
        this.foodStats.addExhaustion(p_75113_1_);
    }

    @Override
    public float getSaturationLevel() {
        return this.foodStats.getSaturationLevel();
    }

    @Override
    public void setFoodLevel(int p_75114_1_) {
        this.foodStats.setFoodLevel(p_75114_1_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setFoodSaturationLevel(float p_75119_1_) {
        this.foodStats.setFoodSaturationLevel(p_75119_1_);
    }
}
