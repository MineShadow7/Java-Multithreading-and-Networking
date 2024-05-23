package org.jvmlthread.javamultithreading.game;

import com.google.gson.annotations.Expose;

public record GameLeaderboardEntry(@Expose String playerName, @Expose int rank) {
}

