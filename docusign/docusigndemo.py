import requests
import urlparse
import webbrowser
import urllib
import base64
import json
import os

auth_url="https://account-d.docusign.com/oauth/auth"
client_id="5af20934-a3a7-45de-a002-e89416aabce8"
token_url="https://account-d.docusign.com/oauth/token"
callback_url="https://www.docusign.com"
secret_key="411f4c83-8848-41d4-80f4-86a6ccefe311"

auth_query_params= {"response_type":"code","scope":"signature","client_id":client_id,"state":"a39fh23hnf23","redirect_uri":callback_url}
# construct the authorization url
final_auth_url = auth_url+"?{}".format(urllib.urlencode(auth_query_params))
# provide the login credentials if asked
webbrowser.open(final_auth_url)
authorization_response = raw_input('Enter the full callback URL after you have authenticated:')
# parse the url
parsed = urlparse.urlparse(authorization_response)
# parse the auth code form the url
auth_code=urlparse.parse_qs(parsed.query)['code']
# parse the state if present
state=urlparse.parse_qs(parsed.query)['state']
# token request header
token_headers={'Authorization': 'Basic '+base64.b64encode(client_id+":"+secret_key)}
# token request data
token_data={'grant_type':'authorization_code','code':auth_code,'state':state}
# make post request
r=requests.post(token_url,headers=token_headers,data=token_data)
# store the response
tokenResponse=json.loads(r.text)

if(r.status_code==200):
  print("access_token:\n"+tokenResponse['access_token'])
  # get access token
  access_token=tokenResponse['access_token']
else:
  # print error message
  print("error")
  print(r.status_code)
  print(tokenResponse['error'])

# todo:get the request body for the envelope
# get file name from user and send it
# convert file yo base64 first
# try to get the url without asking the user to give the callback url

userinfoEndpoint="https://account-d.docusign.com/oauth/userinfo"
# request header
request_header={'Authorization': 'Bearer '+access_token}
# make request
res=requests.get(userinfoEndpoint,headers=request_header)
userinfo=json.loads(res.text)
if(res.status_code==200):
  # get baseUri and account id to amke the api calls
  base_uri=userinfo['accounts'][0]['base_uri']
  account_id=userinfo['accounts'][0]['account_id']
else:
  print("unable to fetch the user info, Reason:")
  print(userinfo['error'])

def createEnvelope():
  create_envelope_endpoint=base_uri+"/restapi/v2/accounts/"+account_id+"/envelopes"
  recipient_email=raw_input("enter recipient email: ")
  recipient_name=raw_input("enter recipient name: ")
  file_path=raw_input("enter the full path of the file you want to send: ")
  filename = os.path.basename(file_path)
  name, file_extension = os.path.splitext(filename)
  with open(file_path, "rb") as input_file:
    encoded_string = base64.b64encode(input_file.read())
  data={
  "documents": [
    {
      "documentBase64": encoded_string,
      "documentId": "1",
      "fileExtension": file_extension,
      "name": filename
    }
  ],
  "emailSubject": "Please sign the sample file",
  "recipients": {
    "signers": [
      {
        "email": recipient_email,
        "name": recipient_name,
        "recipientId": "1",
        "routingOrder": "1",
        "tabs": {
          "signHereTabs": [
            {
              "xPosition": "450",
              "yPosition": "650",
              "documentId": "1",
              "pageNumber": "1"
            }
          ]
        }
      }
    ]
  },
  "status": "sent"
  }
  req_header={'Content-Type': 'application/json','Authorization':'Bearer '+access_token}
  response = requests.post(create_envelope_endpoint,headers=req_header, json=data)
  envelope_details=json.loads(response.text) 
  if(response.status_code==201):
    print("file successfully sent")
    print("envelope id:\n"+envelope_details['envelopeId'])
  else:
    print("envelope creation failed, Reason:")
    print(envelope_details['error'])
  

option=input("enter 1 to send an envelope")
if option==1:
  createEnvelope()
else:
  exit()
  




