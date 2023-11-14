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

package com.hedera.services.bdd.spec.utilops.records;

/**
 * Enumerates non-default matching modes in which {@link SnapshotModeOp} fuzzy-matching can be run.
 */
public enum SnapshotMatchMode {
    /**
     * Allows for non-deterministic contract call results.
     */
    NONDETERMINISTIC_CONTRACT_CALL_RESULTS,
    /**
     * Allows for non-deterministic function parameters.
     */
    NONDETERMINISTIC_FUNCTION_PARAMETERS,
    /**
     * Allows for non-deterministic amounts.
     */
    NONDETERMINISTIC_TRANSACTION_FEES,
    /**
     * Lets a spec advertise itself as being non-deterministic.
     *
     * <p>We need this to let such specs to opt out of auto record snapshots, since fuzzy-matching would never pass.
     */
    FULLY_NONDETERMINISTIC,
    /**
     * Some of the ingest checks in mono-service are moved into pureChecks or handle in modular service. So any
     * response code added in spec.streamlinedIngestChecks will not produce a record in mono-service, as it is rejected in ingest.
     * But in modular service we produce a record. This will not cause any issue for differential testing, because we test
     * transactions that have reached consensus. Use this snapshot mode to still fuzzy-match against records whose
     * receipt's status would be rejected in pre-check by mono-service.
     */
    EXPECT_STREAMLINED_INGEST_RECORDS,
}