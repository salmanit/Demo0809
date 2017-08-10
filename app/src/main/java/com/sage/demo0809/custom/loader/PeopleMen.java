package com.sage.demo0809.custom.loader;

/**
 * Created by Sage on 2017/5/12.
 */

public class PeopleMen implements PeopleIml {
    @Override
    public String sayHello() {
        return "hello boy!";
    }

    @Override
    public String sayDes() {
        return "you are handsome";
    }
}
