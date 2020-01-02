package theblockbox.aswampscurse.entity;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import theblockbox.aswampscurse.Main;

import java.util.Random;

public class NecromanticWitchEntity extends MonsterEntity implements IRangedAttackMob {
    public static final IDataSerializer<Long> LONG_SERIALIZER = new IDataSerializer<Long>() {
        public void write(PacketBuffer buf, Long value) {
            buf.writeLong(value);
        }

        public Long read(PacketBuffer buf) {
            return buf.readLong();
        }

        public Long copyValue(Long value) {
            return value;
        }
    };
    public static final DataParameter<Long> GHOUL_SUMMON = EntityDataManager.createKey(NecromanticWitchEntity.class, NecromanticWitchEntity.LONG_SERIALIZER);

    static {
        DataSerializers.registerSerializer(NecromanticWitchEntity.LONG_SERIALIZER);
    }

    public NecromanticWitchEntity(EntityType<NecromanticWitchEntity> type, World world) {
        super(type, world);
        this.lookController = new LookController(this) {
            @Override
            public void setLookPositionWithEntity(Entity entity, float p_75651_2_, float p_75651_3_) {
                super.setLookPositionWithEntity(entity, p_75651_2_, p_75651_3_);
                if ((entity instanceof LivingEntity) && (p_75651_2_ == p_75651_3_) && (p_75651_2_ == 30.0F) && (NecromanticWitchEntity.this.shouldSummonGhouls((LivingEntity) entity))) {
                    NecromanticWitchEntity.this.dataManager.set(NecromanticWitchEntity.GHOUL_SUMMON, NecromanticWitchEntity.this.world.getGameTime());
                }
            }
        };
    }

    public static boolean canSpawnHere(EntityType<? extends MonsterEntity> p_223325_0_, IWorld world, SpawnReason p_223325_2_, BlockPos pos, Random p_223325_4_) {
        Biome biome = world.getBiome(pos);
        return (biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS) && MonsterEntity.func_223325_c(p_223325_0_, world, p_223325_2_, pos, p_223325_4_);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(NecromanticWitchEntity.GHOUL_SUMMON, -1L);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 60, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity entity, float p_82196_2_) {
        if (this.shouldSummonGhouls(entity)) {
            // summon ghouls
            double radius = this.rand.nextInt(5) + 5;
            double angle = 0;
            double step = Math.PI / 2;
            for (int i = 0; i < 4; i++) {
                NecroticGhoulEntity ghoulEntity = Main.NECROTIC_GHOUL_TYPE.create(this.world);
                ghoulEntity.setPosition((this.posX + radius * Math.cos(angle)), this.posY, (this.posZ + radius * Math.sin(angle)));
                this.world.addEntity(ghoulEntity);
                angle += step;
            }
            this.dataManager.set(NecromanticWitchEntity.GHOUL_SUMMON, this.world.getGameTime());
        } else {
            // (try to) throw damage potion at attacker
            // https://youtu.be/pn4_vcSPZwg
            Vec3d motion = entity.getMotion();
            double x = entity.posX + motion.x - this.posX;
            double y = entity.posY + (double) entity.getEyeHeight() - 1.100000023841858D - this.posY;
            double z = entity.posZ + motion.z - this.posZ;
            float distance = MathHelper.sqrt((x * x) + (z * z));

            PotionEntity potionEntity = new PotionEntity(this.world, this);
            potionEntity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), Potions.HARMING));
            potionEntity.rotationPitch -= -20.0F;
            potionEntity.shoot(x, y + distance * 0.2D, z, 0.75F, 8.0F);
            this.world.playSound((PlayerEntity) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + (this.rand.nextFloat() * 0.4F));
            this.world.addEntity(potionEntity);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float p_70097_2_) {
        if (source.isFireDamage())
            return super.attackEntityFrom(source, p_70097_2_);
        Entity attacker = source.getTrueSource();
        if (attacker instanceof LivingEntity) {
            ListNBT list = ((LivingEntity) attacker).getHeldItemMainhand().getStack().getEnchantmentTagList();
            for (int i = 0; i < list.size(); ++i) {
                CompoundNBT nbt = list.getCompound(i);
                ResourceLocation enchantment = ResourceLocation.tryCreate(nbt.getString("id"));
                if ((enchantment != null) && (enchantment.equals(Enchantments.FIRE_ASPECT.getRegistryName())
                        || enchantment.equals(Enchantments.SMITE.getRegistryName())
                        || enchantment.equals(Enchantments.FLAME.getRegistryName()))) {
                    return super.attackEntityFrom(source, p_70097_2_);
                }
            }
        }
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITCH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ENTITY_WITCH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITCH_DEATH;
    }

    public boolean shouldSummonGhouls(LivingEntity entity) {
        return (this.getGhoulSummoningState() != GhoulSummoningState.COOLDOWN)
                && entity.world.getEntitiesWithinAABB(NecroticGhoulEntity.class, this.getBoundingBox().grow(60)).isEmpty();
    }

    public GhoulSummoningState getGhoulSummoningState() {
        long ticks = this.getDataManager().get(NecromanticWitchEntity.GHOUL_SUMMON);
        if (ticks != -1L) {
            long time = this.world.getGameTime() - ticks;
            if (time < 4000L) {
                return (time < 20L) ? GhoulSummoningState.SUMMONING : GhoulSummoningState.COOLDOWN;
            }
        }
        return GhoulSummoningState.CAN_SUMMON;
    }

    public enum GhoulSummoningState {
        CAN_SUMMON, COOLDOWN, SUMMONING
    }
}
