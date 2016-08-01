package online.pizzacrust.incomer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFinder {
    private final File rootDirectory;
    private final String filter;
    private final List<File> files = new ArrayList<>();

    public RecursiveFinder(File rootDirectory,
                           String filenameFilter) {
        this.rootDirectory = rootDirectory;
        this.filter = filenameFilter;
    }

    public List<File> getFiles() {
        return files;
    }

    public void find() {
        findInternally(rootDirectory);
    }

    private void findInternally(File file) {
        if (file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                findInternally(file1);
            }
        } else if (file.getName().endsWith(filter)) {
            files.add(file);
        }
    }

}
