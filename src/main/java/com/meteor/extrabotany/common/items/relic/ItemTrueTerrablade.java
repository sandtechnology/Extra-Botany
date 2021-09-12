package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.common.core.Helper;
import com.meteor.extrabotany.common.entities.projectile.EntityTrueTerrabladeProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemTier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemTrueTerrablade extends ItemSwordRelic{

    public static final int MANA_PER_DAMAGE = 400;

    public ItemTrueTerrablade(Properties prop) {
        super(ItemTier.DIAMOND, 5, -2F, prop);
    }

    public void attackEntity(LivingEntity player, Entity target){
        Vector3d targetpos = target == null ? Helper.PosToVec(raytraceFromEntity(player, 80F, true).getPos()).add(0, 1, 0) : target.getPositionVec().add(0, 1, 0);

        EntityTrueTerrabladeProjectile proj = new EntityTrueTerrabladeProjectile(player.world, player);
        proj.setPosition(player.getPosX(), player.getPosY()+0.5D, player.getPosZ());
        proj.setTargetPos(targetpos);
        proj.faceTargetAccurately(0.8F);
        player.world.addEntity(proj);
    }

    @Override
    public void onLeftClick(PlayerEntity player, Entity target) {
        if (!player.world.isRemote && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this
                && player.getCooledAttackStrength(0) == 1
                && ManaItemHandler.instance().requestManaExactForTool(player.getHeldItemMainhand(), player, MANA_PER_DAMAGE, true)) {
            attackEntity(player, target);
        }
    }
}

