package juuxel.adorn.lib

import io.netty.buffer.Unpooled
import juuxel.adorn.Adorn
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.minecraft.client.network.packet.CustomPayloadS2CPacket
import net.minecraft.client.network.packet.EntitySpawnS2CPacket
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.util.PacketByteBuf

object ModPackets {
    val ENTITY_SPAWN = Adorn.id("entity_spawn")

    fun init() {
        ClientSidePacketRegistry.INSTANCE.register(ENTITY_SPAWN) { context, buf ->
            val packet = EntitySpawnS2CPacket()
            packet.read(buf)
            val world = context.player?.world ?: return@register
            val entity = packet.entityTypeId.create(world)!!
            entity.entityId = packet.id
            entity.uuid = packet.uuid
            entity.method_18003(packet.x, packet.y, packet.z)
            entity.pitch = packet.pitch * 360 / 256f
            entity.yaw = packet.yaw * 360 / 256f

            (context.player.world as? ClientWorld)?.addEntity(packet.id, entity)
        }
    }

    fun createEntitySpawnPacket(entity: Entity) =
        CustomPayloadS2CPacket(ENTITY_SPAWN, PacketByteBuf(Unpooled.buffer()).apply {
            EntitySpawnS2CPacket(entity).write(this)
        })
}