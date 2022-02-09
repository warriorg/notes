部署位置 
/home/longnows/sw-decode


启动方式 
./start.sh 


关闭方式

kill $(pgrep sw-decode-linux)


检查是否启动

curl --include --request POST 'http://localhost:3000/decode' \
--header 'Content-Type: application/json' \
--data '{
    "key": "8V1YlwTyqMTW0Qkg",
    "text": "qVGSEMhKEyoDMeO5M0OJot9dwtv0Oauo90w8HGfKKOkCumGcXF6BlYL2vlHLcWnjrrin1qKCbopvdrn+xRfGTQ=="
}'


