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

package com.swirlds.platform.gui;

import static com.swirlds.logging.LogMarker.EXCEPTION;

import com.swirlds.common.system.NodeId;
import com.swirlds.common.system.events.PlatformEvent;
import com.swirlds.platform.Consensus;
import com.swirlds.platform.components.state.StateManagementComponent;
import com.swirlds.platform.gossip.shadowgraph.ShadowGraph;
import com.swirlds.platform.internal.EventImpl;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides a way to access private platform objects from the GUI. Suboptimal, but necessary to preserve the current UI
 * architecture if we don't want to allow public access to these objects.
 *
 * @deprecated this class will eventually be removed
 */
@Deprecated(forRemoval = true)
public final class GuiPlatformAccessor {

    private static final Logger logger = LogManager.getLogger(GuiPlatformAccessor.class);

    private final Map<NodeId, String> aboutStrings = new ConcurrentHashMap<>();
    private final Map<NodeId, String> platformNames = new ConcurrentHashMap<>();
    private final Map<NodeId, byte[]> swirldIds = new ConcurrentHashMap<>();
    private final Map<NodeId, Integer> instanceNumbers = new ConcurrentHashMap<>();
    private final Map<NodeId, ShadowGraph> shadowGraphs = new ConcurrentHashMap<>();
    private final Map<NodeId, StateManagementComponent> stateManagementComponents = new ConcurrentHashMap<>();
    private final Map<NodeId, AtomicReference<Consensus>> consensusReferences = new ConcurrentHashMap<>();

    private static final GuiPlatformAccessor INSTANCE = new GuiPlatformAccessor();

    /**
     * Get the static instance of the GuiPlatformAccessor.
     *
     * @return the static instance of the GuiPlatformAccessor
     */
    public static GuiPlatformAccessor getInstance() {
        return INSTANCE;
    }

    private GuiPlatformAccessor() {}

