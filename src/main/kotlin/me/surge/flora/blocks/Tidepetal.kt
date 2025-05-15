package me.surge.flora.blocks

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import me.surge.flora.misc.Cutout
import net.minecraft.block.*
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.component.type.SuspiciousStewEffectsComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.registry.tag.BlockTags
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView
import net.minecraft.world.tick.ScheduledTickView

class Tidepetal(settings: Settings) : BlockWithEntity(settings), SuspiciousStewIngredient, Waterloggable, Cutout {

    companion object {
        val STEW_EFFECT_CODEC_TIDEPETAL = SuspiciousStewEffectsComponent.CODEC.fieldOf("suspicious_stew_effects")

        val CODEC: MapCodec<Tidepetal> =
            RecordCodecBuilder.mapCodec {
                it.group<SuspiciousStewEffectsComponent, Settings>(
                    STEW_EFFECT_CODEC_TIDEPETAL.forGetter { obj -> obj.stewEffects }, createSettingsCodec()
                ).apply(it) { stewEffects: SuspiciousStewEffectsComponent, settings: Settings ->
                    Tidepetal(settings)
                }
            }

        val WATERLOGGED: BooleanProperty = Properties.WATERLOGGED

        val SHAPE = createColumnShape(6.0, 0.0, 10.0);
    }

    init {
        defaultState = defaultState
            .with(WATERLOGGED, true)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(WATERLOGGED)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return this.defaultState
            .with(WATERLOGGED, ctx.world.getFluidState(ctx.blockPos).isOf(Fluids.WATER))
    }

    override fun getFluidState(state: BlockState): FluidState {
        return if (state[WATERLOGGED]) Fluids.WATER.getStill(false) else super.getFluidState(state)
    }

    override fun getStateForNeighborUpdate(state: BlockState, world: WorldView, tickView: ScheduledTickView, pos: BlockPos, direction: Direction, neighborPos: BlockPos, neighborState: BlockState, random: Random): BlockState {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        }

        return if (!state.canPlaceAt(world, pos)) Blocks.AIR.defaultState else super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random)
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        return SHAPE.offset(state.getModelOffset(pos))
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val floor = pos.down()
        val floorState = world.getBlockState(floor)
        return floorState.isIn(BlockTags.DIRT) || floorState.isOf(Blocks.SAND) || floorState.isOf(Blocks.FARMLAND)
    }

    override fun canPathfindThrough(state: BlockState?, type: NavigationType?): Boolean {
        return if (type == NavigationType.AIR && !this.collidable) true else super.canPathfindThrough(state, type)
    }

    override fun isTransparent(state: BlockState): Boolean {
        return state.fluidState.isEmpty
    }

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return validateTicker<TidepetalEntity, T>(type, FloraBlockEntities.TIDEPETAL, if (!world.isClient)
            BlockEntityTicker { world: World, pos: BlockPos, state: BlockState, blockEntity: TidepetalEntity ->
                TidepetalEntity.serverTick(world, pos)
            } else null)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = TidepetalEntity(pos, state)

    override fun getStewEffects(): SuspiciousStewEffectsComponent = SuspiciousStewEffectsComponent(
            listOf(SuspiciousStewEffectsComponent.StewEffect(StatusEffects.WATER_BREATHING, MathHelper.floor(9.0f * 20.0f)))
        )

    override fun getCodec(): MapCodec<out BlockWithEntity> = CODEC

    class TidepetalEntity(pos: BlockPos, state: BlockState) : BlockEntity(FloraBlockEntities.TIDEPETAL, pos, state) {

        companion object {

            @JvmStatic
            fun serverTick(world: World, pos: BlockPos) {
                val box = Box(pos).expand(5.0)

                for (entity in world.getOtherEntities(null, box)) {
                    if (entity !is LivingEntity || entity.statusEffects.any { it.effectType == StatusEffects.WATER_BREATHING && !it.isDurationBelow(200)}) {
                        continue
                    }

                    entity.addStatusEffect(StatusEffectInstance(StatusEffects.WATER_BREATHING, 200, 0, true, false))
                }

            }

        }

    }

}