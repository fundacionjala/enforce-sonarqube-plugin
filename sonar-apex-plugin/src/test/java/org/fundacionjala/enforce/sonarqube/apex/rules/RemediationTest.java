/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.junit.Test;
import org.sonar.api.server.debt.DebtRemediationFunction;
import org.sonar.api.server.rule.RulesDefinition.DebtRemediationFunctions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RemediationTest {

    private final Remediation remediation;

    public RemediationTest() {
        remediation = new Remediation();
    }

    @Test
    public void testRemediationFunctionConstantPerIssue() {
        remediation.func = "Constant issue";
        remediation.constantCost = "3 mn";
        DebtRemediationFunctions functions = mock(DebtRemediationFunctions.class);
        DebtRemediationFunction function = mock(DebtRemediationFunction.class);
        when(functions.constantPerIssue(eq(remediation.constantCost))).thenReturn(function);
        when(function.coefficient()).thenReturn("3 min");
        remediation.remediationFunction(functions);
        verify(functions).constantPerIssue("3 min");
        assertEquals("3 min",function.coefficient());
    }
    
    @Test
    public void testRemediationFunctionLinear() {
        remediation.func = "Linear";
        remediation.linearFactor = "2 mn";
        DebtRemediationFunctions functions = mock(DebtRemediationFunctions.class);
        DebtRemediationFunction function = mock(DebtRemediationFunction.class);
        when(functions.linear(eq(remediation.linearFactor))).thenReturn(function);
        when(function.coefficient()).thenReturn("2 min");
        remediation.remediationFunction(functions);
        verify(functions).linear("2 min");
        assertEquals("2 min",function.coefficient());
    }

    @Test
    public void testRemediationFunctionlinearWithOffset() {
        remediation.func = "somethingElse";
        remediation.linearFactor = "2 mn";
        remediation.linearOffset = "2 mn";
        DebtRemediationFunctions functions = mock(DebtRemediationFunctions.class);
        DebtRemediationFunction function = mock(DebtRemediationFunction.class);
        when(functions.linearWithOffset(eq(remediation.linearFactor), eq(remediation.linearOffset))).thenReturn(function);
        when(function.coefficient()).thenReturn("2 min");
        remediation.remediationFunction(functions);
        verify(functions).linearWithOffset("2 min", "2 min");
        assertEquals("2 min",function.coefficient());
    }

}
