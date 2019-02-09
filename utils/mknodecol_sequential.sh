#!/bin/bash

# This is a utility script which generates a serialized NodeCollection using the FileMgr class.
# Pass the number of nodes to be created as an argument to the script.

FILEMGR_SCRIPT="/tmp/mknodecol"

touch $FILEMGR_SCRIPT

echo "init mknodecol_random" >> $FILEMGR_SCRIPT

for i in $(seq $1); do
    echo "add $i test_node" >> $FILEMGR_SCRIPT
done

echo "flush mknodecol_random" >> $FILEMGR_SCRIPT
echo "exit" >> $FILEMGR_SCRIPT

java FileMgr < $FILEMGR_SCRIPT

rm $FILEMGR_SCRIPT
