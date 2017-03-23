# DemoFileHandlerSpringBoot

API document: 

Please start Spring boot application first. 
root: localhost：8080
Tested on Postman. 

Requirements: 
1. API to upload a file with a few meta-data fields. 
   Persist meta-data in persistence store (In memory DB or file system and store the content on a file system)

POST @ /upload to upload a file along with optional input user and desc (description)
Will return a message with status 200 if succeed.
Will return 404 if the file exists on server. 
The meta data will be put into embedded database Apache Derby. 

2. API to get file meta-data

GET @ /meta/all     return a list of all the meta data.

GET @ /meta?id={id} return the meta data of given id. 
return 404 and empty message if meta with the id doesn't exist.

3. API to download content stream (Optional)

GET @ /files/all to show the download links of all files. 

GET @ /files/{filename} to download the specific file. 
return 404 and empty message if filename doesn't exist.

4. API to search for file IDs with a search criterion (Optional)

GET @ /meta_id?keyword={keyword} return a list of all the meta data having filename, user, desc related with the keyword occurrence.

GET @ /meta_id?user={user} return the meta data of files uploaded by the given user. 
return emptyList if meta with the correct formatted criteria doesn't exist.
return 404 and empty message if criteria is not correctly formatted.

5. Write a scheduler in the same app to poll for new items in the last hour and send an email (Optional)

Wrote a scheduler to poll the new incoming items in the last hour and trigger it every 30 sec. 
Didn't finish with the email function, but left the space for it. 
