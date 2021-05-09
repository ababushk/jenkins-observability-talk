FROM jenkins/jenkins:2.277.3-jdk11

USER root

# Install plugins
# force versions from plugins.txt
ENV PLUGINS_FORCE_UPGRADE=true
COPY --chown=jenkins:jenkins plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

# Install groovy startup hooks
COPY init.groovy.d/*.groovy /usr/share/jenkins/ref/init.groovy.d/

# Configuration as code settings
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc
COPY casc /usr/share/jenkins/ref/casc

# Persistent data
VOLUME $JENKINS_HOME/builds
VOLUME $JENKINS_HOME/custom_logs

# Setting JAVA_OPTS
# 1. Skip install wizard. All configuration is done via CasC and groovy scripts
# 2. Separate builds from jobs configurations
ENV JAVA_OPTS='\
        -Djenkins.install.runSetupWizard=false \
        -Djenkins.model.Jenkins.buildsDir=${JENKINS_HOME}/builds/${ITEM_FULL_NAME}'

USER jenkins
# parent's ENTRYPOINT should be used
