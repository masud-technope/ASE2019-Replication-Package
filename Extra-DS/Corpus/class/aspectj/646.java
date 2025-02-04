package spacewar;

/**
 * SpaceObjects are objects that float around in space.  They support the
 * minimal SpaceObject protocol, having to do with position, velocity,
 * size and liveness.  They are constructed with game, position, velocity
 * and size.  When constructed, a spaceobject adds itself to the registry.
 *
 * When it dies, a spaceobject removes itself from the registry.  But note
 * that it doesn't decide when to die, subclasses do that.
 *
 * The display aspects actually draw the space object on the screen and say
 * how much space it takes up there.
 */
abstract class SpaceObject {

    private Game game;

    private double xPos, yPos, oldXPos, oldYPos, xVel, yVel;

    private boolean alive;

     SpaceObject(Game theGame, double xP, double yP, double xV, double yV) {
        game = theGame;
        xPos = xP;
        yPos = yP;
        oldXPos = xP;
        oldYPos = yP;
        xVel = xV;
        yVel = yV;
        alive = true;
        getGame().getRegistry().register(this);
    }

    Game getGame() {
        return game;
    }

    double getXPos() {
        return xPos;
    }

    double getYPos() {
        return yPos;
    }

    double getOldXPos() {
        return oldXPos;
    }

    double getOldYPos() {
        return oldYPos;
    }

    double getXVel() {
        return xVel;
    }

    double getYVel() {
        return yVel;
    }

    void setXVel(double n) {
        xVel = n;
    }

    void setYVel(double n) {
        yVel = n;
    }

    boolean isAlive() {
        return alive;
    }

    void setIsAlive(boolean n) {
        alive = n;
    }

    /**
     * Move 1 unit of time's worth of distance.  I.e. increment xPos by xVel
     * and yPos by yVel.  If we move off an edge of the screen move us back
     * in the opposite edge.
     */
    void clockTick() {
        oldXPos = xPos;
        oldYPos = yPos;
        xPos = (xPos + xVel) % getGame().getWidth();
        if (xPos < 0)
            xPos += getGame().getWidth();
        yPos = (yPos + yVel) % getGame().getHeight();
        if (yPos < 0)
            yPos += getGame().getHeight();
    }

    void accelerate(double dXVel, double dYVel) {
        xVel += dXVel;
        yVel += dYVel;
    }

    void die() {
        getGame().getRegistry().unregister(this);
    }

    abstract int getSize();

    abstract void handleCollision(SpaceObject obj);
}
