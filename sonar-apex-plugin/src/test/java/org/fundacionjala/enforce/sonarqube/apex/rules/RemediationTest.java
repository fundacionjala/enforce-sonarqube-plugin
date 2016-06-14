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
