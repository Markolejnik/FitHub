package sk.spse.olejnik.fithub;

import java.util.List;

public class FilterData {

    private String filterType;
    private List<String> filterMuscleGroups;


    public FilterData() {}

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public List<String> getFilterMuscleGroups() {
        return filterMuscleGroups;
    }

    public void setFilterMuscleGroups(List<String> filterMuscleGroups) {
        this.filterMuscleGroups = filterMuscleGroups;
    }

}
