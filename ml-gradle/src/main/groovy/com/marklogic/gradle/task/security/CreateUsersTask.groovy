package com.marklogic.gradle.task.security

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import com.marklogic.gradle.xcc.XccHelper

class CreateUsersTask extends DefaultTask {

    String xccUrl
    String[] usernames
    String[] descriptions
    String[] passwords
    
    // Should be formatted as ["'role1', 'role2', 'role3'", "'role4', 'role5'"]
    String[] roleNames

    @TaskAction
    void executeXquery() {
        usernames.eachWithIndex() { username, i ->
            String xquery = "xdmp:eval(\"xquery version '1.0-ml'; " +
                    "import module namespace sec = 'http://marklogic.com/xdmp/security' at '/MarkLogic/security.xqy'; " +
                    "if (sec:user-exists('${username}')) then () else " +
                    "sec:create-user('${username}', '${descriptions[i]}', '${passwords[i]}', (${roleNames[i]}), (), ()) " +
                    "\", (), <options xmlns='xdmp:eval'><database>{xdmp:security-database()}</database></options>)";

            println "Creating user ${username}"
            new XccHelper(xccUrl).executeXquery(xquery)
        }
    }
}