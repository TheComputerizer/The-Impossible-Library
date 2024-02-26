package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import org.joml.Vector3d;

public class Orbit {

    private final double radius;
    private final double speed;
    /**
     * The angle is stored in radians and follows the right hand rule
     */
    private final double angle;

    public Orbit(double radius, double speed, double angle) {
        this.radius = radius;
        this.speed = speed;
        this.angle = angle;
    }

    public Vector3d getNextVec(Vector3d curVec, Vector3d centerVec) {
        double curDistance = curVec.distance(centerVec);
        double curGravity = curDistance<this.radius ? 0d : this.speed/(this.radius/curDistance);
        if(curGravity>this.speed) curGravity = ((curGravity-this.speed)/100d)+this.speed;
        Vector3d dirVec = curVec.cross(centerVec).normalize().mul(this.speed);
        Vector3d gravityVec = centerVec.sub(curVec).normalize().mul(curGravity);
        return curVec.add(dirVec).add(gravityVec);
    }
}
