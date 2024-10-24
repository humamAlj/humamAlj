package io.jenkins.plugins;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Run;

public class TestingResultBuildAction implements Action {
    private final AbstractBuild<?, ?> build;
    private final String errorString;
    private final String customMessage;

    public TestingResultBuildAction(AbstractBuild<?, ?> build, String errorString, String customMessage) {
        super();
        this.build = build;
        this.errorString = errorString;
        this.customMessage = customMessage;
    }

    @Override
    public String getDisplayName() {
        return "Build Ergebnisse anzeigen";
    }

    @Override
    public String getUrlName() {
        return "buildErgebnisse";
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    public String getErrorString() {
        return errorString;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    protected String getTitle() {
        return this.build.getDisplayName() + "html3";
    }

    Run<?, ?> getBuild() {
        return this.build;
    }
}
