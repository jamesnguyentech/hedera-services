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

package com.swirlds.cli.logging;

import static com.swirlds.cli.logging.LogProcessingUtils.colorizeLogLineAnsi;

import com.swirlds.common.formatting.TextEffect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZoneId;

/**
 * An entry point that log output can be piped into to colorize it with ANSI.
 */
public class StdInOutColorize {
    public static void main(final String[] args) throws IOException {
        TextEffect.setTextEffectsEnabled(true);

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String rawLine = bufferedReader.readLine();
        while (rawLine != null) {
            final String outputLine = colorizeLogLineAnsi(rawLine, ZoneId.systemDefault());
            System.out.println(outputLine);

            rawLine = bufferedReader.readLine();
        }

        bufferedReader.close();
    }
}
