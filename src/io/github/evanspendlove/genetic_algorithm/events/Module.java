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
    public boolean equals(Object obj)
    {
        // Basic checks
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Module)) return false;
        Module o = (Module) obj;

        if(o.getModuleCode().compareTo(this.getModuleCode()) == 0) // If the Module codes are the same
        {
            if(o.getModuleName().compareTo(this.getModuleName()) == 0) // If the module names are the same
            {
                if(o.getHourPerWeek() == this.getHourPerWeek()) // Comparing the hours per week (int)
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        // Opting for shorter toString:
        //return "{" + this.moduleCode + " - " + this.moduleName + " (" + this.hourPerWeek + ")" + "}";

        return "{" + this.moduleCode + "}";
    }
}
