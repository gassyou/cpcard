package com.tutum.cpcard.entities;

/**
 * Created by Mars on 2015/4/21.
 */
public class Vote {
    private Member m;
    private Integer worth;

    public Integer getWorth()
    {
        return worth;
    }

    public void setWorth(Integer w)
    {
        this.worth = w;
    }

    public Member getActor()
    {
        return m;
    }

    public void setActor(Member m)
    {
        this.m = m;
    }


    public Vote(Member m,Integer worth)
    {
        this.m = m;
        this.worth = worth;
    }
}
