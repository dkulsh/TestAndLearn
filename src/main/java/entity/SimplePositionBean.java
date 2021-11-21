package entity;

import com.opencsv.bean.CsvBindByPosition;

public class SimplePositionBean  {

    public SimplePositionBean() {
    }

    public SimplePositionBean(String exampleColOne, String exampleColTwo) {
        this.exampleColOne = exampleColOne;
        this.exampleColTwo = exampleColTwo;
    }

    @CsvBindByPosition(position = 0)
    private String exampleColOne;

    @CsvBindByPosition(position = 1)
    private String exampleColTwo;
}