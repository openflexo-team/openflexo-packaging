#!/bin/bash
curdir=`pwd`
openflexo_dir=`dirname "$0"`
cd "$openflexo_dir";
java @vm.args@ -classpath lib/\*;dev/\* @main.class@ -userType @userType@