package org.fundacionjala.sonarqube.apex;

import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;
import org.fundacionjala.sonarqube.tree.Tree;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.File;
import java.util.List;

public class ApexAstScanner {

    private static final Logger LOG = Loggers.get(ApexSquid.class);
    private ActionParser<Tree> parser;

    public ApexAstScanner(ActionParser<Tree> parser){
        this.parser = parser;
    }

    public void scan(List<File> inputFiles) {
        if (!inputFiles.isEmpty()) {
            for (File currentFile : inputFiles) {
                simpleScan(currentFile);
            }
        }
    }

    private void simpleScan(File file) {
        try {
            Tree ast = parser.parse(file);
        } catch (RecognitionException exception) {
            LOG.error("Unable to parse source file : " + file.getAbsolutePath());
            LOG.error(exception.getMessage());
        }
    }

}
