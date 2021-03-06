package juuxel.adorn.entity

import juuxel.adorn.Adorn
import juuxel.adorn.client.renderer.InvisibleEntityRenderer
import juuxel.adorn.lib.RegistryHelper
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.registry.Registry

object AdornEntities : RegistryHelper(Adorn.NAMESPACE) {
    val SITTING_VEHICLE = register(
        Registry.ENTITY_TYPE,
        "sitting_vehicle",
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::SittingVehicleEntity)
            .dimensions(EntityDimensions.fixed(0f, 0f))
            .build()
    )

    fun init() {}

    @Environment(EnvType.CLIENT)
    fun initClient() {
        EntityRendererRegistry.INSTANCE.register(SITTING_VEHICLE) { manager, _ ->
            InvisibleEntityRenderer(
                manager
            )
        }
    }
}
