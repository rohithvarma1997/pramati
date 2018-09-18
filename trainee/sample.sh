#!/bin/sh
fpath=$1 
filename=$(basename "$fpath") 
directory=$(dirname "$fpath") 
cd ..
cd $directory 
mail=$(grep -E -o "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}" $filename)
mob=$(grep -E -o "[0-9]{10,12}" $filename)
echo "Name:$(echo "$filename" | cut -f 1 -d '.')"
echo "Email:$mail"
echo "Mobile:$mob"
res=$filename,$mail,$mob
echo $res
psql -d resume -U rohithv -h 172.18.0.2 -c 'select * form resume_collect'
