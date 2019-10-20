package theblockbox.aswampscurse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theblockbox.aswampscurse.entity.NecromanticWitchEntity;
import theblockbox.aswampscurse.entity.NecromanticWitchRenderer;
import theblockbox.aswampscurse.entity.NecroticGhoulEntity;
import theblockbox.aswampscurse.entity.NecroticGhoulRenderer;

@Mod("aswampscurse")
public class Main {
    public static final EntityType<NecromanticWitchEntity> NECROMANTIC_WITCH_TYPE = EntityType.Builder.create(NecromanticWitchEntity::new,
            EntityClassification.MONSTER).size(0.6F, 1.95F).build("necromantic_witch");
    public static final EntityType<NecroticGhoulEntity> NECROTIC_GHOUL_TYPE = EntityType.Builder.create(NecroticGhoulEntity::new,
            EntityClassification.MONSTER).size(0.6F, 1.95F).build("necrotic_ghoul");
    private static final Logger LOGGER = LogManager.getLogger();

    static {
        Main.NECROMANTIC_WITCH_TYPE.setRegistryName("aswampscurse:necromantic_witch");
        Main.NECROTIC_GHOUL_TYPE.setRegistryName("aswampscurse:necrotic_ghoul");
    }

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // pre init code
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(new SpawnEggItem(Main.NECROMANTIC_WITCH_TYPE, 3407872, 9872190,
                            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("aswampscurse:necromantic_witch_spawn_egg"),
                    new SpawnEggItem(Main.NECROTIC_GHOUL_TYPE, 3407872, 9872190,
                            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("aswampscurse:necrotic_ghoul_spawn_egg"),
                    new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("aswampscurse:swamps_fetish"));
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().registerAll(Main.NECROMANTIC_WITCH_TYPE, Main.NECROTIC_GHOUL_TYPE);
        }

        @SubscribeEvent
        public static void onModelRegister(ModelRegistryEvent event) {
            EntityRendererManager manager = Minecraft.getInstance().getRenderManager();
            manager.register(NecromanticWitchEntity.class, new NecromanticWitchRenderer(manager));
            manager.register(NecroticGhoulEntity.class, new NecroticGhoulRenderer(manager));
        }
    }
}
