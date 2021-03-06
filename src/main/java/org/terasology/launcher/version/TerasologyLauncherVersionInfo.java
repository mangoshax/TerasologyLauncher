/*
 * Copyright 2016 MovingBlocks
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

package org.terasology.launcher.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TerasologyLauncherVersionInfo {

    private static final Logger logger = LoggerFactory.getLogger(TerasologyLauncherVersionInfo.class);

    private static final String VERSION_INFO_FILE = "versionInfo.properties";

    private static final String BUILD_NUMBER = "buildNumber";
    private static final String BUILD_ID = "buildId";
    private static final String BUILD_TAG = "buildTag";
    private static final String BUILD_URL = "buildUrl";
    private static final String JOB_NAME = "jobName";
    private static final String GIT_BRANCH = "gitBranch";
    private static final String GIT_COMMIT = "gitCommit";
    private static final String DATE_TIME = "dateTime";
    private static final String DISPLAY_VERSION = "displayVersion";

    private static final String DEFAULT_VALUE = "";

    private static TerasologyLauncherVersionInfo instance;

    // Indicates whether this version info is 'empty' (usually indicates that the launcher is being run in a development environment)
    private final boolean isEmpty;
    private final String buildNumber;
    private final String buildId;
    private final String buildTag;
    private final String buildUrl;
    private final String jobName;
    private final String gitBranch;
    private final String gitCommit;
    private final String dateTime;
    private final String displayVersion;
    private final String stringRepresentation;

    private TerasologyLauncherVersionInfo(Properties versionInfoProperties) {
        final Properties properties;
        if (versionInfoProperties != null) {
            properties = versionInfoProperties;
        } else {
            properties = loadPropertiesFromInputStream(this.getClass().getResourceAsStream(VERSION_INFO_FILE));
        }

        isEmpty = properties.isEmpty();
        buildNumber = properties.getProperty(BUILD_NUMBER, DEFAULT_VALUE);
        buildId = properties.getProperty(BUILD_ID, DEFAULT_VALUE);
        buildTag = properties.getProperty(BUILD_TAG, DEFAULT_VALUE);
        buildUrl = properties.getProperty(BUILD_URL, DEFAULT_VALUE);
        jobName = properties.getProperty(JOB_NAME, DEFAULT_VALUE);
        gitBranch = properties.getProperty(GIT_BRANCH, DEFAULT_VALUE);
        gitCommit = properties.getProperty(GIT_COMMIT, DEFAULT_VALUE);
        dateTime = properties.getProperty(DATE_TIME, DEFAULT_VALUE);
        displayVersion = properties.getProperty(DISPLAY_VERSION, DEFAULT_VALUE);

        final StringBuilder stringRepresentationBuilder = new StringBuilder();
        stringRepresentationBuilder.append("[");
        stringRepresentationBuilder.append(BUILD_NUMBER);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(buildNumber);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append(BUILD_ID);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(buildId);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append(BUILD_TAG);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(buildTag);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append(BUILD_URL);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(buildUrl);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append(JOB_NAME);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(jobName);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append(GIT_BRANCH);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(gitBranch);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append(GIT_COMMIT);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(gitCommit);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append(DATE_TIME);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(dateTime);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append(DISPLAY_VERSION);
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(displayVersion);
        stringRepresentationBuilder.append(", ");
        stringRepresentationBuilder.append("isEmpty");
        stringRepresentationBuilder.append("=");
        stringRepresentationBuilder.append(isEmpty);
        stringRepresentationBuilder.append("]");

        stringRepresentation = stringRepresentationBuilder.toString();
    }

    public static synchronized TerasologyLauncherVersionInfo getInstance() {
        if (instance == null) {
            instance = new TerasologyLauncherVersionInfo(null);
        }
        return instance;
    }

    public static TerasologyLauncherVersionInfo loadFromInputStream(InputStream inStream) {
        return new TerasologyLauncherVersionInfo(loadPropertiesFromInputStream(inStream));
    }

    private static Properties loadPropertiesFromInputStream(InputStream inStream) {
        final Properties properties = new Properties();
        if (inStream != null) {
            try {
                properties.load(inStream);
            } catch (IOException e) {
                logger.error("Loading launcher version info failed!", e);
            } finally {
                try {
                    inStream.close();
                } catch (IOException e) {
                    logger.warn("Closing InputStream failed!", e);
                }
            }
        }

        return properties;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getBuildId() {
        return buildId;
    }

    public String getBuildTag() {
        return buildTag;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public String getJobName() {
        return jobName;
    }

    public String getGitBranch() {
        return gitBranch;
    }

    public String getGitCommit() {
        return gitCommit;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDisplayVersion() {
        return displayVersion;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
