package io.digdag.core.agent;

import java.util.List;
import java.time.Instant;
import com.google.common.base.Optional;
import io.digdag.client.config.Config;
import io.digdag.spi.TaskReport;
import io.digdag.core.session.SessionStateFlags;

public interface TaskCallbackApi
{
    void taskHeartbeat(List<String> lockedIds, AgentId agentId, int lockSeconds);

    void taskSucceeded(long taskId, String lockId, AgentId agentId,
            Config stateParams, Config subtaskConfig,
            TaskReport report);

    void taskFailed(long taskId, String lockId, AgentId agentId,
            Config error, Config stateParams,
            Optional<Integer> retryInterval);

    void taskPollNext(long taskId, String lockId, AgentId agentId,
            Config stateParams, int retryInterval);

    SessionStateFlags startSession(
            int repositoryId,
            String workflowName,
            Instant instant,
            Optional<String> retryAttemptName,
            Config overwriteParams);
}