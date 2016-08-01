package online.pizzacrust.incomer;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.lang.System.*;

public class IncomerPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.afterEvaluate((evalProject) -> {
            out.println("====================");
            out.println(" PIZZACRUST PROJECT ");
            PluginExtension extension = evalProject.getExtensions().getByType(PluginExtension
                    .class);
            RecursiveFinder javaFinder = new RecursiveFinder(evalProject.getRootDir(), "java");
            RecursiveFinder gradleFinder = new RecursiveFinder(evalProject.getRootDir(), "gradle");
            RecursiveFinder kotlinFinder = new RecursiveFinder(evalProject.getRootDir(), "kt");
            javaFinder.find();
            gradleFinder.find();
            kotlinFinder.find();
            int totalAmountOfFiles = javaFinder.getFiles().size() + gradleFinder.getFiles().size
                    () + kotlinFinder.getFiles().size();
            out.println(" FILE AMOUNT: " + totalAmountOfFiles);
            long total = longFiles(javaFinder.getFiles()) + longFiles(gradleFinder.getFiles()) +
                    longFiles(kotlinFinder.getFiles());
            out.println(" LINES: " + total);
            int divided = (int) (total / extension.lineCount);
            int money = (int) (divided * extension.incomePerCount);
            out.println(" WORTH: $" + money);
            out.println("====================");
        });
        project.getExtensions().add("income", PluginExtension.class);
    }

    private long longFiles(List<File> fileList) {
        long count = 0L;
        for (File file : fileList) {
            count = count + lines(file);
        }
        return count;
    }

    private long lines(File file) {
        try {
            return Files.lines(file.toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
