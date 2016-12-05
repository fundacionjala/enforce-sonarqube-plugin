package org.fundacionjala.sonarqube.apex;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;

import static org.fest.assertions.Assertions.assertThat;

public class ApexPluginTest {

    private static final Version V6_0 = Version.create(6, 0);

    private ApexPlugin apexPlugin;

    @Before
    public void setUp() {
        apexPlugin = new ApexPlugin();
    }

    @Test
    public void testPluginExtensionsCompatibleWithV6_0() {
        SonarRuntime runtime = SonarRuntimeImpl.forSonarQube(V6_0, SonarQubeSide.SCANNER);
        Plugin.Context context = new Plugin.Context(runtime);
        apexPlugin.define(context);
        assertThat(context.getExtensions()).hasSize(3);
    }
}
