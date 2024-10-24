package io.jenkins.plugins;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Computer;
import hudson.slaves.SlaveComputer;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import java.io.IOException;
import org.kohsuke.stapler.DataBoundConstructor;

public class TestingResultPublisher extends Recorder {
    /*  String pcError = "PC nicht gestartet";
    String buildError = "Code könnte nicht gebildet werden";
    String testsoftwareError = "testSoftware könnte nicht ausgeführt werden";
    String testError = "test fehlgeschlagen";
    String skriptError = "testskript error";
    String connectionError = "fehler bei verbindung";
     */
    public final String errorString;
    public final String customMessage;

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    @DataBoundConstructor
    public TestingResultPublisher(String errorString, String customMessage) {
        this.errorString = errorString;
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public String getErrorString() {
        return errorString;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
            throws IOException, InterruptedException {

        build.addAction(new TestingResultBuildAction(build, errorString, customMessage));
        /*
               listener.getLogger().println("");
               String buildNumber = Integer.toString(build.getNumber());
               String buildProject = build.getProject().getName();
        */
        listener.getLogger().println("Build Number: " + build.getNumber());
        listener.getLogger().println("Project Name: " + build.getProject().getName());

        if (Computer.currentComputer() instanceof SlaveComputer) {
            listener.getLogger().println("current Computer used");

        } else {
            listener.getLogger().println("another Computer used");
        }

        listener.getLogger().println("Error String: " + errorString);
        listener.getLogger().println("Custom String: " + customMessage);
        listener.getLogger().println("TestingResultPublisher completed");

        return true;
    }

    @Override
    public BuildStepDescriptor getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        @SuppressWarnings("deprecation")
        public DescriptorImpl() {
            super(TestingResultPublisher.class);
        }

        @Override
        public String getDisplayName() {
            return "publish test result";
        }

        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }
    }
}
