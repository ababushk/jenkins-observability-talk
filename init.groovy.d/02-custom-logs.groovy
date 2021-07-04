/*
    02-custom-logs.groovy
    Sends more verbose logs of selected packages to files
    to make them scrapable by Promtail
*/
import java.util.logging.FileHandler
import java.util.logging.SimpleFormatter
import java.util.logging.Logger
import java.util.logging.Level
import jenkins.model.Jenkins
import io.jenkins.lib.support_log_formatter.SupportLogFormatter

/* put logs from given Java package to a file */
def setupCustomLogging(String package_name) {
    def logger = Logger.getLogger(package_name)
    def logsDir = new File(Jenkins.instance.rootDir, 'custom_logs')
    if(!logsDir.exists()) {
        logsDir.mkdirs()
    }
    FileHandler handler = new FileHandler(logsDir.absolutePath+"/${package_name}-%g.log",
                                          100 * 1024 * 1024, 10, true);

    println "--> Setting custom logging for ${package_name}..."
    handler.setFormatter(new SupportLogFormatter());
    handler.setLevel(Level.FINE)
    logger.setLevel(Level.FINE)
    logger.addHandler(handler)
    println "--> Custom logging for ${package_name} was set"
}

def packages = [
    'org.jenkinsci.plugins.workflow',
    'org.jenkinsci.plugins.githubautostatus',
]

packages.each { pkg ->
    setupCustomLogging(pkg)
}
