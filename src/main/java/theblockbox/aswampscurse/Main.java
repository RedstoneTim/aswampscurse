package theblockbox.aswampscurse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.IItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theblockbox.aswampscurse.entity.NecromanticWitchEntity;
import theblockbox.aswampscurse.entity.NecromanticWitchRenderer;
import theblockbox.aswampscurse.entity.NecroticGhoulEntity;
import theblockbox.aswampscurse.entity.NecroticGhoulRenderer;

import javax.annotation.Nullable;

@Mod("aswampscurse")
public class Main {
    public static final EntityType<NecromanticWitchEntity> NECROMANTIC_WITCH_TYPE = EntityType.Builder.create(NecromanticWitchEntity::new,
            EntityClassification.MONSTER).size(0.6F, 1.95F).build("necromantic_witch");
    public static final EntityType<NecroticGhoulEntity> NECROTIC_GHOUL_TYPE = EntityType.Builder.create(NecroticGhoulEntity::new,
            EntityClassification.MONSTER).size(0.6F, 1.95F).build("necrotic_ghoul");
    @CapabilityInject(INecroticVirus.class)
    public static final Capability<INecroticVirus> CAPABILITY_NECROTIC_VIRUS = null;
    public static final ResourceLocation NECROTIC_VIRUS = new ResourceLocation("aswampscurse:necrotic_virus");
    public static final ResourceLocation ALGAE_POTION_NAME = new ResourceLocation("aswampscurse:potion_of_algae");
    public static final Effect ALGAE_EFFECT = new Effect(EffectType.BENEFICIAL, 0x41FA42) {
        @Override
        public boolean isInstant() {
            return true;
        }

        @Override
        public void affectEntity(@Nullable Entity p_180793_1_, @Nullable Entity p_180793_2_, LivingEntity entity, int p_180793_4_, double p_180793_5_) {
            entity.getCapability(Main.CAPABILITY_NECROTIC_VIRUS).ifPresent(necroticVirus -> Main.cureInfected(entity, necroticVirus, false, false));
        }
    };
    public static final Potion ALGAE_POTION = new Potion("algae");
    private static final Logger LOGGER = LogManager.getLogger();

    static {
        Main.NECROMANTIC_WITCH_TYPE.setRegistryName("aswampscurse:necromantic_witch");
        Main.NECROTIC_GHOUL_TYPE.setRegistryName("aswampscurse:necrotic_ghoul");
        Main.ALGAE_POTION.setRegistryName(Main.ALGAE_POTION_NAME);
        Main.ALGAE_EFFECT.setRegistryName(Main.ALGAE_POTION_NAME);
    }

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // pre init code
        CapabilityManager.INSTANCE.register(INecroticVirus.class, new INecroticVirus.NecroticVirusStorage(),
                INecroticVirus.NecroticVirus::new);
    }

    public static void cureInfected(LivingEntity entity, INecroticVirus necroticVirus, boolean killEntity, boolean summonGhoul) {
        if ((entity.world.getGameTime() - necroticVirus.getTimeInfected()) > 19200) {
            necroticVirus.setTimeInfected(-1L);
            NecroticGhoulEntity ghoul = Main.NECROTIC_GHOUL_TYPE.create(entity.world);
            ghoul.enablePersistence();
            BlockPos pos = entity.getPosition();
            ghoul.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
            ghoul.onInitialSpawn(entity.world, entity.world.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, null, null);
            entity.world.addEntity(ghoul);
            if (killEntity) {
                entity.attackEntityFrom(DamageSource.MAGIC, Float.MAX_VALUE);
            }
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(new SpawnEggItem(Main.NECROMANTIC_WITCH_TYPE, 3407872, 9872190,
                            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("aswampscurse:necromantic_witch_spawn_egg"),
                    new SpawnEggItem(Main.NECROTIC_GHOUL_TYPE, 3407872, 3872190,
                            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("aswampscurse:necrotic_ghoul_spawn_egg"),
                    new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("aswampscurse:swamps_fetish"));
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().registerAll(Main.NECROMANTIC_WITCH_TYPE, Main.NECROTIC_GHOUL_TYPE);
            EntitySpawnPlacementRegistry.register(Main.NECROMANTIC_WITCH_TYPE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223325_c);
        }

        @SubscribeEvent
        public static void onPotionRegistry(final RegistryEvent.Register<Potion> event) {
            event.getRegistry().registerAll(Main.ALGAE_POTION);
        }

        @SubscribeEvent
        public static void onEffectRegistry(final RegistryEvent.Register<Effect> event) {
            event.getRegistry().registerAll(Main.ALGAE_EFFECT);
        }

        @SubscribeEvent
        public static void onCapabilityAttach(final AttachCapabilitiesEvent<Entity> event) {
            Entity entity = event.getObject();
            if ((entity instanceof PlayerEntity) || (entity instanceof VillagerEntity)) {
                event.addCapability(Main.NECROTIC_VIRUS, new NecroticVirusProvider());
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            LivingEntity entity = event.getEntityLiving();
            entity.getCapability(Main.CAPABILITY_NECROTIC_VIRUS).ifPresent(necroticVirus -> Main.cureInfected(entity, necroticVirus, false, true));
        }

        @SubscribeEvent
        public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if ((entity.ticksExisted % 200 == 0) && !entity.world.isRemote()) {
                entity.getCapability(Main.CAPABILITY_NECROTIC_VIRUS).ifPresent(necroticVirus -> {
                    Main.cureInfected(entity, necroticVirus, true, true);
                    if (necroticVirus.isInfected(entity.world)) {
                        entity.addPotionEffect(new EffectInstance(Effects.HUNGER, 300, 1));
                    }
                });
            }
        }

        @SubscribeEvent
        public static void onEntityHurt(LivingHurtEvent event) {
            LivingEntity hurtEntity = event.getEntityLiving();
            Entity attackingEntity = event.getSource().getTrueSource();
            if (attackingEntity instanceof PlayerEntity) {
                attackingEntity.getCapability(Main.CAPABILITY_NECROTIC_VIRUS).ifPresent(necroticVirus -> {
                    if (necroticVirus.isInfected(attackingEntity.world)) {
                        if (((PlayerEntity) attackingEntity).getRNG().nextInt(4) != 0) {
                            // if infected, infect with 75% chance
                            hurtEntity.getCapability(Main.CAPABILITY_NECROTIC_VIRUS).ifPresent(virus -> virus.infectIfPossible(hurtEntity));
                        }
                        if (hurtEntity instanceof PlayerEntity) {
                            ((PlayerEntity) attackingEntity).getFoodStats().addStats(2, 1);
                        }
                    }
                });
            }
            if ((event.getSource() == DamageSource.STARVE) && (hurtEntity.getHealth() <= 1.0F)
                    && (hurtEntity.getCapability(Main.CAPABILITY_NECROTIC_VIRUS).filter(virus -> virus.isInfected(hurtEntity.world)).isPresent())) {
                event.setCanceled(true);
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void onModelRegister(final ModelRegistryEvent event) {
            EntityRendererManager manager = Minecraft.getInstance().getRenderManager();
            manager.register(NecromanticWitchEntity.class, new NecromanticWitchRenderer(manager));
            manager.register(NecroticGhoulEntity.class, new NecroticGhoulRenderer(manager));
        }
    }
}
