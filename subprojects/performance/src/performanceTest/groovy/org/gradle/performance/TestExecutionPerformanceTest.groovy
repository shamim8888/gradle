/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.performance

import org.gradle.performance.categories.JavaPerformanceTest
import org.junit.experimental.categories.Category
import spock.lang.Unroll

@Category([JavaPerformanceTest])
class TestExecutionPerformanceTest extends AbstractCrossVersionPerformanceTest {
    @Unroll("Project '#testProject' test execution")
    def "test execution"() {
        given:
        runner.testId = "test $testProject"
        runner.testProject = testProject
        runner.tasksToRun = ['cleanTest', 'test']
        runner.args = ['-q']
        runner.targetVersions = ['2.11', 'last']
        runner.useDaemon = true

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()

        where:
        testProject << ["withTestNG", "withJUnit", "withVerboseTestNG", "withVerboseJUnit"]
    }
}
