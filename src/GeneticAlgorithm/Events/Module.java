package GeneticAlgorithm.Events;

public class Module
{
    // Instance variables
    private String moduleCode;
    private String moduleName;

    // Constructor
    public Module(String moduleCode, String moduleName)
    {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    @Override
    public String toString()
    {
        return "{" + this.moduleCode + " - " + this.moduleName + "}";
    }
}
