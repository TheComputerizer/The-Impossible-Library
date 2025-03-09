package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;

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

    public Vector3 getNextVec(Vector3 curVec, Vector3 centerVec) {
        double curDistance = curVec.distance(centerVec);
        double curGravity = curDistance<this.radius ? 0d : this.speed/(this.radius/curDistance);
        if(curGravity>this.speed) curGravity = ((curGravity-this.speed)/100d)+this.speed;
        Vector3 dirVec = curVec.cross(centerVec).normalize().mulScalar(this.speed);
        Vector3 gravityVec = centerVec.sub(curVec).normalize().mulScalar(curGravity);
        return curVec.add(dirVec).add(gravityVec);
    }
}
