package com.example.mail.sortStrategy;

public class SortStrategyFactory {
    public SortStrategyFactory() {}

    public SortStrategy createStrategy(String sort){
        if (sort.equals("priority"))
            return new SortByPriority();
        else
            return new SortByDefault();

    }
}
