#!/usr/bin/env bash

BLADE_VERSION='3.1.0.201807032155'

echo "installing blade-cli v${BLADE_VERSION} locally"
echo 'alternatives for this script: https://github.com/liferay/liferay-blade-cli/tree/master/cli/installers'

###########################
echo 'discovering JPM dir'

curl -sL https://github.com/jpm4j/jpm4j.installers/raw/master/dist/biz.aQute.jpm.run.jar > ~/tmp.jar
JPM_BIN_DIR=`java -jar ~/tmp.jar -u init | grep -e "Bin[ \t]*dir" | awk '{print $3}'`
rm -f ~/tmp.jar

###########################
echo 'installing blade'

${JPM_BIN_DIR}/jpm install -f https://releases.liferay.com/tools/blade-cli/${BLADE_VERSION}/blade.jar

echo "blade installed successfully into ${JPM_BIN_DIR}/blade"

echo ''