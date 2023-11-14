/*
 * Copyright (C) 2023 Hedera Hashgraph, LLC
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

package com.swirlds.platform.wiring;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.swirlds.base.time.Time;
import com.swirlds.common.context.PlatformContext;
import com.swirlds.test.framework.context.TestPlatformContextBuilder;
import org.junit.jupiter.api.Test;

class WiringTests {

    @Test
    void cyclicalBackpressureTest() {
        final PlatformContext context = TestPlatformContextBuilder.create().build();
        final PlatformWiring wiring = new PlatformWiring(context, Time.getCurrent());

        assertFalse(wiring.isCyclicalBackpressurePresent(), "cyclical back pressure detected");
    }

    @Test
    void illegalDirectSchedulerAccessTest() {
        final PlatformContext context = TestPlatformContextBuilder.create().build();
        final PlatformWiring wiring = new PlatformWiring(context, Time.getCurrent());

        assertFalse(wiring.isIllegalDirectSchedulerUsagePresent(), "illegal direct scheduler usage detected");
    }
}