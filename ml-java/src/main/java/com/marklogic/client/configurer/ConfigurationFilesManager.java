package com.marklogic.client.configurer;

import java.io.File;
import java.util.Date;

/**
 * Defines operations for managing whether a configuration file needs to be installed or not.
 */
public interface ConfigurationFilesManager {

    /**
     * Give the implementor a chance to initialize itself - e.g. loading data from a properties file or other resource.
     */
    public void initialize();

    public boolean hasFileBeenModifiedSinceLastInstalled(File file);

    public void saveLastInstalledTimestamp(File file, Date date);
}