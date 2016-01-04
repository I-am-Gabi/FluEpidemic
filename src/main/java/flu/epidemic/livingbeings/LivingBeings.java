package flu.epidemic.livingbeings;

import flu.epidemic.simulator.Field;

import flu.epidemic.simulator.Location;
import flu.epidemic.States.State;
import flu.epidemic.virus.Virus;

import java.util.List;

/**
 * @author David J. Barnes and Michael Kölling
 * @author Gabriela Cavalcante
 * @version 03/12/15.
 */
public abstract class LivingBeings {
    protected State state;
    protected Being type;
    

	protected Virus virus;
    protected Location location;
    protected Field field;
    protected boolean alive;

    public LivingBeings(Being type, Field field, Location location) {
        this.type = type;
        this.virus = null;
        alive = true;
        this.field = field;
        setLocation(location);
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     */
    abstract public void act();

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        if (state.equals(State.DEAD))
            return true;
        return false;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Return the animal's state
     * @return the animal's state
     */
    public State getState() {
        return state;
    }

    /**
     * Return the virus
     * @return the virus
     */
    public Virus getVirus() {
        return virus;
    }
    
    /**
     * Return the type
     * @return the type
     */
    public Being getType() {
		return type;
	}
}
