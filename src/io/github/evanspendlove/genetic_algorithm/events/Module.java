package io.github.evanspendlove.genetic_algorithm.events;

public class Module
{
    // Instance variables
    private String moduleCode;
    private String moduleName;
    private int hourPerWeek = 0; // Study hours per week

    // Getters
    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getHourPerWeek() {
        return hourPerWeek;
    }

    // Constructor
    public Module(String moduleCode, String moduleName, int hoursPerWeek)
    {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.hourPerWeek = hoursPerWeek;
    }

    @Override
    public String toString()
    {
        return "{" + this.moduleCode + " - " + this.moduleName + " (" + this.hourPerWeek + ")" + "}";
    }
}
