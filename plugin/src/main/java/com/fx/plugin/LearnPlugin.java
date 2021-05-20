package com.fx.plugin;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class LearnPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("LearnPlugin");
        //添加至构建
        project.getExtensions().getByType(AppExtension.class).registerTransform(new LifeCycleTransform(project));
    }
}