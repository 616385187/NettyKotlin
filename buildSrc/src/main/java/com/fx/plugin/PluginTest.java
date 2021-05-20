package com.fx.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

//插件入口
public class PluginTest implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        //do something
       System.out.println("com.fx.plugin.bulidSr_PluginTest");
       target.afterEvaluate(project ->  System.out.println("com.fx.plugin.bulidSr_PluginTest"));
    }
}