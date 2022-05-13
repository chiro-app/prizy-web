#!/bin/bash
set -e

if [ $# -ne 2 ]; then
    echo -e "Usage:\n\n${0##*/} <APP ID> <TAG>"
    exit 1
fi

APP_ID=$1
NEW_TAG=$2
TFILE=$(mktemp --suffix .yaml)

doctl -v apps spec get "${APP_ID}" > app.yml
sed "s/{TAG_NAME}/${NEW_TAG}/g" app.yml > "${TFILE}"
doctl -v apps update "${APP_ID}" --spec "${TFILE}"

rm "${TFILE}" app.yml