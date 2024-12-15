package org.uzdiz.timeTableComposite;

import java.util.ArrayList;
import java.util.List;

public abstract class TimeTableComposite extends TimeTableComponent {
    protected List<TimeTableComponent> children = new ArrayList<>();
    protected String name;

    public TimeTableComposite(String name) {
        this.name = name;
    }

    @Override
    public void showDetails() {
        for (TimeTableComponent child : this.children) {
            child.showDetails();
        }
    }

    public boolean add(TimeTableComponent component) {
        this.children.add(component);
        return true;
    }

    public boolean remove(TimeTableComponent component) {
        return this.children.remove(component);
    }

    public TimeTableComponent getChild(int i) {
        return this.children.get(i);
    }

    public String getName() {
        return this.name;
    }

    public List<TimeTableComponent> getChildren() {
        return children;
    }
}
