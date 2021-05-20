package com.fx.plugin;


import com.android.build.api.transform.Context;
import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;

import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LifeCycleTransform extends Transform {
    Project project;

    public LifeCycleTransform(Project project) {
        this.project = project;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    // 核心方法
    // inputs是传过来的输入流，有两种格式：jar和目录格式
    // outputProvider 获取输出目录，将修改的文件复制到输出目录，必须执行
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        //当前是否是增量编译
        project.getLogger().warn("***********************transform start****************************");
        boolean isIncremental = transformInvocation.isIncremental();
        //消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        //引用型输入，无需输出。
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs();
        //OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        for (TransformInput input : inputs) {
            for (JarInput jarInput : input.getJarInputs()) {
                File dest = outputProvider.getContentLocation(
                        jarInput.getFile().getAbsolutePath(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
//                FileUtils.copyFile(jarInput.getFile(), dest);
                project.getLogger().warn("jarInput----->" + jarInput.getName() );
                scanJar(jarInput);
            }
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                File dest = outputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(),
                        Format.DIRECTORY);
                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
//                FileUtils.copyDirectory(directoryInput.getFile(), dest);
                project.getLogger().warn("directoryInput----->" + directoryInput.getFile().getAbsolutePath() + "dest-->" + dest.getAbsolutePath());
            }
        }
        project.getLogger().warn("***********************transform end****************************");
    }

    private void scanJar(JarInput jarInput) {
        try {
            JarFile jarFile = new JarFile(jarInput.getFile());
            Enumeration<JarEntry> jarEntry = jarFile.entries();
            while (jarEntry.hasMoreElements()) {
                project.getLogger().warn("class--->" + jarEntry.nextElement().getName());
            }
            jarFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }


}
