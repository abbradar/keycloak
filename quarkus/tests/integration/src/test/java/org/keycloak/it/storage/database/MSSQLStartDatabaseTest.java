/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.it.storage.database;

import org.junit.jupiter.api.Test;
import org.keycloak.it.junit5.extension.CLIResult;
import org.keycloak.it.junit5.extension.CLITest;
import org.keycloak.it.junit5.extension.WithDatabase;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;

@CLITest
@WithDatabase(alias = "mssql")
public class MSSQLStartDatabaseTest extends AbstractStartDabataseTest {

    /**
     * It should be possible to enable XA but here we reproduce a managed environment where only nonXA transaction is supported
     *
     * @param result
     */
    @Override
    @Test
    @Launch({ "-Dkc.db.tx-type=enabled", "-Dkc.db.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver", "start-dev" })
    void testSuccessful(LaunchResult result) {
        CLIResult cliResult = (CLIResult) result;
        cliResult.assertStartedDevMode();
    }

    @Override
    protected void assertWrongUsername(CLIResult cliResult) {
        cliResult.assertMessage("ERROR: Login failed for user 'wrong'");
    }

    @Override
    protected void assertWrongPassword(CLIResult cliResult) {
        cliResult.assertMessage("Login failed for user");
    }
}
