# jenkins-observability-talk

# Components
1. Grafana
2. Prometheus
3. Zipkin - display traces from OpenTelemetry collector
4. OpenTelemetry Collector - collects traces and metrics from Jenkins OpenTelemetry plugin and exports them to Prometheus and Zipkin
5. InfluxDB - store data sent by Job and Stage monitoring (aka github-autostatus) InfluxDB plugins