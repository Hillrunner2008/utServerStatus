package com.utstatus.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ExecuatableFileFilter extends FileFilter implements java.io.FileFilter {

    private String exts[] = {".exe", ".X86_64", ".i386"};
    private boolean acceptFolder = true;

    public ExecuatableFileFilter(boolean folder) {
        this.acceptFolder = folder;
    }

    public ExecuatableFileFilter() {
    }

    public boolean accept(File file) {
        if (file.isDirectory() && acceptFolder) {
            return true;
        }
        if (file.isFile()) {
            String name = file.getName().toLowerCase();
            for (String ext : exts) {
                if (name.endsWith(ext)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public String getDescription() {
        return "All Image Types";
    }
}
