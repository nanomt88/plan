package com.nanomt88.maven;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 *
 *
 * @author nanomt88@gmail.com
 * @create 2022/3/3 14:23
 **/
public class MavenTest {

    @Test
    public void readJar(){
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);
    }

    @Test
    public void readPom() throws Exception{
        FileInputStream fileInputStream = new FileInputStream(
                new File("D:\\workspaces\\idea\\plan\\gateway-demo\\pom.xml"));
        MavenXpp3Reader mavenReader = new MavenXpp3Reader();
        Model mavenModel = mavenReader.read(fileInputStream);
        Parent parent = mavenModel.getParent();
        if(parent != null){
            System.out.println("parent:" + parent);
        }

        List<Dependency> dependencies = mavenModel.getDependencies();
        for(Dependency dependency : dependencies){
            String artifactId = dependency.getArtifactId();
            String groupId = dependency.getGroupId();
            String version = dependency.getVersion();
//            System.out.println(dependency);
            System.out.println(groupId + "." + artifactId + ":" + version);
        }

        DependencyManagement dependencyManagement = mavenModel.getDependencyManagement();
        List<Dependency> managementDependencies = dependencyManagement == null ? null : dependencyManagement.getDependencies();

    }

}
