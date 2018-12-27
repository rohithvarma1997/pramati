# Project Title
Docusign Demo

## Getting Started
This program demonstrates how to get access token and create envelope using docusign REST APIs.

### Prerequisites
- install python2.7
- install requests
 ```
python2.7 -m pip install requests --user
```
### Process
- Run the code
```
python2.7 docusigndemo.py
 ```
- The authorisation code link will be opened in the browser.
  (Provide the authentication details if prompted.)
- Copy the callback URL and paste in terminal.
  (Access token is generated)
- Choose option 1 to send envelope.
- Provide the recipient mail and name.
- Provide the path of the file to be sent which is in your machine.

## Example
- Enter the full callback URL after you have authenticated:(get the link from browser)
- enter 1 to send an envelope1
- enter recipient email: vinayr0325@gmail.com
- enter recipient name: v
- enter the full path of the file you want to send: /home/vinayr/hello.txt
## Output
Acknowledgment of status of file
envelope id of the file 




