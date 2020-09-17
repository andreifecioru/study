#!/usr/bin/env bash

file_list="./Makefile.in $(find . -iname include.mk)"

for item in $file_list; do
    echo "Patching: $item"
    sed -i "s|http://repo1.maven.org|https://repo1.maven.org|g" $item
    sed -i "s|http://central.maven.org|https://repo1.maven.org|g" $item
done

