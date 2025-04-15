package me.surge.flora.blocks

import net.minecraft.block.*
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView
import net.minecraft.world.tick.ScheduledTickView

class Tidepetal(settings: Settings) : FlowerBlock(StatusEffects.WATER_BREATHING, 9.0F, settings), Waterloggable {

    companion object {
        val WATERLOGGED: BooleanProperty = Properties.WATERLOGGED
    }

    init {
        defaultState = stateManager.defaultState.with(WATERLOGGED, true)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(WATERLOGGED)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        val fluidState = ctx.world.getFluidState(ctx.blockPos)
        val bl = fluidState.fluid === Fluids.WATER
        return super.getPlacementState(ctx)!!.with<Boolean?, Boolean?>(WATERLOGGED, bl)
    }

    override fun canPlantOnTop(floor: BlockState, world: BlockView?, pos: BlockPos?): Boolean {
        return !floor.getCollisionShape(world, pos).getFace(Direction.UP)
            .isEmpty || floor.isSideSolidFullSquare(world, pos, Direction.UP)
    }

    override fun canPlaceAt(state: BlockState?, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.down()
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos)
    }

    protected override fun getStateForNeighborUpdate(state: BlockState, world: WorldView, tickView: ScheduledTickView, pos: BlockPos, direction: Direction, neighborPos: BlockPos, neighborState: BlockState, random: Random): BlockState {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.defaultState
        } else {
            if (state.get<Boolean?>(SeaPickleBlock.WATERLOGGED)) {
                tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world))
            }

            return super.getStateForNeighborUpdate(
                state,
                world,
                tickView,
                pos,
                direction,
                neighborPos,
                neighborState,
                random
            )
        }
    }

    override fun onBlockAdded(
        state: BlockState,
        world: World,
        pos: BlockPos,
        oldState: BlockState,
        notify: Boolean
    ) {
        super.onBlockAdded(state, world, pos, oldState, notify)
        world.scheduleBlockTick(pos, this, 1)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        super.scheduledTick(state, world, pos, random)

        val box = Box(pos).expand(5.0)

        for (entity in world.getOtherEntities(null, box)) {
            if (!entity.isInFluid || entity !is LivingEntity || entity.statusEffects.any { it.effectType == StatusEffects.WATER_BREATHING && it.isDurationBelow(200)}) {
                continue
            }

            entity.addStatusEffect(StatusEffectInstance(StatusEffects.WATER_BREATHING, 200, 0, true, false))
        }

        world.scheduleBlockTick(pos, this, 1)
    }

}