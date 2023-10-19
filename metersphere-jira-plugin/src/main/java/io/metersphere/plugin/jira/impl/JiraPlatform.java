package io.metersphere.plugin.jira.impl;


import io.metersphere.plugin.jira.client.JiraDefaultClient;
import io.metersphere.plugin.jira.domain.JiraIntegrationConfig;
import io.metersphere.plugin.jira.domain.JiraIssueProject;
import io.metersphere.plugin.jira.domain.JiraProjectConfig;
import io.metersphere.plugin.platform.dto.PlatformRequest;
import io.metersphere.plugin.platform.spi.AbstractPlatform;
import io.metersphere.plugin.sdk.util.MSPluginException;
import io.metersphere.plugin.sdk.util.PluginUtils;
import org.apache.commons.lang3.StringUtils;
import org.pf4j.Extension;

/**
 * @author jianxing
 */
@Extension
public class JiraPlatform extends AbstractPlatform {

    protected JiraDefaultClient jiraClient;

    public JiraPlatform(PlatformRequest request) {
        super(request);
        JiraIntegrationConfig integrationConfig = getIntegrationConfig(request.getIntegrationConfig(), JiraIntegrationConfig.class);
        jiraClient = new JiraDefaultClient(integrationConfig);
    }

    @Override
    public void validateIntegrationConfig() {
        jiraClient.auth();
    }

    @Override
    public void validateProjectConfig(String projectConfigStr) {
        //TODO 平台key校验
        try {
            JiraProjectConfig projectConfig = getProjectConfig(projectConfigStr);
            JiraIssueProject project = jiraClient.getProject(projectConfig.getJiraKey());
            if (project != null && StringUtils.isBlank(project.getId())) {
                throw new MSPluginException("项目不存在");
            }
        } catch (Exception e) {
            throw new MSPluginException(e.getMessage());
        }
    }


    public JiraProjectConfig getProjectConfig(String configStr) {
        if (StringUtils.isBlank(configStr)) {
            throw new MSPluginException("请在项目中添加项目配置！");
        }
        JiraProjectConfig projectConfig = PluginUtils.parseObject(configStr, JiraProjectConfig.class);
        return projectConfig;
    }
}
