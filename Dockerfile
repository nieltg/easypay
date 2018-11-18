FROM gradle:4.10-alpine AS build

USER root
RUN apk --no-cache add perl perl-uri

USER gradle
COPY --chown=gradle:gradle . .

RUN set -ex \
  && perl tools/gradle-proxy-cfg.pl > ~/.gradle/gradle.properties \
  && gradle build

FROM camunda/camunda-bpm-platform:wildfly-7.10.0-alpha5

COPY --from=build /home/gradle/build/libs/*.war /camunda/standalone/deployments
