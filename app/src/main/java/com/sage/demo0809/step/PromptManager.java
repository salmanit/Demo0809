package com.sage.demo0809.step;

/**
 * Created by Sage on 2016/8/12.
 */
public class PromptManager {
    private static PromptManager promptManager;
    public static PromptManager getInstance(){
        if(promptManager==null){
            promptManager=new PromptManager();
        }
        return  promptManager;
    }
}
