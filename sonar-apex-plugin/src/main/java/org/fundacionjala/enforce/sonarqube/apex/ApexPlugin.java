/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.sonar.api.SonarPlugin;

import org.fundacionjala.enforce.sonarqube.apex.cpd.ApexCpdMapping;
import org.fundacionjala.enforce.sonarqube.apex.rules.ApexCommonRulesDecorator;
import org.fundacionjala.enforce.sonarqube.apex.rules.ApexCommonRulesEngine;
import org.fundacionjala.enforce.sonarqube.apex.rules.ApexRulesDefinition;

/**
 * It's an entry-point to declare its extensions.
 */
public class ApexPlugin extends SonarPlugin {

    /**
     * Classes of the implemented extensions.
     *
     * @return a list of the extensions.
     */
    @Override
    public List getExtensions() {
        return ImmutableList.of(
                Apex.class,
                ApexCpdMapping.class,

                ApexSquidSensor.class,
                ApexRulesDefinition.class,
                ApexProfile.class,

                ApexCommonRulesDecorator.class,
                ApexCommonRulesEngine.class);
    }
}
