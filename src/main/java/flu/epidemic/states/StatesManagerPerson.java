package flu.epidemic.states;

import flu.epidemic.livingbeings.LivingBeings;
import flu.epidemic.simulator.Field;
import flu.epidemic.simulator.Location;
import flu.epidemic.simulator.Randomizer;
import flu.epidemic.virus.Virus;

import java.util.Random;

/**
 * @author Gabriela Cavalcante
 * @created 03/01/16.
 */
public class StatesManagerPerson extends StatesManager {

    public StatesManagerPerson(StateType stateType, Field field, Location location) {
        super(stateType, field, location);
    }

    @Override
    StateType analyseStateContagious(Virus virus, int timeContagious) {
        return null;
    }

    @Override
    public StateType getState(boolean healthyPerson, Virus virus, int timeInfection, int timeContagious, int timeRecover) {
        if (currentState.isEquals(StateType.HEALTHY)) {
            currentState = analyseStateHealthy();
        } else if (currentState.isEquals(StateType.SICK)) {
            currentState = analyseStateSick(virus, timeInfection);
        } else if (currentState.isEquals(StateType.CONTAGIOUS)) {
            currentState = analyseStateContagious(healthyPerson, virus, timeContagious);
        } else if (currentState.isEquals(StateType.RECOVERING)) {
            currentState = analyseStateRecover(virus, timeRecover);
        }
        return currentState;
    }

    @Override
    public StateType analyseStateSick(Virus virus, int timeInfection) {
        if (timeInfection >= virus.getIncubationTime())
            return StateType.CONTAGIOUS;
        return StateType.SICK;
    }

    private StateType analyseStateRecover(Virus virus, int timeRecover) {
        if (timeRecover == virus.getRecoverTime())
            return StateType.HEALTHY;
        return StateType.RECOVERING;
    }

    @Override
    StateType analyseStateContagious(boolean healthyPerson, Virus virus, int timeContagious) {
        Random rand = Randomizer.getRandom();
        if (timeContagious == virus.getContagiousTime()) {
            if (rand.nextDouble() <= virus.getMortalityRate() && !healthyPerson)
                return StateType.DEAD;
            else
                return StateType.RECOVERING;
        }
        return StateType.CONTAGIOUS;
    }

    @Override
    public StateType getState(Virus virus, int timeInfection, int timeContagious) {
        return null;
    }

    @Override
    StateType analyseStateHealthy() {
        for (Location loc : field.adjacentLocations(location)) {
            LivingBeings beings = (LivingBeings) field.getObjectAt(loc);
            Random rand = Randomizer.getRandom();
            if (beings != null) {
                if (beings.getState().isEquals(StateType.CONTAGIOUS)) {
                    if(rand.nextDouble() <= beings.getVirus().getInfectionRate()){
                        currentVirus = beings.getVirus();
                        return StateType.SICK;
                    }
                } else if (beings.isResistant()) {
                    return StateType.RECOVERING;
                }
            }
        }
        return StateType.HEALTHY;
    }

}