    /**
     * The SwirldMain calls this to set the string that is shown when the user chooses "About" from the Swirlds menu in
     * the upper-right corner of the window. It is recommended that this be a short string that includes the name of the
     * app, the version number, and the year.
     *
     * @param nodeId the ID of the node
     * @param about  wha should show in the "about" window from the menu
     */
    public void setAbout(@NonNull final NodeId nodeId, @NonNull final String about) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        Objects.requireNonNull(about, "about must not be null");
        aboutStrings.put(nodeId, about);
    }

    /**
     * Get the "about" string, or an empty string if none has been set.
     *
     * @param nodeId the ID of the node
     * @return an "about" string
     */
    public String getAbout(@NonNull final NodeId nodeId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        return aboutStrings.getOrDefault(nodeId, "");
    }

    /**
     * Set a platform name, given the node ID.
     *
     * @param nodeId       the ID of the node
     * @param platformName a platform name
     */
    public void setPlatformName(@NonNull final NodeId nodeId, @NonNull final String platformName) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        Objects.requireNonNull(platformName, "platformName must not be null");
        platformNames.put(nodeId, platformName);
    }

    /**
     * Get a platform name, given the node ID, or an empty string if none has been set.
     *
     * @param nodeId the ID of the node
     * @return a platform name
     */
    @NonNull
    public String getPlatformName(@NonNull final NodeId nodeId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        return platformNames.getOrDefault(nodeId, "");
    }

    /**
     * Set the swirld ID for a node.
     *
     * @param nodeId   the ID of the node
     * @param swirldId the swirld ID
     */
    public void setSwirldId(@NonNull final NodeId nodeId, @NonNull final byte[] swirldId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        Objects.requireNonNull(swirldId, "swirldId must not be null");
        swirldIds.put(nodeId, swirldId);
    }

    /**
     * Get the swirld ID for a node, or null if none is set.
     *
     * @param nodeId the ID of the node
     * @return the swirld ID
     */
    @Nullable
    public byte[] getSwirldId(@NonNull final NodeId nodeId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        return swirldIds.getOrDefault(nodeId, null);
    }

    /**
     * Set the instance number for a node.
     *
     * @param nodeId         the ID of the node
     * @param instanceNumber the instance number
     */
    public void setInstanceNumber(@NonNull final NodeId nodeId, final int instanceNumber) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        instanceNumbers.put(nodeId, instanceNumber);
    }

    /**
     * Get the instance number for a node, or -1 if none is set.
     *
     * @param nodeId the ID of the node
     * @return the instance number
     */
    public int getInstanceNumber(@NonNull final NodeId nodeId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        return instanceNumbers.getOrDefault(nodeId, -1);
    }

    /**
     * Set the shadow graph for a node.
     *
     * @param nodeId      the ID of the node
     * @param shadowGraph the shadow graph
     */
    public void setShadowGraph(@NonNull final NodeId nodeId, @NonNull final ShadowGraph shadowGraph) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        Objects.requireNonNull(shadowGraph, "shadowGraph must not be null");
        shadowGraphs.put(nodeId, shadowGraph);
    }

    /**
     * Get the shadow graph for a node, or null if none is set.
     *
     * @param nodeId the ID of the node
     * @return the shadow graph
     */
    @Nullable
    public ShadowGraph getShadowGraph(@NonNull NodeId nodeId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        return shadowGraphs.getOrDefault(nodeId, null);
    }

    /**
     * Get a sorted list of events.
     */
    public PlatformEvent[] getAllEvents(@NonNull NodeId nodeId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        // There is currently a race condition that can cause an exception if event order changes at
        // just the right moment. Since this is just a testing utility method and not used in production
        // environments, we can just retry until we succeed.
        int maxRetries = 100;
        while (maxRetries-- > 0) {
            try {
                final EventImpl[] allEvents = getShadowGraph(nodeId).getAllEvents();
                Arrays.sort(allEvents, (o1, o2) -> {
                    if (o1.getConsensusOrder() != -1 && o2.getConsensusOrder() != -1) {
                        // both are consensus
                        return Long.compare(o1.getConsensusOrder(), o2.getConsensusOrder());
                    } else if (o1.getConsensusTimestamp() == null && o2.getConsensusTimestamp() == null) {
                        // neither are consensus
                        return o1.getTimeReceived().compareTo(o2.getTimeReceived());
                    } else {
                        // one is consensus, the other is not
                        if (o1.getConsensusTimestamp() == null) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                return allEvents;
            } catch (final IllegalArgumentException e) {
                logger.error(EXCEPTION.getMarker(), "Exception while sorting events", e);
            }
        }
        throw new IllegalStateException("Unable to sort events after 100 retries");
    }

    /**
     * Set the state management component for a node.
     *
     * @param nodeId                   the ID of the node
     * @param stateManagementComponent the state management component
     */
    public void setStateManagementComponent(
            @NonNull final NodeId nodeId, @NonNull final StateManagementComponent stateManagementComponent) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        Objects.requireNonNull(stateManagementComponent, "stateManagementComponent must not be null");
        stateManagementComponents.put(nodeId, stateManagementComponent);
    }

    /**
     * Get the state management component for a node, or null if none is set.
     *
     * @param nodeId the ID of the node
     * @return the state management component
     */
    @Nullable
    public StateManagementComponent getStateManagementComponent(@NonNull final NodeId nodeId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        return stateManagementComponents.getOrDefault(nodeId, null);
    }

    /**
     * Set the consensus for a node.
     *
     * @param nodeId    the ID of the node
     * @param consensus the consensus
     */
    public void setConsensusReference(
            @NonNull final NodeId nodeId, @NonNull final AtomicReference<Consensus> consensus) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        Objects.requireNonNull(consensus, "consensus must not be null");
        consensusReferences.put(nodeId, consensus);
    }

    /**
     * Get the consensus for a node, or null if none is set.
     *
     * @param nodeId the ID of the node
     * @return the consensus
     */
    @Nullable
    public Consensus getConsensus(@NonNull final NodeId nodeId) {
        Objects.requireNonNull(nodeId, "nodeId must not be null");
        final AtomicReference<Consensus> consensusReference = consensusReferences.getOrDefault(nodeId, null);
        if (consensusReference == null) {
            return null;
        }
        return consensusReference.get();
    }
}