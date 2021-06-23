/*
    00-root-logger.groovy
    Makes Jenkins to write its log to a file in addition to usual console output
    to make them scrapable by Promtail.
    Note: logs will be available only after this script will be executed, so
    some messages from before won't appear in resulting file.
    If you need those messages, you better scrape logs from Jenkins container, but not all
    of them following easy-parseable pattern
*/

import java.util.logging.FileHandler
import java.util.logging.SimpleFormatter
import java.util.logging.Logger
import java.util.logging.Level
import jenkins.model.Jenkins
// According to https://github.com/jenkinsci/winstone/pull/63 this is formatter
// class used for default Jenkins logs
// Let's reuse it - it would be easier to parse log lines produced by this
import io.jenkins.lib.support_log_formatter.SupportLogFormatter

def logsDir = new File(Jenkins.instance.rootDir, 'custom_logs')
if(!logsDir.exists()) {
    logsDir.mkdirs()
}

FileHandler handler = new FileHandler(logsDir.absolutePath+'/jenkins-%g.log',
                                      100 * 1024 * 1024, 10, true);

handler.setFormatter(new SupportLogFormatter());
handler.setLevel(Level.INFO)

println '--> Send Jenkins logs to a file'
Logger.getLogger('').addHandler(handler)
Logger.getLogger('').setLevel(Level.INFO)

println '--> Set log levels for packages that write to main log'
Logger.getLogger('com.cloudbees.simplediskusage').setLevel(Level.WARNING)
