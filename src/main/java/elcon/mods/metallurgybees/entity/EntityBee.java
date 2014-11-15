package elcon.mods.metallurgybees.entity;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBee extends EntityThrowable {

    public EntityBee(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    @Override
    protected void onImpact(MovingObjectPosition paramMovingObjectPosition) {
        if (paramMovingObjectPosition.entityHit != null) {
            paramMovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 3);
        }
        for (int i = 0; i < 8; i++)
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        if (!this.worldObj.isRemote)
            setDead();
    }

}
