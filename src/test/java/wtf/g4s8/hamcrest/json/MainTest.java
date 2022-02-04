/*
 * MIT License
 *
 * Copyright (c) 2018 Kirill
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights * to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package wtf.g4s8.hamcrest.json;

import java.io.IOException;
import wtf.g4s8.oot.ConsoleReport;
import wtf.g4s8.oot.FailingReport;
import wtf.g4s8.oot.ParallelTests;
import wtf.g4s8.oot.TestCase;
import wtf.g4s8.oot.TestGroup;

/**
 * Tests entry point.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.ProhibitPublicStaticMethods",
        "PMD.JUnit4TestShouldUseTestAnnotation",
        "PMD.TestClassWithoutTestCases"
    }
)
public final class MainTest extends TestCase.Wrap {

    /**
     * Ctor.
     */
    private MainTest() {
        super(
            new ParallelTests(
                new TestGroup.Joined(
                    new JsonContainsCase(),
                    new JsonHasCase(),
                    new JsonValueIsCase(),
                    new StringIsJsonCase(),
                    new ReadmeExamplesCase()
                )
            )
        );
    }

    /**
     * Entry point.
     * @throws IOException On report failure
     */
    public static void test() throws IOException {
        try (FailingReport report = new FailingReport(new ConsoleReport())) {
            new MainTest().run(report);
        }
    }
}
