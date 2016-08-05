/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.toolkit;

import com.sonar.sslr.impl.Parser;
import org.sonar.colorizer.Tokenizer;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;

import java.util.List;

public class ApexConfigurationModel extends AbstractConfigurationModel {
    @Override
    public Parser doGetParser() {
        return null;
    }

    @Override
    public List<Tokenizer> doGetTokenizers() {
        return null;
    }

    @Override
    public List<ConfigurationProperty> getProperties() {
        return null;
    }
}
