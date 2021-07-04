# jenkins-observability-talk

## Components

1. Grafana
2. Prometheus
3. Zipkin - display traces from OpenTelemetry collector
4. OpenTelemetry Collector - collects traces and metrics from Jenkins OpenTelemetry plugin and exports them to Prometheus and Zipkin
5. InfluxDB - store data sent by Job and Stage monitoring (aka github-autostatus) InfluxDB plugins

## How to install

```console
$ influx -host localhost
Connected to http://localhost:8086 version 1.7.9
InfluxDB shell version: 1.6.4
> show databases
name: databases
name
----
_internal
> create database influxdb_plugin
> create database autostatus_plugin
```

Run some pipelines and query data from the DB:

```console
> show series on influxdb_plugin
key
---
jenkins_data,build_result=SUCCESS,project_name=cd_pipeline_emulation,project_path=cd_pipeline_emulation

> show series on autostatus_plugin
key
---
job,owner=none,repo=cd_pipeline_emulation,result=CompletedSuccess
stage,owner=none,repo=cd_pipeline_emulation,result=CompletedSuccess,stagename=build
stage,owner=none,repo=cd_pipeline_emulation,result=CompletedSuccess,stagename=checkout
stage,owner=none,repo=cd_pipeline_emulation,result=CompletedSuccess,stagename=deploy
stage,owner=none,repo=cd_pipeline_emulation,result=CompletedSuccess,stagename=test
```
