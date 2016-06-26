#!/usr/bin/env bash

_sem_ver="$(cat version.txt | xargs)"
_commits="$(git rev-list --count HEAD)"
_hash="$(git rev-parse --short HEAD)"


if [ "${CIRCLE_PROJECT_USERNAME:-}" = "pico-works" ]; then
    if [ "${CIRCLE_TAG:-}" = "${_sem_ver}" ]; then
        echo "$_sem_ver"
        exit 0
    fi

    if [ "${CIRCLE_BRANCH:-}" = "develop" ]; then
        echo "$_sem_ver-$_commits"
        exit 0
    fi
fi

echo "$_sem_ver-$_commits-$_hash"
