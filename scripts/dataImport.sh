#!/bin/sh

if [ $# -ne 4 ];then
    echo "Invoke it as $0 DHIS_IP PORT USERNAME PASSWORD"
    exit -1
fi

DHIS_IP=$1
DHIS_PORT=$2
DHIS_USERNAME=$3
DHIS_PASSWORD=$4

CATEGORYCOMBO=`curl -s -H 'Content-Type:application/json' -u $DHIS_USERNAME:$DHIS_PASSWORD -X GET http://$DHIS_IP:$DHIS_PORT/api/categoryCombos|./json.sh -p -l -b|grep categoryCombos|grep id|awk '{print $NF}'|tr -d '"'`

# Step 1 - Create Data Elements
for file in `ls dataelements`;
do
    sed -e "s/REPLACEME/$CATEGORYCOMBO/g" dataelements/$file|curl -s -u $DHIS_USERNAME:$DHIS_PASSWORD -X POST \
        -H 'Content-Type:application/json' --data-binary @- http://$DHIS_IP:$DHIS_PORT/api/dataElements
done

# Step 2 - Create Top Level Org
ADMINID=`curl -s -H 'Content-Type:application/json' -u $DHIS_USERNAME:$DHIS_PASSWORD -X GET http://$DHIS_IP:$DHIS_PORT/api/users|./json.sh -s -l|grep id|awk '{print $NF}'|tr -d '"'`
echo "ADMIN ID = $ADMINID"
TOPLEVELORGID=`cat orgUnits/toplevel.json |./json.sh -s -l |grep -w id|head -1|awk '{print $NF}'|tr -d '"'`
sed -e "s/REPLACEME/$ADMINID/g" orgUnits/toplevel.json|curl -s -u $DHIS_USERNAME:$DHIS_PASSWORD -X POST -H 'Content-Type:application/json' --data-binary @- http://$DHIS_IP:$DHIS_PORT/api/organisationUnits
#sed -e "s/REPLACEME/$ADMINID/g" orgUnits/toplevel.json|curl -s -u $DHIS_USERNAME:$DHIS_PASSWORD -X PUT -H 'Content-Type:application/json' --data-binary @- http://$DHIS_IP:$DHIS_PORT/api/organisationUnits/$TOPLEVELORGID
sed -e "s/REPLACEME/$ADMINID/g" orgUnits/updateusers.json|curl -s -u $DHIS_USERNAME:$DHIS_PASSWORD -X PUT -H 'Content-Type:application/json' --data-binary @- http://$DHIS_IP:$DHIS_PORT/api/users/$ADMINID

# Step 3 - Create Data Set and assign it to Org
for file in `ls datasets`;
do
    sed -e "s/REPLACEME/$TOPLEVELORGID/g" datasets/$file|curl -s -u $DHIS_USERNAME:$DHIS_PASSWORD -X POST \
        -H 'Content-Type:application/json' --data-binary @- http://$DHIS_IP:$DHIS_PORT/api/dataSets
echo ""
done
