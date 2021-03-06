package flu.epidemic.virus;

/**
 * util to represents the STATUS of a person.
 *
 * @author Raquel Lopes de Oliveira
 * @version 1.0
 * @created 10/12/2015
 */
public enum TypeVirus {
    H1N1("H1N1"),
    H5N1("H5N1");

    private String name= "";

    /**
     * Constructor enum
     * @param name
     */
    TypeVirus(String name) {
        this.name = name;
    }

    /**
     * Method to change name to String
     * @return status
     */
    @Override
    public String toString() {
        return name;
    }
}