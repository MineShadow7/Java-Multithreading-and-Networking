package org.jvmlthread.javamultithreading.common.model;

import com.google.gson.annotations.Expose;

public record LeaderboardEntry(@Expose String playerName, @Expose int rank) {
}
