package org.fundacionjala.sonarqube.visitors;

import org.fundacionjala.sonarqube.apex.ApexCheck;
import org.sonar.api.batch.sensor.SensorContext;

public abstract class ClassVisitor implements ApexCheck {

    SensorContext context;


}
