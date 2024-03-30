package com.backend.core.usecase.util.handler;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class SourceCodeHandlerUtil {
    public static void deleteDirectory(String path) throws IOException {
        File file = new File(path);

        if (file != null) {
            FileUtils.deleteDirectory(file);
            file.delete();
        }
    }
}
